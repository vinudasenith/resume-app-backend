# Resume Matcher Pro Backend 
____

## Overview ℹ️
____

This repository contains the backend for Resume Matcher Pro, a web application designed to check CVs, give a relevance score with a detailed report based on predefined criteria, and allow admins to provide manual feedback. The backend is built using Java and Spring Boot, offering RESTful APIs to support the Angular frontend. It integrates with a database to store user profiles, resumes, scoring reports, and feedback, enabling automated and human-assisted resume evaluation.

## Tech Stack 🛠️
____

- **Language**: Java 17 
- **Framework**: Spring Boot 3.x 
- **Database**: PostgreSQL 
- **Build Tool**: Maven 
- **Dependencies**:
  - Spring Web (REST APIs) 
  - Spring Data JPA (Database operations) 
  - Spring Security (Authentication & Authorization) 
  - Lombok (Boilerplate reduction) 

## Features 
____

- **User Management**: Secure user registration, login, and profile management with JWT-based authentication. 
- **Resume Submission**: Upload and parse resumes (PDF, DOCX) to extract skills, education, experience, and other criteria. 
- **ATS Compatibility Scoring**: Evaluates resumes against job descriptions to generate an ATS compatibility score with a detailed report. 
- **Report Download**: Users can download ATS score reports for job applications or reference. 
- **Admin Feedback**: Admin users can provide personalized feedback on resumes to help users improve their CVs. 
- **REST APIs**: Provides endpoints for seamless interaction with the Angular frontend. 
- **Security**: JWT-based authentication with role-based access control (e.g., USER, ADMIN roles). 

## Prerequisites ✅
____

Before setting up the project, ensure you have the following installed:

- Java 17 or higher 
- Maven 3.8.x or higher 
- PostgreSQL 15 or higher (or another compatible database) 
- Git 

## Setup Instructions 🚀
____

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/vinudasenith/resume-app-backend.git
   cd resume-app-backend
   ```

2. **Configure the Database** 🗄️:
   - Install PostgreSQL and create a database named `resume_matcher`.
   - Update the database configuration in `src/main/resources/application.properties`:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/resume_matcher
     spring.datasource.username=your-username
     spring.datasource.password=your-password
     spring.jpa.hibernate.ddl-auto=update
     ```

3. **Install Dependencies**:
   Run the following command to download Maven dependencies:
   ```bash
   mvn clean install
   ```

4. **Run the Application** ▶️:
   Start the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```
   The application will be available at `http://localhost:8080`.

## Project Structure 📂
____

```plaintext
.
├── src/                            # Source code directory
│   ├── main/                       # Main application source code
│   │   ├── java/com/example/resumematcher/
│   │   │   ├── config/             # Configuration classes
│   │   │   ├── controller/         # REST API controllers 
│   │   │   ├── dto/                # Data Transfer Objects for API responses 
│   │   │   ├── model/              # Entity classes 
│   │   │   ├── repository/         # JPA repositories for database access 
│   │   │   ├── security/           # Security-related classes 
│   │   │   ├── service/            # Business logic 
│   │   │   └── util/               # Utility classes 
│   │   └── resources/
│   │       ├── static/             # Static resources 
│   │       ├── templates/          # Template files 
│   │       └── application.properties  # Configuration file 
│   └── test/                       # Unit and integration tests 
├── uploads/                        # Directory for uploaded files 
├── targets/                        # Directory for compiled output and build artifacts 
├── .env                            # Environment variables configuration file 
├── .gitattributes                  # Git attributes configuration 
├── .gitignore                      # Git ignore file for excluding files from version control 
├── Help.md                         # Additional help or documentation file 
├── mvnw                            # Maven wrapper script for Unix-based systems 
├── mvnw.cmd                        # Maven wrapper script for Windows 
└── pom.xml                         # Maven project configuration file 
```

## API Endpoints 🔗
____

Key endpoints:

- `POST /api/users/register` - Register a new user 
- `POST /api/users/login` - Authenticate and get JWT token 
- `GET /api/users/me` - Pass own user data 
- `POST /api/resumes/upload` - Upload and parse a resume 
- `POST /api/feedback/send` - Send feedback for a resume 

## Running Tests 
____

Run unit and integration tests with:
```bash
mvn test
```

## License 📜
____

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Contact 📧
____

For questions or feedback, reach out to [ha.vinudas@gmail.com](mailto:ha.vinudas@gmail.com)
