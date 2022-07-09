package com.fpo.app.api.model.KafkaObjects;

import com.fpo.app.api.model.Customer;

public class NewFollow {
    public String process;
    public Customer customer;
    public NewFollow(String process, Customer customer) {
        this.process = process;
        this.customer = customer;
    }
}
