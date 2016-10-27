package io.baardl.presenter.cleanair;

import io.baardl.presenter.application.ApplicationAdapter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class DeviceMessageService {
    private static final Logger log = getLogger(DeviceMessageService.class);

    private final ApplicationAdapter applicationApi;

    @Autowired
    public DeviceMessageService(ApplicationAdapter applicationApi) {
        this.applicationApi = applicationApi;
    }

    public DeviceMessage findDeviceState(String deviceId) {
        return applicationApi.findDeviceState(deviceId);
    }
}
