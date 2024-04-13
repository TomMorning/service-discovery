package com.tangmaolin.servicediscovery.scheduler;

import com.tangmaolin.servicediscovery.registry.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ServiceCleanupScheduler {
    private static final long SCHEDULED_CLEANUP_INTERVAL = 5000; // 5 seconds
    private static final long SERVICE_EXPIRATION_TIME = 30000; // 30 seconds
    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    @Qualifier("inMemoryServiceRegistry")
    private ServiceRegistry serviceRegistry;

    @PostConstruct
    public void init() {
        // Initialize the ScheduledExecutorService with one thread
        this.scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

        // Schedule the cleanup task
        this.scheduledExecutorService.scheduleAtFixedRate(this::cleanupServices,
                0, SCHEDULED_CLEANUP_INTERVAL, TimeUnit.MILLISECONDS);
    }

    public void cleanupServices() {
        serviceRegistry.cleanupExpiredServices(SERVICE_EXPIRATION_TIME);
    }

    @PreDestroy
    public void destroy() {
        // Shutdown the executor when the context is being destroyed
        if (this.scheduledExecutorService != null && !this.scheduledExecutorService.isShutdown()) {
            this.scheduledExecutorService.shutdown();
        }
    }
}
