package br.com.lucas.config;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;

@Health
@ApplicationScoped
public class ApplicationHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse
                .named("application-check").up()
                .withData("CPUAvailable", Runtime.getRuntime().availableProcessors())
                .withData( "MemoryFree", Runtime.getRuntime().freeMemory())
                .withData("TotalMemory", Runtime.getRuntime().totalMemory())
                .build();
    }
}