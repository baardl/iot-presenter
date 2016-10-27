package io.baardl.presenter.cleanair;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/device")
public class DeviceResource {
    private static final Logger log = getLogger(DeviceResource.class);

    private DeviceMessageService deviceMessageService;


    @Autowired
    public DeviceResource(DeviceMessageService deviceMessageService) {
        this.deviceMessageService = deviceMessageService;
    }

    @RequestMapping(value = "/{deviceId}/message", method = RequestMethod.GET)
    DeviceMessage latestMessage(@PathVariable String deviceId) {
        log.trace("Find latestMessage for deviceId {}", deviceId);
        DeviceMessage deviceMessage = deviceMessageService.findDeviceState(deviceId);
        if (deviceMessage != null) {
            log.trace("Found message {} for deviceId {}", deviceMessage, deviceId);
        } else {
            deviceMessage = new DeviceMessage(deviceId, stubMessageText);
        }
        return deviceMessage;

    }

    private final String stubMessageText = "{\"state\":{\"desired\":{\"sensorid\":\"Temperature\",\"value\":\"45\",\"demo\":true}},\"metadata\":{\"desired\":{\"sensorid\":{\"timestamp\":1470825702},\"value\":{\"timestamp\":1470825702}}},\"version\":108,\"timestamp\":1470825702}";
}
