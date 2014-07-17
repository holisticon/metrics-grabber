package de.holisticon.monitoring.tools.metricsgrabber.core.config;

/**
 * Class that encapsulates a metric reader configuration.
 * @author Tobias Gindler, Holisticon AG on 17.07.14.
 */
public class MetricReaderConfig implements Activateable{

    private String type;
    private MetricConfiguration[] metrics;
    private boolean active = true;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MetricConfiguration[] getMetrics() {
        return metrics;
    }

    public void setMetrics(MetricConfiguration[] metrics) {
        this.metrics = metrics;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
