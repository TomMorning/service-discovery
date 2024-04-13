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

    @PostMapping("/register")
    public String registerService(@RequestParam String serviceName, @RequestParam String address) {
        serviceRegistry.registerService(serviceName, address);
        return "Service registered successfully";
    }

    @GetMapping("/discover")
    public List<String> discoverService(@RequestParam String serviceName) {
        return serviceRegistry.discoverService(serviceName);
    }

    @PostMapping("/heartbeat")
    public String keepAlive(@RequestParam String address) {
        serviceRegistry.keepAlive(address);
        return "Heartbeat received";
    }
}
