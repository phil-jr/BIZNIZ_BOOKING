package com.gm.fgcf.api.model;

public class BusinessCustomer {
    BusinessCustomer(){}
    private String businessId;
    private String customerId;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
