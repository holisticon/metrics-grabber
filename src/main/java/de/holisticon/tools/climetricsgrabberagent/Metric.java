package de.holisticon.tools.climetricsgrabberagent;

/**
 * Metric bean.
 * @author Tobias Gindler, Holisticon AG
 */
public class Metric {

    private final String name;
    private final Object value;
    private final long timestamp;

    public Metric (
            final String name,
            final String value,
            final long timestamp
    ) {

        this.name = name;
        this.value = value;
        this.timestamp = timestamp;

    }

    public String getName() {
        return name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Object getValue() {
        return value;
    }
}
