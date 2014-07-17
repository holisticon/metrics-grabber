package de.holisticon.monitoring.tools.metricsgrabber.core.output;

import de.holisticon.monitoring.tools.metricsgrabber.core.common.Initializable;
import de.holisticon.monitoring.tools.metricsgrabber.core.Metric;

import java.util.List;

/**
 * Interface for output writers.
 * @author Tobias Gindler, Holisticon AG
 */
public interface OutputWriter extends Initializable{

    /**
     * Writes metrics via output writer.
     * @param metrics
     */
    void writeMetrics( List<Metric> metrics);

    /**
     * Initializes OutputWriter
     * @param nodeIdentifier the node identifier used to prefix metrics data
     */
    void init(final String nodeIdentifier) throws InitParameterException;

}
