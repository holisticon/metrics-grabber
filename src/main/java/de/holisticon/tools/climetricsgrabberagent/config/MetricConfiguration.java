package de.holisticon.tools.climetricsgrabberagent.config;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tobias Gindler on 04.07.14.
 */
public class MetricConfiguration {

    private String metricPrefix;
    private String metricQueryString;
    private int queryIntervalInSeconds;
    private String [] attributes;
    private Set<String> attributeSet;

    public String getMetricPrefix() {
        return metricPrefix;
    }

    public void setMetricPrefix(String metricPrefix) {
        this.metricPrefix = metricPrefix;
    }

    public String getMetricQueryString() {
        return metricQueryString;
    }

    public void setMetricQueryString(String metricQueryString) {
        this.metricQueryString = metricQueryString;
    }

    public int getQueryIntervalInSeconds() {
        return queryIntervalInSeconds;
    }

    public void setQueryIntervalInSeconds(int queryIntervalInSeconds) {
        this.queryIntervalInSeconds = queryIntervalInSeconds;
    }

    public String[] getAttributes() {
        return attributes;
    }

    public Set<String> getMetricsAsSet() {
        return this.attributeSet;
    }

    public void setAttributes(String[] attributes) {

        this.attributes = attributes;

        Set<String> tmpSet = new HashSet<String>();
        for (String attribute : attributes) {
            tmpSet.add(attribute);
        }
        this.attributeSet = tmpSet;
    }
}
