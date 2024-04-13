# Simple Service Discovery Project

## Overview
The ServiceDiscovery project provides a simple in-memory service registry to facilitate service registration and discovery in dynamic service environments. It supports heartbeat mechanisms to maintain active registrations and automatically cleans up services that fail to send heartbeats within a predefined interval.

## Features
- **Service Registration**: Allows services to register their network address under a specific service name.
- **Service Discovery**: Enables clients to retrieve addresses by service name.
- **Heartbeat Maintenance**: Services must send periodic heartbeats to maintain their registration; services that fail to do so are automatically unregistered.
- **Notification on Expiry**: Logs and potentially notifies when services are unregistered due to missed heartbeats.

## Project Structure


- **registry/**: Contains the `ServiceRegistry` interface and its implementations.
    - `ServiceRegistry.java`: Interface defining the necessary service registry methods.
    - `impl/`: Holds the implementations of the interface.
        - `InMemoryServiceRegistry.java`: In-memory implementation of the `ServiceRegistry`.

- **model/**: Contains domain models used across the application.
    - `ServiceInfo.java`: Represents a service's information including address and last activity.

- **scheduler/**: Contains scheduled tasks for service management.
    - `ServiceCleanupScheduler.java`: Manages the periodic cleanup of inactive services.

- **controller/**: Handles incoming HTTP requests.
    - `ServiceController.java`: Processes requests related to service registration, discovery, and heartbeats.

- **ServiceDiscoveryApplication.java**: Main entry point for the Spring Boot application.



## Usage
- **Register a service**:
  POST to `/register` with parameters `serviceName` and `address`.
- **Discover services**:
  GET `/discover` with parameter `serviceName`.
- **Send a heartbeat**:
  POST to `/heartbeat` with parameter `address`.
