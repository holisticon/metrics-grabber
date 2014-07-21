package de.holisticon.monitoring.tools.metricsgrabber.agent;

import de.holisticon.monitoring.tools.metricsgrabber.core.MetricsGrabber;
import de.holisticon.monitoring.tools.metricsgrabber.core.config.Configuration;
import de.holisticon.monitoring.tools.metricsgrabber.core.tools.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Metrics grabber agent for JBoss As 7+.
 * Grabbing metrics via cli interface has a drastically smaller memory footprint than querying metrics via JMX.
 * So this agent helps to reduce costs of monitoring your jboss as or eap applications.
 *
 * @author Tobias Gindler, Holisticon AG
 */
public class CliMetricsGrabberAgent {

    private static final Logger logger = LoggerFactory.getLogger(CliMetricsGrabberAgent.class);


    private static void init() {
        // Now get rid of the annoying system.out and system.err of jboss cli api !!!
        OutputStream nullWriter = new OutputStream() {
            @Override
            public void write(byte[] b) throws IOException {
                super.write(b);
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
            }

            @Override
            public void flush() throws IOException {
            }

            @Override
            public void close() throws IOException {
            }

            @Override
            public void write(int b) throws IOException {

            }
        };

        System.setErr(new PrintStream(nullWriter));
    }


    /**
     * Entry point for metrics agent
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            System.out.println("You have defined no configuration file parameter:\njava -jar metric-grabber-agent <configfile.json>");
            System.exit(-1);
        }

        init();

        // get configuration
        Configuration configuration = JsonUtil.readJsonFromFile(args[0], Configuration.class);

        // start the metrics grabber
        MetricsGrabber metricsGrabber = new MetricsGrabber(configuration);
        metricsGrabber.start();


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
