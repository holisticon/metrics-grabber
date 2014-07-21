package de.holisticon.monitoring.tools.metricsgrabber.core.input;

import de.holisticon.monitoring.tools.metricsgrabber.core.Metric;
import de.holisticon.monitoring.tools.metricsgrabber.core.config.MetricConfiguration;
import org.jboss.as.cli.CliInitializationException;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandContextFactory;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
 *
 * @author Tobias Gindler, Holistion AG on 17.07.14.
 */
public class JBossCliReader extends AbstractMetricReader {


    @Override
    public List<Metric> readMetrics(MetricConfiguration metricConfiguration) {

        logger.info("query metrics '{}'", metricConfiguration.getMetricQueryString());

        final String query = metricConfiguration.getMetricQueryString();

        List<Metric> results = new ArrayList<Metric>();

        // Initialize the CLI context
        final CommandContext ctx;
        try {
            ctx = CommandContextFactory.getInstance().newCommandContext();
        } catch (CliInitializationException e) {
            logger.error("Failed to initialize CLI context", e);
            throw new IllegalStateException("Failed to initialize CLI context", e);
        }

        try {

            long timestamp = Calendar.getInstance().getTimeInMillis();

            // connect to the server controller
            ctx.connectController();

            // execute commands and operations
            ModelNode request = ctx.buildRequest(query);
            ModelControllerClient modelControllerClient = ctx.getModelControllerClient();
            ModelNode response = modelControllerClient.execute(request);


            boolean requestWasSuccessful = "success".equals(response.get("outcome").asString());

            if (requestWasSuccessful) {

                ModelNode resultNode = response.get("result");

                if (metricConfiguration.getMetricsAsSet() != null && metricConfiguration.getMetricsAsSet().size() > 0) {

                    for (String key : metricConfiguration.getAttributes()) {
                        if (metricConfiguration.getMetricsAsSet().contains(key)) {
                            results.add(new Metric(metricConfiguration.getMetricPrefix() + "." + key, resultNode.get(key).asString(), timestamp));
                        }
                    }
                } else {
                    for (String key : resultNode.keys()) {
                        results.add(new Metric(metricConfiguration.getMetricPrefix() + "." + key, resultNode.get(key).asString(), timestamp));
                    }
                }

            }

            // cleanup
            request.clear();
            response.clear();

            // close model controller client
            modelControllerClient.close();




        } catch (Exception e) {
            // the operation or the command has failed
            logger.error("Error occurred during execution of query '" + query + "'", e);
        } finally {
            // terminate the session and
            // close the connection to the controller
            try {

                // disconnect
                ctx.disconnectController();

                if (!ctx.isTerminated()) {
                    // WTF : Terminating the session leads to exceptions, must check if session is terminated implicit to avoid resource leak
                    ctx.terminateSession();
                }



            } catch (Exception e) {
                logger.debug(e.getMessage(), e);
            }
        }


        return results;

    }

}
