package com.taot.guice;

import javax.inject.Inject;

import com.google.inject.persist.PersistService;

public class Initializer {

    @Inject
    Initializer(PersistService  persistService) {
        persistService.start();
    }
}
