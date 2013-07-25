package com.taot.guice;

import com.google.inject.AbstractModule;
import com.taot.guice.service.BillingService;
import com.taot.guice.service.CreditCardProcessor;
import com.taot.guice.service.impl.MyCreditCardProcessor;
import com.taot.guice.service.impl.RealBillingService;

public class BillingModule extends AbstractModule {

    @Override
    protected void configure() {
        
        bind(CreditCardProcessor.class);
        
        bind(BillingService.class);
    }
}
