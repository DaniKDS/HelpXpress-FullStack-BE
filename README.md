# HelpXpress-FullStack
# HelpXpress Backend Documentation

## Overview

HelpXpress is designed to streamline access to various support and assistance services. The backend is built using modern technologies, ensuring scalability, security, and maintainability.

## System Architecture

The backend of HelpXpress is structured into several main packages, each handling different aspects of the business logic:

- **`com.supportportal`**: This is the root package that includes all the necessary modules and dependencies.
  - **`configuration`**: Contains main application configurations, including security settings and Spring bean configurations.
  - **`controller`**: Hosts controllers that expose APIs at the network level and handle client requests.
  - **`domain`**: Defines the data models and entities used throughout the application.
    - **`Http`**: Contains helper classes for handling HTTP responses.
    - **`principal`**: Includes classes necessary for managing user identity and authentication.
  - **`repository`**: Includes interfaces for database access using Spring Data JPA.
  - **`service`**: Contains services that implement business logic, calling repositories for CRUD operations.
  - **`utility`**: Provides utilities and helpers for various recurring functions within the application.

## APIs

### Authentication and Authorization
- **POST `/api/auth/login`**: Authenticates a user and returns a JWT token.
- **POST `/api/auth/register`**: Registers a new user in the system.
-...
### User Management
- **GET `/api/users/{id}`**: Returns details of a user.
- **PUT `/api/users/{id}`**: Updates user details.
- **DELETE `/api/users/{id}`**: Deletes a user from the system.
- ...

### Additional Services
- **GET `/api/services`**: Lists available services.
- **POST `/api/services`**: Adds a new service to the offering.
- ...

## Security

The backend utilizes Spring Security to manage user authentication and authorization. All APIs are protected and accessible only to authenticated users, except the authentication and registration endpoints.

## Conclusion

This documentation provides an overview of the structure and functionalities of the HelpXpress backend. For additional details, refer to the source code and the comments included within it.

