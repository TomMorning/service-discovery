package com.tangmaolin.servicediscovery.registry;

/**
 * @author tangmaolin02
 */

import java.util.List;

public interface ServiceRegistry {
    /**
     * Register a service with the service registry
     * @param serviceName The name of the service
     * @param address The address of the service
     */
    void registerService(String serviceName, String address);

    /**
     * Discover services registered with the service registry
     * @param serviceName The name of the service
     * @return The addresses of the service instances
     */
    List<String> discoverService(String serviceName);

    /**
     * Keep a service alive
     * @param address The address of the service
     */
    void keepAlive(String address);

    /**
     * Cleanup expired services
     * @param expirationTimeInMs
     */
    void cleanupExpiredServices(long expirationTimeInMs);
}