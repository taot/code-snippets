package com.taot.guice;

import java.io.IOException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;

public class App {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("App start running...");

        Injector injector = Guice.createInjector(new DaoModule(), new ServiceModule(),
            new JpaPersistModule("myFirstJpaUnit"));
        
        injector.getInstance(Initializer.class);
        Worker worker = injector.getInstance(Worker.class);
        worker.run();

        System.out.println("End...");
    }
}
