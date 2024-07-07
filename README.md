# Easy-Booking

Easy Booking is a sophisticated hotel management and booking system that allows users to book hotels, check availability, and review hotels. This project is designed with a microservice architecture, comprising four main services: user service, hotel service, booking service, and rating service. An API gateway facilitates unified access to these services through a single port. Additionally, a configuration server, utilizing GitHub for centralized configuration management, reduces redundancy and enhances scalability.

To ensure high reliability and resilience, we have implemented fault tolerance mechanisms across all services. These include circuit breakers to prevent resource exhaustion during failures, retry policies to handle transient errors automatically, and rate limiters to protect services from excessive load. This comprehensive fault tolerance strategy ensures robust and resilient operations within the microservice architecture.
