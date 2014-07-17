package de.holisticon.tools.climetricsgrabberagent.output;

import de.holisticon.tools.climetricsgrabberagent.common.AbstractInitializeable;
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
