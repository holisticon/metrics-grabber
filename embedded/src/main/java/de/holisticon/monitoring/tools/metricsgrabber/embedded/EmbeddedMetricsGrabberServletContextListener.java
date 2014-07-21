package de.holisticon.monitoring.tools.metricsgrabber.embedded;

import de.holisticon.monitoring.tools.metricsgrabber.core.MetricsGrabber;
import de.holisticon.monitoring.tools.metricsgrabber.core.config.Configuration;
import de.holisticon.monitoring.tools.metricsgrabber.core.tools.JsonUtil;
import de.holisticon.monitoring.tools.metricsgrabber.core.tools.ReflectionUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.ws.handler.MessageContext;

/**
 * Servlet Context Listener that starts and destroys the embedded metrics grabber.
 * @author Tobias Gindler, Holisticon AG on 17.07.14.
 */
public class EmbeddedMetricsGrabberServletContextListener implements ServletContextListener {

    public static final String METRIC_GRABBER_CONFIG_NAME = "/metricGrabberConfig.json";

    private MetricsGrabber metricsGrabber;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        try {
            servletContextEvent.getServletContext().log("Starting Metrics Grabber");

            Configuration configuration = JsonUtil.readJsonFromResource(METRIC_GRABBER_CONFIG_NAME, Configuration.class);

            metricsGrabber = new MetricsGrabber(configuration);

            metricsGrabber.start();

            servletContextEvent.getServletContext().log("Starting Metrics Grabber started successfully");
        } catch (Exception e) {

            servletContextEvent.getServletContext().log("Starting Metrics Grabber failed",e);
            throw new RuntimeException("Metrics Grabber - error during startup",e);

        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        metricsGrabber.destroy();
    }

}
