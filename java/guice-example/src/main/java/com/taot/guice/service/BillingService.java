package com.taot.guice.service;

import com.taot.guice.model.CreditCard;

public interface BillingService {
    
    int chargeOrder(CreditCard creditCard);
}
