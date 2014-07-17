package de.holisticon.monitoring.tools.metricsgrabber.core.common;

import de.holisticon.monitoring.tools.metricsgrabber.core.config.InitParameter;

/**
 * Created by TGI on 17.07.14.
 */
public interface Initializable {

    /**
     * Sets init parameters. Must normally be called before the init method.
     * @param initParameters the init parameters to set
     */
    void setInitParameters (final InitParameter[] initParameters);

}
