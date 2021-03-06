package de.holisticon.monitoring.tools.metricsgrabber.core;

import de.holisticon.monitoring.tools.metricsgrabber.core.config.Configuration;
import de.holisticon.monitoring.tools.metricsgrabber.core.config.MetricConfiguration;
import de.holisticon.monitoring.tools.metricsgrabber.core.config.MetricReaderConfig;
import de.holisticon.monitoring.tools.metricsgrabber.core.config.OutputWriterConfig;
import de.holisticon.monitoring.tools.metricsgrabber.core.input.MetricReader;
import de.holisticon.monitoring.tools.metricsgrabber.core.output.OutputWriter;
import de.holisticon.monitoring.tools.metricsgrabber.core.tools.ActivateableFilter;
import de.holisticon.monitoring.tools.metricsgrabber.core.tools.NamedThreadFactory;
import de.holisticon.monitoring.tools.metricsgrabber.core.tools.ReflectionUtil;
import org.jboss.as.cli.impl.CliShutdownHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Base class for the metrics grabber.
 *
 * @author Tobias Gindler, Holisticon AG on 17.07.14.
 */
public class MetricsGrabber {

    private static final Logger logger = LoggerFactory.getLogger(MetricsGrabber.class);

    private ScheduledExecutorService metricGrabberExecutorService;

    private final Configuration configuration;


    public MetricsGrabber(final Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Entry point for metrics agent
     */
    public void start() {


        metricGrabberExecutorService = Executors.newScheduledThreadPool(configuration.getNrOfMetricThreads(), new NamedThreadFactory("cli-metrics-grabber-", true));

        metricGrabberExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                logger.info("RUN FULL GC and FINALIZATION");

                /*
                System.gc();
                System.runFinalization();
                */

                try {
                    Field field = CliShutdownHook.class.getDeclaredField("handlers");
                    field.setAccessible(true);
                    List list = (List) field.get(null);
                    list.clear();
                    /*
                    int length = list.size();
                    for (int i = 0; i < length; i++) {

                        try {
                            Object obj = list.remove(0);
                            Method method = obj.getClass().getDeclaredMethod("shutdown");
                            method.setAccessible(true);
                            method.invoke(obj);
                        } catch (Throwable t) {
                        }
                    }
                    */
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, 1, 5, TimeUnit.MINUTES);

        // get output writer configuration
        final OutputWriter[] outputWriters = getOutputWriters();

        // Now start the metric readers
        for (MetricReaderConfig metricReaderConfig : ActivateableFilter.filterActivateableArray(configuration.getMetricReaders(), MetricReaderConfig.class)) {

            // now create metric reader via reflection
            final MetricReader metricReader = ReflectionUtil.createInstance(metricReaderConfig.getType(), MetricReader.class);
            if (metricReader == null) {

                logger.error("Couldn't create configured metric reader with type : {}", metricReaderConfig.getType());

            } else {

                logger.info("Metric reader with type {} was successfully created.", metricReaderConfig.getType());

                for (final MetricConfiguration metricConfiguration : ActivateableFilter.filterActivateableArray(metricReaderConfig.getMetrics(), MetricConfiguration.class)) {

                    metricGrabberExecutorService.scheduleWithFixedDelay(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                List<Metric> metrics = metricReader.readMetrics(metricConfiguration);

                                if (metrics != null && metrics.size() > 0) {

                                    for (OutputWriter outputWriter : outputWriters) {
                                        outputWriter.writeMetrics(metrics);
                                    }
                                }

                            } catch (Exception e) {
                                logger.warn("Error happened during:");
                            }

                        }
                    }, 1, metricConfiguration.getQueryIntervalInSeconds(), TimeUnit.SECONDS);


                }
            }

        }

    }

    public void destroy() {
        metricGrabberExecutorService.shutdownNow();
        try {
            metricGrabberExecutorService.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // do Nothing
        }
    }


    private OutputWriter[] getOutputWriters() {

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
}
