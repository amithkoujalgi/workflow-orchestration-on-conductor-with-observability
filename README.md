## Demo of Workflow Orchestration with Netflix Conductor with Observability Setup

![Java](https://img.shields.io/badge/Java-8+-green.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.7-green.svg)
![Conductor](https://img.shields.io/badge/Netflix_Conductor-3.5-red.svg)

## Table of Contents

- [Introduction](#introduction)
- [Design](#design)
- [Prerequisites](#prerequisites)
- [Build](#build)
- [Run](#run)
- [References](#references)

### Introduction

This demonstration features the seamless workflow orchestration with Netflix's Conductor with workers as Spring Boot
apps and observability over the orchestration.

### Design

```mermaid
  flowchart TD
    w[Worker]
    c[Conductor]
    w -->|Registers with| c;
    u -->|Runs workflow| c;
    c -->|Runs task| w;
    p[Prometheus]
    g[Grafana]
    u[User]
    subgraph Conductor Deployment
        direction TB
        c -->|Publishes metrics| p
        p -->|Consumes metrics| g
    end
    g -->|View Dashboard| u;

```

#### Worker

- This is a microservice that has the code to execute one or more task types.

#### Conductor

- This is a service that is responsible for orchestrating the workflows.
- Registers workers that are capable of running tasks.

#### Prometheus

- Metrics collector

#### Grafana

- Metrics visualization tool

### Prerequisites

- [Docker](https://www.docker.com/products/docker-desktop/)
- [Docker Compose](https://docs.docker.com/compose/)
- [make](https://formulae.brew.sh/formula/make)

### Build

```shell
make build
```

### Start Conductor

```shell
make start-docker
```

### Add workflow

```shell
make add-workflow
```

### Start Worker

```shell
make start-worker
```

### Trigger a workflow run

```shell
make exec-workflow
```

### Trigger a faulty workflow run

```shell
make exec-workflow-faulty
```

With all services up, access:

| Description                           | Link                                        |
|---------------------------------------|---------------------------------------------|
| Conductor UI                          | http://localhost:5000                       | 
| Grafana UI                            | http://localhost:3000                       |                 
| Conductor REST API Playground         | http://localhost:8080/swagger-ui/index.html |                 
| Conductor Prometheus Metrics Endpoint | http://localhost:8080/actuator/prometheus   |                 
