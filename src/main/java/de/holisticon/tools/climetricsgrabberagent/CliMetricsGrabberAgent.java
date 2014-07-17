package de.holisticon.tools.climetricsgrabberagent;

import de.holisticon.tools.climetricsgrabberagent.config.Configuration;
import de.holisticon.tools.climetricsgrabberagent.config.MetricConfiguration;
import de.holisticon.tools.climetricsgrabberagent.config.MetricReaderConfig;
import de.holisticon.tools.climetricsgrabberagent.config.OutputWriterConfig;
import de.holisticon.tools.climetricsgrabberagent.input.MetricReader;
import de.holisticon.tools.climetricsgrabberagent.output.OutputWriter;
import de.holisticon.tools.climetricsgrabberagent.tools.ActivateableFilter;
import de.holisticon.tools.climetricsgrabberagent.tools.JsonUtil;
import de.holisticon.tools.climetricsgrabberagent.tools.NamedThreadFactory;
import de.holisticon.tools.climetricsgrabberagent.tools.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

        for (OutputWriterConfig outputWriterConfig : ActivateableFilter.filterActivateableArray(configuration.getOutputWriterConfigs(), OutputWriterConfig.class)) {


            OutputWriter outputWriter = ReflectionUtil.createInstance(outputWriterConfig.getOutputWriterClassName(), OutputWriter.class);
            if (outputWriter == null) {
                logger.error("Couldn't create configured output writer with type : {}", outputWriterConfig.getOutputWriterClassName());
            } else {

                try {

                    outputWriter.setInitParameters(outputWriterConfig.getInitParameters());
                    outputWriter.init(configuration.getNodeName());

                    list.add(outputWriter);

                } catch (Exception e) {
                    logger.error("Couldn't create configured output writer with type : {}", outputWriterConfig.getOutputWriterClassName(), e);
                }

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

        Configuration configuration = JsonUtil.readJsonFromFile(args[0], Configuration.class);


        metricGrabber = Executors.newScheduledThreadPool(configuration.getNrOfMetricThreads(), new NamedThreadFactory("cli-metrics-grabber-", true));

        final OutputWriter[] outputWriters = getOutputWriters(configuration);


        for (MetricReaderConfig metricReaderConfig : ActivateableFilter.filterActivateableArray(configuration.getMetricReaders(), MetricReaderConfig.class)) {

            // now create metric reader via reflection
            final MetricReader metricReader = ReflectionUtil.createInstance(metricReaderConfig.getType(), MetricReader.class);
            if (metricReader == null) {

                logger.error("Couldn't create configured metric reader with type : {}", metricReaderConfig.getType());

            } else {

                logger.info("Metric reader with type {} was successfully created.", metricReaderConfig.getType());

                for (final MetricConfiguration metricConfiguration : ActivateableFilter.filterActivateableArray(metricReaderConfig.getMetrics(), MetricConfiguration.class)) {

                    metricGrabber.scheduleWithFixedDelay(new Runnable() {
                        @Override
                        public void run() {

                            List<Metric> metrics = metricReader.readMetrics(metricConfiguration);

                            if (metrics != null && metrics.size() > 0) {

                                for (Metric metric : metrics) {
                                    logger.info("[" + metric.getTimestamp() + "] : '" + metric.getName() + "' := '" + metric.getValue() + "'");
                                }

                                for (OutputWriter outputWriter : outputWriters) {
                                    outputWriter.writeMetrics(metrics);
                                }
                            }


                        }
                    }, 1, metricConfiguration.getQueryIntervalInSeconds(), TimeUnit.SECONDS);


                }
            }
        }

        while (true) {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ;
        }


        //System.out.println("config - ");


    }

}
