package de.holisticon.tools.climetricsgrabberagent.output;

import de.holisticon.tools.climetricsgrabberagent.OutputWriter;
import de.holisticon.tools.climetricsgrabberagent.config.InitParameter;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract base class for all output writers.
 * Provides access to configuration.
 *
 * @author Tobias Gindler, Holisticon AG
 */
public abstract class AbstractOutputWriter implements OutputWriter {

    private Map<String,String> initParameterMap;

    public void setInitParameters (final InitParameter[] initParameters) {

        Map<String,String> tmpMap = new HashMap<String, String>();

        if (initParameters != null) {
            for (InitParameter initParameter: initParameters) {
                tmpMap.put(initParameter.getName(), initParameter.getValue());
            }
        }

        this.initParameterMap = tmpMap;

    }

    protected String getInitParameter (final String parameterName) {

        String returnValue = null;

        if (this.initParameterMap != null) {
            returnValue = this.initParameterMap.get(parameterName);
        }

        return returnValue;
    }

    public Integer getInitParameterAsInteger(final String parameterName) throws InitParameterException{

        String value = this.getInitParameter(parameterName);

        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new InitParameterException("Can not convert init parameter '" + parameterName + "' with value '" + value + "' to Integer ");
        }
    }

    public Long getInitParameterAsLong(final String parameterName) throws InitParameterException{

        String value = this.getInitParameter(parameterName);

        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            throw new InitParameterException("Can not convert init parameter '" + parameterName + "' with value '" + value + "' to Long ");
        }
    }


}
