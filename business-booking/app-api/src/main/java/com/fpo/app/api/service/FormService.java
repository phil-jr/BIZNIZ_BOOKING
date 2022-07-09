package com.fpo.app.api.service;

import com.fpo.app.api.config.KafkaProducerConfig;
import com.fpo.app.api.constants.SqlConstants;
import com.fpo.app.api.model.*;
import com.fpo.app.api.model.KafkaObjects.KBookedAppointment;
import com.fpo.app.api.model.ScheduleMetadata.ExceptionDay;
import com.fpo.app.api.model.ScheduleMetadata.ScheduleMetadata;
import com.fpo.app.api.model.ScheduleMetadata.StartEnd;
import com.fpo.app.api.model.returnModel.GetFormMetaData;
import com.fpo.app.api.util.ExistsValidateGet;
import com.fpo.app.api.util.RandomUtil;
import com.fpo.app.api.util.UtilMethods;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FormService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    ExistsValidateGet existsValidateGet;

    @Autowired
    KafkaProducer<String, String> kafkaProducer;


    @Autowired
    KafkaProducerConfig kafkaProducerConfig;

    @Autowired
    private RandomUtil randomUtil;

    //Specialized because we cannot use generic class for return. Each one could be unique
    public ResponseEntity<Object> getFormData(String formId, String sessionId){
        List<Map<String, Object>> dataList = jdbcTemplate.queryForList(SqlConstants.GET_FORM_DATA, new Object[]{formId});
        JSONObject finalObj = new JSONObject();
        finalObj.put("formInfo", new JSONObject());
        finalObj.put("formData", new JSONArray());

        List<Map<String, Object>> dataListInputFiltered = dataList.stream().filter(UtilMethods.distinctByKey(p -> p.get("FormDataGroupId"))).collect(Collectors.toList());

        for(int i = 0; i < dataListInputFiltered.size(); i++){
            JSONObject tempObj = new JSONObject();
            for(int j = 0; j < dataList.size(); j++){

                if(j == 0){
                    finalObj.getJSONObject("formInfo").put("InputData", new JSONArray());
                    finalObj.getJSONObject("formInfo").put("FormName", dataList.get(j).get("FormName").toString());
                    finalObj.getJSONObject("formInfo").put("FormCreationDate", dataList.get(j).get("FormCreationDate").toString());
                }

                if(dataList.get(j).get("FormDataGroupId").toString().equals(dataListInputFiltered.get(i).get("FormDataGroupId").toString())){
                    String inpKey = dataList.get(j).get("inputName").toString().toLowerCase().replace(" ", "_");
                    tempObj.put(inpKey, dataList.get(j).get("FormDataAsString").toString());
                    finalObj.getJSONObject("formInfo").getJSONArray("InputData").put(dataList.get(j).get("inputName").toString());
                }

            }
            finalObj.getJSONArray("formData").put(tempObj);
        }

        return ResponseEntity.status(HttpStatus.OK).body(finalObj.toString());
    }

    //Used for getting the data needed to recreate a form
    public ResponseEntity<Object> getFormMetadataByLinkName(String formLinkName, String business) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<FormMetadataPiece> formMetadata = (ArrayList<FormMetadataPiece>) jdbcTemplate.query(
            SqlConstants.GET_FORM_METADATA_BY_LINK_NAME,
            new Object[] {formLinkName, business},
            new RowMapper<FormMetadataPiece>() {
                public FormMetadataPiece mapRow(ResultSet rs, int rowNum) throws SQLException {
                    FormMetadataPiece fmdp = new FormMetadataPiece();
                    fmdp.setFormName(rs.getString("formName"));
                    fmdp.setFormCreationDate(rs.getTimestamp("formCreationDate").toString());
                    fmdp.setName(rs.getString("inputName"));
                    fmdp.setType(rs.getString("inputType"));
                    fmdp.setPlacement(rs.getInt("placement"));
                    fmdp.setFormContentId(rs.getString("formContentId"));
                    return fmdp;
                }
            });

        for(FormMetadataPiece fmdp: formMetadata){
            if(fmdp.getType().equalsIgnoreCase("select")){
                fmdp.setOptions((ArrayList<String>) jdbcTemplate.queryForList(SqlConstants.GFMBLN_SELECT_OPTION, new Object[] {fmdp.getFormContentId()}, String.class));
            }
        }

        GetFormMetaData gfmd = new GetFormMetaData();
        gfmd.setFormName(formMetadata.get(0).getFormName());
        gfmd.setFormInputData(formMetadata);

        return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(gfmd));
    }

    public ResponseEntity<Object> getForms(String sessionId) throws JsonGenerationException, JsonMappingException, IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<FormMetadataPiece> formMetadataList = (ArrayList<FormMetadataPiece>) jdbcTemplate.query(
        SqlConstants.GET_FORMS,
        new Object[] {sessionId},
        new RowMapper<FormMetadataPiece>() {
            public FormMetadataPiece mapRow(ResultSet rs, int rowNum) throws SQLException {
                    FormMetadataPiece fmdp = new FormMetadataPiece();
                    fmdp.setFormName(rs.getString("formName"));
                    fmdp.setFormCreationDate(rs.getTimestamp("formCreationDate").toString());
                    return fmdp;
                }
        });
        return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(formMetadataList));
    }

    //TODO check if form exists change primary key!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //Data being sent in order to create a custom form and schedule (essentially like a table in a database)
    public ResponseEntity<Object> createForm(String payload, String sessionId) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        FormInputData formInputData = objectMapper.readValue(payload, FormInputData.class);
        String formId = randomUtil.getSaltString(16);
        try {
            String formLinkName = formInputData.getFormName().toLowerCase().replaceAll(" ", "_");
            jdbcTemplate.update(SqlConstants.CREATE_FORM, new Object[]{formId, sessionId, formInputData.getFormName(), formLinkName});
            for (FormInputDatapiece dataPiece : formInputData.getFormInputData()) {
                String formContentId = randomUtil.getSaltString(16);
                jdbcTemplate.update(SqlConstants.CF_INSERT_FORM_CONTENT, new Object[]{formContentId, formId, dataPiece.getType(), dataPiece.getName(), dataPiece.getPlacement()});
                if(dataPiece.getType().equals("select")){
                    ArrayList<String> options = dataPiece.getOptions();
                    String optionsSql = SqlConstants.CF_INSERT_FORM_SELECT_OPTIONS;
                    for(String option: options){
                        optionsSql += "('"+formContentId+"', ?),";
                    }
                    jdbcTemplate.update(optionsSql.substring(0, optionsSql.length()-1), options.toArray());
                }
            }

            //GENERATE DATA FROM SCHEDULE METADATA
            ScheduleMetadata scheduleMetadata = formInputData.getScheduleMetadata();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startDate = LocalDateTime.parse(scheduleMetadata.getDayStart(), dtf);
            LocalDateTime endDate = LocalDateTime.parse(scheduleMetadata.getDayEnd(), dtf);
            String schedItemsSql = SqlConstants.CF_INSERT_SCHEDULING;
            int days = (int) Duration.between(startDate, endDate).toDays();

            for(int i = 0; i < days; i++) {

                LocalDateTime nextDate = startDate.plusDays((long) i);
                ArrayList<StartEnd> startEnd;

                switch (nextDate.getDayOfWeek().toString()) {
                    case "SUNDAY":
                        startEnd = scheduleMetadata.getSunTimes();
                        break;
                    case "MONDAY":
                        startEnd = scheduleMetadata.getMonTimes();
                        break;
                    case "TUESDAY":
                        startEnd = scheduleMetadata.getTueTimes();
                        break;
                    case "WEDNESDAY":
                        startEnd = scheduleMetadata.getWedTimes();
                        break;
                    case "THURSDAY":
                        startEnd = scheduleMetadata.getThuTimes();
                        break;
                    case "FRIDAY":
                        startEnd = scheduleMetadata.getFriTimes();
                        break;
                    case "SATURDAY":
                        startEnd = scheduleMetadata.getSatTimes();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + nextDate.getDayOfWeek().toString());
                }

                ExceptionDay exceptionDay = isExceptionDay(nextDate, scheduleMetadata.getExceptionDay(), dtf);
                ScheduleItem scheduleItem = new ScheduleItem();

                //TODO: check if data is already in scheduling table
                if(exceptionDay == null && startEnd != null) {
                    for (StartEnd se : startEnd) {
                        String scheduleItemId = randomUtil.getSaltString(16);
                        scheduleItem.setScheduleItemId(scheduleItemId);
                        scheduleItem.setFormId(formId);
                        scheduleItem.setAvailable(true);
                        scheduleItem.setTimeSlotBegin(getLocalDateTime(nextDate.toString(), se.getStartTime(), dtf).toString());
                        scheduleItem.setTimeSlotEnd(getLocalDateTime(nextDate.toString(), se.getEndTime(), dtf).toString());
                        scheduleItem.setCustomerId(null);
                        schedItemsSql += "('" + scheduleItem.getScheduleItemId() +"','" + scheduleItem.getFormId() + "','" + scheduleItem.getTimeSlotBegin().replace("T", " ") + "','" + scheduleItem.getTimeSlotEnd().replace("T", " ") + "'," + scheduleItem.isAvailable() + "),\n";
                    }
                } else if (exceptionDay != null && exceptionDay.getExceptionTimes().size() > 0){
                    ArrayList<StartEnd> exceptionTimes = exceptionDay.getExceptionTimes();
                    for (StartEnd dayStartEnd : startEnd) {
                        LocalDateTime appointmentStart = getLocalDateTime(nextDate.toString(), dayStartEnd.getStartTime(), dtf);//LocalDateTime.parse(appointmentStartStr, dtf);
                        LocalDateTime appointmentEnd = getLocalDateTime(nextDate.toString(), dayStartEnd.getEndTime(), dtf);
                        for(StartEnd exceptionTime: exceptionTimes){
                            LocalDateTime etStart = getLocalDateTime(exceptionDay.getExceptionDay(), exceptionTime.getStartTime(), dtf);
                            LocalDateTime etEnd = getLocalDateTime(exceptionDay.getExceptionDay(), exceptionTime.getEndTime(), dtf);
                            Boolean checkIfStartBetween = appointmentStart.isAfter(etStart) && appointmentStart.isBefore(etEnd);
                            Boolean checkIfEndBetween =appointmentEnd.isAfter(etStart) && appointmentEnd.isBefore(etEnd);
                            if(!(checkIfStartBetween || checkIfEndBetween)){
                                String scheduleItemId = randomUtil.getSaltString(16);
                                scheduleItem.setScheduleItemId(scheduleItemId);
                                scheduleItem.setFormId(formId);
                                scheduleItem.setAvailable(true);
                                scheduleItem.setTimeSlotBegin(getLocalDateTime(nextDate.toString(), dayStartEnd.getStartTime(), dtf).toString());
                                scheduleItem.setTimeSlotEnd(getLocalDateTime(nextDate.toString(), dayStartEnd.getEndTime(), dtf).toString());
                                scheduleItem.setCustomerId(null);
                                schedItemsSql += "('" + scheduleItem.getScheduleItemId() +"','" + scheduleItem.getFormId() + "','" + scheduleItem.getTimeSlotBegin().replace("T", " ") + "','" + scheduleItem.getTimeSlotEnd().replace("T", " ") + "'," + scheduleItem.isAvailable() + "),\n";
                            }
                        }
                    }
                }
            }

            schedItemsSql = schedItemsSql.substring(0,schedItemsSql.length()-2) + ";";
            jdbcTemplate.update(schedItemsSql);
            return ResponseEntity.status(HttpStatus.OK).body("{\"Message\":\"Successfully created form \", \"Errors\":[]}");

        } catch (Exception e){
            try {
                jdbcTemplate.update(SqlConstants.CF_DELETE_SCHEDULING, new Object[]{formId});
                jdbcTemplate.update(SqlConstants.CF_DELETE_FORMS, new Object[]{formId});
            } catch (Exception ex){
                e.printStackTrace();
            }
            e.printStackTrace();
        }
        return null;
    }

    public ResponseEntity<Object> bookAppointment(String payload, String sessionId) throws IOException {
        //TODO Set appointment booking limit || If user already scheduled appointment don't allow
        try {
            //TODO possibly need Kafka here
            ObjectMapper objectMapper = new ObjectMapper();
            AppointmentBooking appBooking = objectMapper.readValue(payload, AppointmentBooking.class);

                //Getting scheduling Id
                List<Map<String, Object>> schedList = jdbcTemplate.queryForList(SqlConstants.BOOK_APPMNT_GET_SCHEDULE_ID, new Object[] {appBooking.getTimeSlotBegin(), appBooking.getTimeSlotEnd(), appBooking.getFormLinkName(), appBooking.getBusiness()});
                String scheduleId = schedList.get(0).get("scheduleId").toString();

                String formDataGroupId = randomUtil.getSaltString(16);
                appBooking.getFormData().forEach(item -> {
                    String formDataId = randomUtil.getSaltString(16);
                    jdbcTemplate.update(SqlConstants.BOOK_APPMNT_INSERT_FORM_DATA, new Object[]{formDataId, formDataGroupId,sessionId, item.getInputName(), appBooking.getFormLinkName(), appBooking.getBusiness(), item.getInputValue()});
                });

                jdbcTemplate.update(SqlConstants.BOOK_APPMNT_UPDATE_SCHEDULING, new Object[]{sessionId, false, formDataGroupId, scheduleId});
                
                //Send to kafka
                Customer customer = existsValidateGet.getCustomerBySession(sessionId);
                kafkaProducerConfig.pushToKafka(kafkaProducer, "TEST-TOPIC", new KBookedAppointment("bookedAppointment", customer, appBooking), "username", appBooking.getBusiness());

                return ResponseEntity.status(HttpStatus.OK).body("{\"Message\":\"Successfully booked appointment \", \"Errors\":[]}");
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body("{\"Message\":\"There was an error booking your appointment \", \"Errors\":[]}");
        }
    }

    public ResponseEntity<Object> getAppointmentTimesCust(String businessUsername, String formLinkName) throws JsonGenerationException, JsonMappingException, IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<AppointmentScheduleItemCust> appSchedItems = (ArrayList<AppointmentScheduleItemCust>) jdbcTemplate.query(
        SqlConstants.GET_APPOINTMENT_TIMES_CUST,
        new Object[] {formLinkName, businessUsername},
        new RowMapper<AppointmentScheduleItemCust>() {
            public AppointmentScheduleItemCust mapRow(ResultSet rs, int rowNum) throws SQLException {
                AppointmentScheduleItemCust appSchedItem = new AppointmentScheduleItemCust();
                appSchedItem.setTimeSlotBegin(rs.getTimestamp("timeSlotBegin").toString());
                appSchedItem.setTimeSlotEnd(rs.getTimestamp("timeSlotEnd").toString());
                appSchedItem.setAvailable(rs.getByte("available")+ "");
                return appSchedItem;
            }
        });
        return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(appSchedItems));
    }

    /*public ResponseEntity<Object> getAppointmentTimesBus(String businessUsername, String formLinkName){
        ObjectMapper objectMapper = new ObjectMapper();
        String sql = "SELECT * FROM scheduling " +
                     "WHERE formid = (SELECT formid FROM forms WHERE formLinkName = ? " +
                     "AND businessId = (SELECT businessid FROM businesses WHERE username = ?))";
        ArrayList<AppointmentScheduleItem> appSchedItems = (ArrayList<AppointmentScheduleItem>) jdbcTemplate.query(
        sql,
        new Object[] {formLinkName, businessUsername},
        new RowMapper<AppointmentScheduleItem>() {
            public AppointmentScheduleItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                AppointmentScheduleItem appSchedItem = new AppointmentScheduleItem();
                
                return appSchedItem;
            }
        });
    }*/

    public ExceptionDay isExceptionDay(LocalDateTime day, ArrayList<ExceptionDay> exceptionDays, DateTimeFormatter dtf){
        for (ExceptionDay exceptionDay : exceptionDays) {
            LocalDateTime exday = LocalDateTime.parse(exceptionDay.getExceptionDay(), dtf);
            if(day.getYear() == exday.getYear()
                && day.getMonthValue() == exday.getMonthValue()
                && day.getDayOfMonth() == exday.getDayOfMonth()){
                return exceptionDay;
            }
        }
        return null;
    }

    public LocalDateTime getLocalDateTime(String dateStr, String timeStr, DateTimeFormatter dtf){
        String d = dateStr.substring(0, 10) + " " + timeStr + ":00";
        return LocalDateTime.parse(d, dtf);
    }

}
