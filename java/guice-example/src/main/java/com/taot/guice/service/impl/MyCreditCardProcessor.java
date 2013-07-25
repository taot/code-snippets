package com.taot.guice.service.impl;

import com.taot.guice.model.CreditCard;
import com.taot.guice.service.CreditCardProcessor;

public class MyCreditCardProcessor implements CreditCardProcessor {

    public void process(CreditCard card) {
        System.out.println("MyCreditCardProcessor.process");
    }

}
