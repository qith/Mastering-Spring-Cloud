package pl.piomin.services.boot.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.stereotype.Component;


@Component
public class MyCounterService {

    public MyCounterService() {
        Metrics.addRegistry(new SimpleMeterRegistry());
    }



    public void counter(String name, String... tags) {
        Counter counter = Metrics.counter(name, tags);
        counter.increment();
    }
}

