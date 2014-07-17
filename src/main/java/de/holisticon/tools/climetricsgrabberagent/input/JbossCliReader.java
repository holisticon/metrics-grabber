package de.holisticon.tools.climetricsgrabberagent.input;

import de.holisticon.tools.climetricsgrabberagent.Metric;
import de.holisticon.tools.climetricsgrabberagent.config.MetricConfiguration;
import org.jboss.as.cli.CliInitializationException;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandContextFactory;
import org.jboss.dmr.ModelNode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Tobias Gindler, Holistion AG on 17.07.14.
 */
public class JbossCliReader extends AbstractMetricReader{


    @Override
    public List<Metric> readMetrics(MetricConfiguration metricConfiguration) {

        logger.info("query metrics '{}'", metricConfiguration.getMetricQueryString());

        final String query = metricConfiguration.getMetricQueryString();

        List<Metric> results = new ArrayList<Metric>();

        // Initialize the CLI context
        final CommandContext ctx;
        try {
            ctx = CommandContextFactory.getInstance().newCommandContext();
        } catch(CliInitializationException e) {
            throw new IllegalStateException("Failed to initialize CLI context", e);
        }

        try {

            long timestamp = Calendar.getInstance().getTimeInMillis();

            // connect to the server controller
            ctx.connectController();

            // execute commands and operations
            ModelNode request = ctx.buildRequest(query);

            ModelNode response = ctx.getModelControllerClient().execute(request);

            boolean requestWasSuccessful = "success".equals(response.get("outcome").asString());

            if (requestWasSuccessful) {

                ModelNode resultNode = response.get("result");

                if (metricConfiguration.getMetricsAsSet() != null && metricConfiguration.getMetricsAsSet().size() >0) {

                    for (String key : metricConfiguration.getAttributes()) {
                        if (metricConfiguration.getMetricsAsSet().contains(key)) {
                            results.add(new Metric(metricConfiguration.getMetricPrefix()+"."+key,resultNode.get(key).asString(),timestamp));
                        }
                    }
                } else {
                    for (String key : resultNode.keys()) {
                        results.add(new Metric(metricConfiguration.getMetricPrefix()+"."+key,resultNode.get(key).asString(),timestamp));
                    }
                }

            }

            //for (String key : modelNode.keys()) {
            //String value =  modelNode.get(key);
            //}

            System.out.println("");

        } catch (Exception e) {
            // the operation or the command has failed
            logger.error("Error occurred during execution of query '" + query + "'", e);
        }  finally {
            // terminate the session and
            // close the connection to the controller
            try {
                if (!ctx.isTerminated()) {
                    ctx.terminateSession();
                }
            } catch (Exception e ) {
                logger.debug(e.getMessage(),e);
            }
        }


        return results;

    }

}