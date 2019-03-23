# Studio Admin Toolkit

Studio Admin Toolkit is a lightweight Web application designed to be hosted and accessed on a local network, specifically on a Raspberry Pi with a touchscreen. The application provides basic administrative and management capabilities geared toward assisting independent small / home recording studios.

## Technology Overview

The technology used to develop the application is straightforward and nothing out of the ordinary. Below is a list of the application's core technologies:

1. Java 8
2. Spring Boot 2.1.3
    - MVC
    - Thymeleaf
3. ModelMapper
4. Unit Testing
    - JUnit
    - Mockito
5. Maven
6. PostgreSQL 11
7. jQuery 3.3.1-2 (WebJars)
8. Bootstrap 4.3.1 (WebJars)
9. TempusDominus-Bootstrap-4 5.1.2 (date / time picker) (WebJars)
10. MomentJS 2.22.2 (WebJars)

## Getting Started

The application has been developed in Visual Studio Code. Clone the repository, then open the root folder in Visual Studio Code to get started.

First, you need to create the database in PostgreSQL. If you are using pgAdmin, you can start it and open / execute the script located at `src/main/resources/schema.sql`. If you are using the command line, you can execute `psql -U postgres -a -f src/main/resources/schema.sql` from the root folder.

If you are using a different Postgres user (not `postgres`), or if you are using a different password (not `admin`) you will need to configure the appropriate username and password in `src/main/resources/application.properties`. The applicable properties are `spring.datasource.username` and `spring.datasource.password`. Also, you can change `spring.datasource.url` if you are not using the default host and / or port for the PostgreSQL service.

Once the database has been created, you can run the application locally. There is already a Debug task configured, so you can press `F5` in VS Code. The application will build and the start debugging. Once it is running, point your web browser to `http://localhost:/8080`.

**That's it - you're up and running!**

## Code Structure

Source files are organized by feature rather than by type. For example, all `Client` objects (controllers, services, viewmodels, etc) are located in `src/main/java/com/meutley/studioadmintoolkit/Client`. This may change in the future depending on how complex the code may become. However, the "by feature" approach made the most sense when development began.

HTML templates are located in `src/main/resources/templates`. These are also organized by feature, so all views related to clients are in the `client` subfolder, and so on. Templates in the `shared` subfolder are not specific to any feature, typically reused, such as the root layout template `shared/_layout.html`.
