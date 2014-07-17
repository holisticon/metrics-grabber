package de.holisticon.tools.climetricsgrabberagent;

import de.holisticon.tools.climetricsgrabberagent.config.Configuration;
import de.holisticon.tools.climetricsgrabberagent.config.MetricConfiguration;
import de.holisticon.tools.climetricsgrabberagent.config.OutputWriterConfig;
import de.holisticon.tools.climetricsgrabberagent.input.CliReader;
import de.holisticon.tools.climetricsgrabberagent.tools.NamedThreadFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.as.cli.CliInitializationException;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandContextFactory;
import org.jboss.as.cli.CommandLineException;
import org.jboss.dmr.ModelNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Metrics grabber agent for JBoss As 7+.
 * Grabbing metrics via cli interface has a drastically smaller memory footprint than querying metrics via JMX.
 * So this agent helps to reduce costs of monitoring your jboss as or eap applications.
 *
 * @author Tobias Gindler, Holisticon AG
 */
public class CliMetricsGrabberAgent {

    private static final Logger logger = LoggerFactory.getLogger(CliMetricsGrabberAgent.class);

    private static ScheduledExecutorService metricGrabber;


    private static OutputWriter[] getOutputWriters(final Configuration configuration) {

        List<OutputWriter> list = new ArrayList<OutputWriter>();

        for (OutputWriterConfig outputWriterConfig : configuration.getOutputWriterConfigs()) {

            try {
                OutputWriter outputWriter = (OutputWriter)Class.forName(outputWriterConfig.getOutputWriterClassName()).newInstance();
                outputWriter.setInitParameters(outputWriterConfig.getInitParameters());
                outputWriter.init(configuration.getNodeName());

                list.add(outputWriter);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        return list.toArray(new OutputWriter[list.size()]);

    }

    /**
     * Entry point for metrics agent
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {

        // read configuration
        ObjectMapper mapper = new ObjectMapper();
        Configuration configuration = mapper.readValue(new File(args[0]), Configuration.class);

        metricGrabber =  Executors.newScheduledThreadPool(configuration.getNrOfMetricThreads(), new NamedThreadFactory("cli-metrics-grabber-", true));

        final OutputWriter[] outputWriters = getOutputWriters(configuration);




        for (final MetricConfiguration metricConfiguration : configuration.getMetrics()) {

            metricGrabber.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {

                    MetricReader metricReader = new CliReader();


                    List<Metric> metrics = metricReader.readMetrics(metricConfiguration) ;
                    for (Metric metric : metrics) {
                        logger.info("[" + metric.getTimestamp() +"] : '" + metric.getName() +  "' := '" +  metric.getValue()+"'");
                    }

                    for (OutputWriter outputWriter : outputWriters) {
                        outputWriter.writeMetrics(metrics);
                    }


                }
            }, 1, metricConfiguration.getQueryIntervalInSeconds(), TimeUnit.SECONDS);




        }

        while (true) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ;
        }



        //System.out.println("config - ");



    }

}
