package io.baardl.presenter.application;

import com.github.kevinsawicki.http.HttpRequest;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import io.baardl.presenter.util.StringConv;
import org.slf4j.Logger;

import java.net.URI;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.slf4j.LoggerFactory.getLogger;

class CommandGetDeviceState extends HystrixCommand<String> {
    private static final Logger log = getLogger(CommandGetDeviceState.class);
    private final String uriString;
    private final String token;

    CommandGetDeviceState(URI serviceUri, String token, String deviceId) {
        super(HystrixCommandGroupKey.Factory.asKey("GetDeviceState"));
        this.token = token;
        this.uriString = serviceUri.toString() + "/" + deviceId;
    }

    @Override
    protected String run() {
        HttpRequest request = HttpRequest.get(uriString).authorization("Bearer " + token);
        byte[] responseBody = request.bytes();
        int statusCode = request.code();
        String responseAsText = StringConv.UTF8(responseBody);
        if(HTTP_OK == statusCode) {
            return responseAsText;
        } else {
            log.warn("Failed to fetch data from {}. Status {}, response {}", uriString, statusCode, responseAsText);
            //Throw exception to let Hystrix know the call has failed
            throw new RuntimeException("Failed to fetch data from " + uriString
                    + " . Status " + statusCode + ", response " + responseAsText);
        }
    }

    @Override
    protected String getFallback() {
        log.warn("Fallback - serviceUri={}", uriString);
        return "";
    }

}
