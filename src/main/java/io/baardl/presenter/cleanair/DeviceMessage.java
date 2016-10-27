package io.baardl.presenter.cleanair;

public class DeviceMessage {
    private final String deviceId;
    private final String payload;

    public DeviceMessage(String deviceId, String payload) {
        if (deviceId == null || deviceId.isEmpty()){
            throw new IllegalArgumentException("DeviceId must have a value");
        }
        this.deviceId = deviceId;
        this.payload = payload;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getPayload() {
        return payload;
    }

    public boolean isValid() {
        return deviceId != null && !deviceId.isEmpty();
    }

    @Override
    public String toString() {
        return "DeviceMessage{" +
                "deviceId='" + deviceId + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
