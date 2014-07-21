package de.holisticon.monitoring.tools.metricsgrabber.core.input;

import de.holisticon.monitoring.tools.metricsgrabber.core.Metric;
import de.holisticon.monitoring.tools.metricsgrabber.core.common.Initializable;
import de.holisticon.monitoring.tools.metricsgrabber.core.config.MetricConfiguration;

import java.util.List;

/**
 * Interface for metric readers.
 *
 * @author Tobias Gindler, Holisticon AG on 17.07.14.
 */
public interface MetricReader extends Initializable {

    /**
     * Reads metric for a specific metric configuration.
     *
     * @return the attributes read for the passed metric configuration
     */
    List<Metric> readMetrics(final MetricConfiguration metricConfiguration);


}
