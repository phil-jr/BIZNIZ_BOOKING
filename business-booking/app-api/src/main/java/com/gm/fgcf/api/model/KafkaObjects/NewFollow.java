package com.gm.fgcf.api.model.KafkaObjects;

import com.gm.fgcf.api.model.Customer;

public class NewFollow {
    public String process;
    public Customer customer;
    public NewFollow(String process, Customer customer) {
        this.process = process;
        this.customer = customer;
    }
}
