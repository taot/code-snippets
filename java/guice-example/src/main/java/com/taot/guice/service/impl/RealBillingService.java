package com.taot.guice.service.impl;

import java.util.Random;

import com.google.inject.Inject;
import com.taot.guice.model.CreditCard;
import com.taot.guice.service.BillingService;
import com.taot.guice.service.CreditCardProcessor;

public class RealBillingService implements BillingService {
    
    private Random rand = new Random();
    
    private CreditCardProcessor processor;
    
    @Inject
    public RealBillingService(CreditCardProcessor processor) {
        this.processor = processor;
    }

    public int chargeOrder(CreditCard creditCard) {
        System.out.println("RealBillingService.chargeOrder");
        processor.process(creditCard);
        return rand.nextInt();
    }
}
