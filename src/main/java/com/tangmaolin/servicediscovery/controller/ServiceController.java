package com.tangmaolin.servicediscovery.controller;

/**
 * @author tangmaolin02
 */
import com.tangmaolin.servicediscovery.registry.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServiceController {

    @Autowired
    @Qualifier("inMemoryServiceRegistry")
    private ServiceRegistry serviceRegistry;

    /**
     * Register a service with the service registry
     * @param serviceName The name of the service
     * @param address The address of the service
     * @return
     */
    @PostMapping("/register")
    public String registerService(@RequestParam String serviceName, @RequestParam String address) {
        serviceRegistry.registerService(serviceName, address);
        return "Service registered successfully";
    }

    /**
     * Discover services registered with the service registry
     * @param serviceName The name of the service
     * @return  The addresses of the service instances
     */
    @GetMapping("/discover")
    public List<String> discoverService(@RequestParam String serviceName) {
        return serviceRegistry.discoverService(serviceName);
    }

    /**
     * Keep a service alive
     * @param address The address of the service
     * @return
     */
    @PostMapping("/heartbeat")
    public String keepAlive(@RequestParam String address) {
        serviceRegistry.keepAlive(address);
        return "Heartbeat received";
    }
}
