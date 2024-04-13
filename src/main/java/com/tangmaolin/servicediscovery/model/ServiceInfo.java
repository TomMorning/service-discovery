package com.tangmaolin.servicediscovery.model;

/**
 * @author tangmaolin02
 */
public class ServiceInfo {
    private String serviceName;
    private String address;
    private volatile long lastActive;

    public ServiceInfo(String serviceName, String address) {
        this.serviceName = serviceName;
        this.address = address;
        this.lastActive = System.currentTimeMillis();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getAddress() {
        return address;
    }

    public long getLastActive() {
        return lastActive;
    }

    public void setLastActive(long lastActive) {
        this.lastActive = lastActive;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ServiceInfo that = (ServiceInfo) obj;
        return address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }
}
