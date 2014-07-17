package de.holisticon.tools.climetricsgrabberagent;

import de.holisticon.tools.climetricsgrabberagent.config.InitParameter;
import de.holisticon.tools.climetricsgrabberagent.output.InitParameterException;

import java.util.List;

/**
 * Interface for output writers.
 * @author Tobias Gindler, Holisticon AG
 */
public interface OutputWriter {

    /**
     * Writes metrics via output writer.
     * @param metrics
     */
    void writeMetrics( List<Metric> metrics);

    /**
     * Sets init parameters. Must normally be called before the init method.
     * @param initParameters
     */
    void setInitParameters (final InitParameter[] initParameters);

    /**
     * Initializes OutputWriter
     * @param nodeIdentifier the node identifier used to prefix metrics data
     */
    void init(final String nodeIdentifier) throws InitParameterException;

}
