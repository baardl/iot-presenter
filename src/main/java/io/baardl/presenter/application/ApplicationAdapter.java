package io.baardl.presenter.application;

import io.baardl.presenter.cleanair.DeviceMessage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class ApplicationAdapter {
    private static final Logger log = getLogger(ApplicationAdapter.class);

    private final URI deviceServiceUri;
    private String tokenUrl;
    private String clientId;
    private String clientSecret;

    @Autowired
    public ApplicationAdapter(@Value("${applicationApi.deviceUri}") String deviceServiceUri,
                              @Value("${identity.tokenUrl}") String tokenUrl,
                              @Value("${identity.clientId}") String clientId,
                              @Value("${identity.clientSecret}") String clientSecret) throws Exception {
        this.tokenUrl = tokenUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        try {
            this.deviceServiceUri = new URI(deviceServiceUri);
        } catch (URISyntaxException e) {
            log.error("Failed to get ServiceUri for ApplicationApi's device. Input was [" + deviceServiceUri + "]");
            throw new Exception(e);
        }
    }

    public DeviceMessage findDeviceState(String deviceId) {
        Optional<String> accessToken = new CommandGetServiceAccountAccess(tokenUrl, clientId, clientSecret).execute();
        if(accessToken.isPresent()) {
            String messageBody = new CommandGetDeviceState(deviceServiceUri, accessToken.get(), deviceId).execute();
            return new DeviceMessage(deviceId, messageBody);
        } else{
            return new DeviceMessage(deviceId, "");
        }
    }
}
