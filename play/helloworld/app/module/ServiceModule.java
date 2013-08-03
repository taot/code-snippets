package module;

import com.google.inject.AbstractModule;
import service.HelloService;
import service.impl.HelloServiceImpl;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HelloService.class).to(HelloServiceImpl.class);
    }
}
