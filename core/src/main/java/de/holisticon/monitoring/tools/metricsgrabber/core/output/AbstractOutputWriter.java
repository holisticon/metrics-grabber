package de.holisticon.monitoring.tools.metricsgrabber.core.output;

import de.holisticon.monitoring.tools.metricsgrabber.core.common.AbstractInitializeable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class for all output writers.
 * Provides access to configuration.
 *
 * @author Tobias Gindler, Holisticon AG
 */
public abstract class AbstractOutputWriter extends AbstractInitializeable implements OutputWriter {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractOutputWriter.class);

}
