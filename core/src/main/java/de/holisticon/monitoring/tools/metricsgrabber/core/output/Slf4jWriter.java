package de.holisticon.monitoring.tools.metricsgrabber.core.output;

import de.holisticon.monitoring.tools.metricsgrabber.core.Metric;

import java.util.List;

/**
 *
 * Writer for SLF4J.
 *
 * @author Tobias Gindler on 18.07.14.
 */
public class Slf4jWriter extends AbstractOutputWriter{

    private String nodeIdentifier = null;

    @Override
    public void writeMetrics(List<Metric> metrics) {
        for (Metric metric : metrics) {
            logger.info(
                    "Metric [" + metric.getTimestamp() + "]: " + nodeIdentifier + "." + metric.getName() + " = "+metric.getValue()
            );
        }
    }

    @Override
    public void init(String nodeIdentifier) throws InitParameterException {
        this.nodeIdentifier = nodeIdentifier;
    }
}
