# SHIV

## What is this?

SHIV (**Spherical Horses in Vacuum**) is software for managing spherical horses and their expeditions into vacuum—an operational concern underserved by most enterprise platforms.

The project exists as a practical playground for Domain-Driven Design and hexagonal architecture. Its intentionally compact domain makes architectural boundaries, business rules, and adapter responsibilities easy to examine without introducing another shopping cart, banking app, or task tracker.

The code is split into three independent modules:

- `shiv-core` — framework-neutral application and port abstractions.
- `shiv-horse` — the Horse bounded context: domain model and use cases.
- `shiv-horse-service` — Spring Boot REST and persistence adapters.

## What is already achieved?

- A common DDD model for domain logic and a common hexagonal model for application architecture, provided by `shiv-core`.
- A fully platform-independent business module, `shiv-horse`, built on `shiv-core`. It contains only the domain model and application use cases, with hints such as `@ApplicationComponent` that platform wrappers may use for discovery.
- A Spring Boot wrapper, `shiv-horse-service`, which provides the REST and persistence adapters. Business code and platform code are physically separated, mostly to prove that they really can survive without sharing a package and pretending that counts as architecture.

## What is to be achieved?

- Add the `shiv-vacuum-exp` bounded context and wrap it in `shiv-vacuum-exp-service` using a different backend framework, such as Quarkus, Micronaut, or Ktor.
- Demonstrate practical uses of replicas, proxies, and replicated proxies between bounded contexts.
- Formalize domain and integration event models in `shiv-core`.
- Introduce Kafka for event messaging.
- Use gRPC for synchronous cross-service calls.
- Model common application-level authorization and implement Zanzibar-style authorization with SpiceDB or OpenFGA.

## Quick setup

Prerequisite: JDK 23.

Each module is currently a separate Gradle build, so publish the libraries locally before starting the service:

```bash
cd shiv-core
./gradlew publishToMavenLocal

cd ../shiv-horse
./gradlew publishToMavenLocal

cd ../shiv-horse-service
mkdir -p data
./gradlew bootRun
```

The API starts at `http://localhost:8080/v1/horses`. SQLite data is stored in `shiv-horse-service/data/horse.db`.

Create a horse:

```bash
curl -X POST http://localhost:8080/v1/horses \
  -H 'Content-Type: application/json' \
  -d '{"name":"Bucephalus","radius":1.5,"mass":500.0,"minimumPressure":100.0}'
```
