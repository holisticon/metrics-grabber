package de.holisticon.monitoring.tools.metricsgrabber.core.output;

import de.holisticon.monitoring.tools.metricsgrabber.core.Metric;
import de.holisticon.monitoring.tools.metricsgrabber.core.tools.SocketWriter;
import de.holisticon.monitoring.tools.metricsgrabber.core.tools.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Output writer for graphite
 *
 * @author Tobias Gindler, Holisticon AG
 */
public class GraphiteWriter extends AbstractOutputWriter {


    public static final String INIT_PARAMETER_NAME_IP = "host";
    public static final String INIT_PARAMETER_NAME_PORT = "port";
    public static final String INIT_PARAMETER_NAME_CHARSET = "charset";

    private static final Logger logger = LoggerFactory.getLogger(GraphiteWriter.class);

    private String host;
    private Integer port;
    private Charset charset;
    private String nodeIdentifier;



    /**
     * Creates a {@link java.lang.StringBuffer} that contains all metrics.
     *
     * @param nodeIdentifier the node identifier used to prefix metric names
     * @param metrics        the metrics to process
     * @return
     */
    String convertMetricsToString(final String nodeIdentifier, final List<Metric> metrics) {

        StringBuilder metricLines = new StringBuilder();

        for (Metric metric : metrics) {

            metricLines.append(nodeIdentifier).append(".").append(metric.getName()).append(" ").append(StringUtil.replaceWhitespaces(metric.getValue().toString())).append(" ").append(metric.getTimestamp()).append("\n");

        }

        return metricLines.toString();

    }

    @Override
    public void writeMetrics(List<Metric> metrics) {
        SocketWriter socketWriter = null; //new SocketWriter(null, null);

        try {

            socketWriter = new SocketWriter(new InetSocketAddress(host, port), charset);

            socketWriter.write(convertMetricsToString(nodeIdentifier, metrics));

            socketWriter.flush();

        } catch (Exception e) {
            logger.error("Couldn't write metrics to Graphite", e);
        } finally {
            try {
                socketWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void init(String nodeIdentifier) throws InitParameterException {

        this.nodeIdentifier = nodeIdentifier;
        this.host = this.getInitParameter(INIT_PARAMETER_NAME_IP);
        this.port = this.getInitParameterAsInteger(INIT_PARAMETER_NAME_PORT);
        String charsetString = this.getInitParameter(INIT_PARAMETER_NAME_CHARSET);
        try {
            this.charset = Charset.forName(charsetString);
        } catch (Exception e) {
            throw new InitParameterException("Can not convert init parameter '" + INIT_PARAMETER_NAME_CHARSET + "' with value '" + charsetString + "' to Integer ");
        }
    }
}
