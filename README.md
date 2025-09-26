Backend for FullStacked – A Spring Boot fitness tracker app with JWT authentication, email verification, and workout logging.
Tech Stack: Backend: Spring Boot (Java 17)
Database: PostgreSQL / MySQL / H2 (for local testing)
Security: JWT Authentication, Spring Security
Build Tool: Maven
Other: JPA/Hibernate, Jakarta Mail
Features: User Authentication (JWT-based login & registration)
Email Verification for secure account activation
Workout Management
Create workouts (with name + date)
Log sets, reps, weight
Query past workouts for progress tracking
Setup:
git clone https://github.com/<Cadenviv07>/fullstacked-fitness-backend.git
cd fullstacked-fitness-backend

spring.datasource.url=jdbc:mysql://localhost:3306/fitness_app
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

mvn spring-boot:run

http://localhost:8080

### Example Endpoints
- **POST** `/api/auth/register` → Register a new user
- **POST** `/api/auth/login` → Login and receive JWT
- **POST** `/api/workouts` → Create a new workout (requires JWT)

