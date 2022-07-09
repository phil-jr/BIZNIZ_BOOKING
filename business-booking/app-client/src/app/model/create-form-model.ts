import { FormInputDataCF } from "./form-input-data-cf-model";
import { ScheduleMetadataCF } from "./schedule-metadata-cf.model";

export class CreateForm {
    formName: string;
    formInputData: FormInputDataCF[];
    scheduleMetaData: ScheduleMetadataCF[];
    constructor(){}
}