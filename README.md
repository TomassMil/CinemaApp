# Cinema Booking System

A Spring Boot application for managing cinema bookings, movie schedules, and user accounts. Built with Spring Security for authentication and MySQL for data persistence.

## Features

- User authentication and authorization
- User profile management
- Role-based access control (User, Employee, System Admin)
- Movie listings and schedules
- Ticket booking system
- Responsive UI with Tailwind CSS

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven
- IDE (Eclipse, IntelliJ IDEA, VS Code)

## Database Setup

1. Install MySQL if you haven't already
2. Create a new database named `CinemaApp`
3. Update the database configuration in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/CinemaApp?createDatabaseIfNotExist=true
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

## Project Setup

1. Clone the repository:
```bash
git clone https://github.com/yourusername/CinemaApp.git
cd CinemaApp
```

2. Configure your IDE:
   - Import as a Maven project
   - Ensure Java 17 is set as the project SDK
   - Enable annotation processing
   - If using VS Code, make sure to install these extension packs: Java Extension Pack, Spring Boot Extension Pack

3. Update application.properties:
   - Set your MySQL credentials
   - Adjust server port if needed (default: 8082)
   - Other properties are pre-configured for development

4. Build the project:
```bash
mvn clean install
```

5. Run the application:
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8082`

## Project Structure

```
src/main/java/lv/venta/cbs/
├── config/          # Configuration classes
├── controller/      # MVC controllers
├── model/          # Entity classes
│   └── enums/      # Enumerations
├── repository/     # Data access layer
└── service/        # Business logic layer

src/main/resources/
├── static/         # Static resources
├── templates/      # Thymeleaf templates
└── application.properties
```

## User Roles

The system supports three user roles:
- `ROLE_USER`: Regular users who can book tickets
- `ROLE_EMPLOYEE`: Staff members with additional privileges
- `ROLE_SYSADMIN`: System administrators with full access

## Development Guidelines

1. **Database Changes**
   - Use `spring.jpa.hibernate.ddl-auto=update` for development
   - For production, use proper database migration tools
   - Always backup the database before making schema changes

2. **Security**
   - Never commit sensitive data (passwords, API keys)
   - Use environment variables for production credentials
   - Keep Spring Security and other dependencies updated

3. **Code Style**
   - Follow Java naming conventions
   - Use meaningful variable and method names
   - Add comments for complex logic
   - Keep methods small and focused

## Common Issues

1. **Database Connection**
   - Ensure MySQL is running
   - Verify credentials in application.properties
   - Check if the port is correct (default: 3306)

2. **Build Issues**
   - Clean and rebuild the project
   - Update Maven dependencies
   - Check Java version compatibility

3. **Runtime Errors**
   - Check application logs
   - Verify database schema
   - Ensure all required services are running

## Contributing

1. Fork the repository (If not a developer of this project)
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request