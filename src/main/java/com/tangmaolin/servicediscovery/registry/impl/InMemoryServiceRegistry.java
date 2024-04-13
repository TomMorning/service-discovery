package com.tangmaolin.servicediscovery.registry.impl;

/**
 * @author tangmaolin02
 */
import com.tangmaolin.servicediscovery.model.ServiceInfo;
import com.tangmaolin.servicediscovery.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

@Component
@Qualifier("inMemoryServiceRegistry")
public class InMemoryServiceRegistry implements ServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryServiceRegistry.class);
    private ConcurrentHashMap<String, Set<ServiceInfo>> services = new ConcurrentHashMap<>();

    @Override
    public void registerService(String serviceName, String address) {
        services.computeIfAbsent(serviceName, k -> new CopyOnWriteArraySet<>())
                .add(new ServiceInfo(serviceName, address));
    }

    @Override
    public List<String> discoverService(String serviceName) {
        return services.getOrDefault(serviceName, new CopyOnWriteArraySet<>())
                .stream()
                .map(ServiceInfo::getAddress)
                .collect(Collectors.toList());
    }

    @Override
    public void keepAlive(String address) {
        services.values().forEach(set -> set.forEach(info -> {
            if (info.getAddress().equals(address)) {
                info.setLastActive(System.currentTimeMillis());
            }
        }));
    }

    @Override
    public void cleanupExpiredServices(long expirationTimeInMs) {
        long currentTime = System.currentTimeMillis();
        for (String serviceName : services.keySet()) {
            Set<ServiceInfo> serviceSet = services.get(serviceName);
            if (serviceSet != null) {
                Iterator<ServiceInfo> iterator = serviceSet.iterator();
                while (iterator.hasNext()) {
                    ServiceInfo info = iterator.next();
                    if (currentTime - info.getLastActive() > expirationTimeInMs) {
                        iterator.remove();
                        notifyServiceExpiration(info); // 通知服务过期
                        logger.info("Removed expired service: {} from {}", info.getAddress(), serviceName);
                    }
                }
                if (serviceSet.isEmpty()) {
                    services.remove(serviceName); // 如果服务集合为空，从map中删除键
                    logger.info("Removed all services for {}", serviceName);
                }
            }
        }
    }

    private void notifyServiceExpiration(ServiceInfo serviceInfo) {
        // TODO: 实现通知逻辑
        logger.info("Notifying about expired service: {}", serviceInfo.getAddress());
    }
}
