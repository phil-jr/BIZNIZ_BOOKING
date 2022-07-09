package com.fpo.app.api.constants;

public class SqlConstants {

    //GET FORMS
    public final static String GET_FORMS = "SELECT * FROM forms WHERE businessId = (SELECT businessId FROM businesssession WHERE sessionId = ?)";

    public final static String GET_FORM_METADATA_BY_LINK_NAME = "SELECT * FROM formcontent " +
                                                                "JOIN forms ON forms.FormId = formcontent.FormId " +
                                                                "WHERE forms.formLinkName = ? " + 
                                                                "AND businessId = (SELECT businessId FROM businesses WHERE username = ?)";

    public final static String GFMBLN_SELECT_OPTION = "SELECT * FROM formcontent " +
                                                      "JOIN forms ON forms.FormId = formcontent.FormId " +
                                                      "WHERE forms.formLinkName = ? " + 
                                                      "AND businessId = (SELECT businessId FROM businesses WHERE username = ?)";

    //CREATE FORM                                                  
    public final static String CREATE_FORM =  "INSERT INTO forms VALUES (?, (SELECT BusinessId FROM businesssession WHERE SessionId = ?), ?, ?, NOW())";

    public final static String CF_INSERT_FORM_CONTENT = "INSERT INTO forms VALUES (?, (SELECT BusinessId FROM businesssession WHERE SessionId = ?), ?, ?, NOW())";

    public final static String CF_INSERT_FORM_SELECT_OPTIONS = "INSERT INTO forms VALUES (?, (SELECT BusinessId FROM businesssession WHERE SessionId = ?), ?, ?, NOW())";

    public final static String CF_INSERT_SCHEDULING = "INSERT INTO scheduling (ScheduleId, FormId, TimeSlotBegin, TimeSlotEnd, Available) VALUES ";

    public final static String CF_DELETE_SCHEDULING = "DELETE FROM scheduling WHERE formId = ?";

    public final static String CF_DELETE_FORMS = "DELETE FROM forms WHERE formId = ?";

    //GET FORM DATA
    public final static String GET_FORM_DATA =  "SELECT Scheduling.formDataGroupId, scheduling.timeSlotBegin, scheduling.timeSlotEnd,\n" +
                                                "        forms.formName, customers.customerId, customers.firstName, customers.lastName,\n" +
                                                "        formContent.inputName, formdata.formDataAsString, forms.formCreationDate\n" +
                                                "FROM formdata\n" +
                                                "JOIN FormContent ON FormData.FormContentId = FormContent.FormContentId\n" +
                                                "JOIN Forms ON FormContent.FormId = Forms.FormId\n" +
                                                "JOIN Scheduling ON FormData.FormDataGroupId = Scheduling.FormDataGroupId\n" +
                                                "JOIN Customers ON FormData.CustomerId = Customers.CustomerId\n" +
                                                "WHERE Forms.FormId = ?;";


    //BOOK APPOINTMENT
    public final static String BOOK_APPMNT_GET_SCHEDULE_ID = "SELECT scheduleId FROM scheduling WHERE timeSlotBegin = ? AND timeSlotEnd = ? AND formId = (SELECT formId FROM forms WHERE formLinkName = ? AND businessId = (SELECT businessId FROM businesses WHERE username = ?))";

    public final static String BOOK_APPMNT_INSERT_FORM_DATA = "INSERT INTO formdata VALUES (?, ?, (SELECT customerId FROM customersession WHERE sessionId = ?), (SELECT formContentId FROM formcontent WHERE inputName = ? AND formId = (SELECT formId FROM forms WHERE formLinkName = ? AND businessId = (SELECT businessId FROM businesses WHERE username = ?))), ?, NOW())";

    public final static String BOOK_APPMNT_UPDATE_SCHEDULING = "UPDATE scheduling SET CustomerId = (SELECT CustomerId FROM CustomerSession WHERE SessionId = ?), Available = ?, FormDataGroupId = ? WHERE ScheduleId = ?"; 

    //GET APPOINTMENT TIMES
    public final static String GET_APPOINTMENT_TIMES_CUST = "SELECT * FROM scheduling " +
                                                            "WHERE formid = (SELECT formid FROM forms WHERE formLinkName = ? " +
                                                            "AND businessId = (SELECT businessid FROM businesses WHERE username = ?)) " +
                                                            "AND available = 1";
}
