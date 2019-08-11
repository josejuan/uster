package com.uster.server.rest;

import com.uster.backend.FakeBackend;
import com.uster.backend.HibernateBackend;
import com.uster.backend.core.Backend;
import com.uster.business.Uster;
import com.uster.extra.DerbyBackend;
import com.uster.metrics.Metrics;
import com.uster.metrics.Prometheus;
import com.uster.metrics.SimpleMetrics;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.naming.NamingException;

@Getter
@Setter
@ConfigurationProperties(prefix = "uster")
public class Config {

    private PossibleBackend backend = PossibleBackend.Default;
    private PossibleMetrics metrics = PossibleMetrics.Stdout;

    private Backend makeBackend() {
        switch (backend) {
            case Default:
                return new FakeBackend();
            case Hibernate:
                try {
                    DerbyBackend.setup();
                } catch (NamingException e) {
                    throw new IllegalStateException(e);
                }
                return new HibernateBackend();
        }
        throw new IllegalStateException("Unknown '" + backend + "' backend");
    }

    private Metrics makeMetrics() {
        switch (metrics) {
            case Stdout:
                return new SimpleMetrics();
            case Prometheus:
                return new Prometheus();
        }
        throw new IllegalStateException("Unknown '" + metrics + "' metrics");
    }

    Uster makeUster() {
        return new Uster(makeBackend(), makeMetrics());
    }

    public enum PossibleBackend {
        Default,
        Hibernate
    }

    public enum PossibleMetrics {
        Stdout,
        Prometheus
    }
}
