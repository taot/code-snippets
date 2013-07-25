package com.taot.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.taot.guice.model.CreditCard;
import com.taot.guice.service.BillingService;

public class App {
    
    public static void main(String[] args) {
        System.out.println("App start running...");
        Injector injector = Guice.createInjector(new BillingModule());
        BillingService bs = injector.getInstance(BillingService.class);
        bs.chargeOrder(new CreditCard());
    }
}
