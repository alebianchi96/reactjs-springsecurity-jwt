<a name="readme-top"></a>

[![Docker][Docker.bdg]][Docker-url]
[![Bash][Bash.bdg]][Bash-url]
[![Postgres][Postgres.bdg]][Postgres-url]
[![React][React.js]][React-url]
[![Springboot][Springboot.bdg]][springboot-url]

<br>

<div width="100px" height="100px" align="center">
<image width="100px" height="100px" src="https://brandslogos.com/wp-content/uploads/images/large/react-logo-1.png"/>
</div>

<div align="center">
  <h3 align="center">Spring Boot and React Application Secured by JWT Authentication</h3>
</div>

## 👓 Overview
This project demonstrates a web application featuring a React.js frontend and a Spring Boot backend secured with JWT authentication using Spring Security.

<br>

## 📦 Structure of the project
[![Docker][Docker.bdg]][Docker-url]
[![Bash][Bash.bdg]][Bash-url]

To simplify installation, the project is organized within a Docker Compose setup, which includes three main components:

- **frontend**: accessible on port 80
- **be-spring-security**: accessible on port 8080
- **db-employee (PostgreSQL)**: accessible on port 5435

A script named <code>deploy-all.sh</code> is provided to streamline the build and deployment process. This script handles all necessary steps, accommodating user preferences:
- Shuts down the current Docker Compose instance.
- Prompts the user to rebuild the backend:
   <br>If yes, performs a Maven build of the project and removes the old Docker image to replace it with the new one.
- Prompts the user to rebuild the frontend:
   <br>If yes, performs an npm build of the project and removes the old Docker image to replace it with the new one.
- Launches a new Docker Compose instance (if not already present, each image will be rebuilt).

To execute the script, run: 
```bash
$ sh deploy-all.sh
```

<br>

## ⌨️ Local development
Clone the repository:
```bash
$ git clone
$ cd fe-react
$ npm install
$ cd ..
```

During frontend development, it is recommended to keep the entire project running on Docker to leverage the routing to backend APIs provided by NGINX. The application running in development mode (<code>localhost:3000</code>) will point to NGINX on Docker via the "proxy" attribute set in the **package.json** file.

To start the frontend in development mode, run the command: 
```bash
$ cd fe-react
$ npm run start
```

<br>

## 🧩 Components
The project consists of the following main components:
<br>

### [![Postgres][Postgres.bdg]][Postgres-url]

name: **db-employee**

- **employees**: stores information about the employees.
- **users**: stores information about the users.
<br><br>

### [![Springboot][Springboot.bdg]][springboot-url]

name: **be-spring-security**

- **SecurityexampleApplication.java**: the entry point of the application. At startup, it registers a default user (username: 'ale', password: 'ale') and some sample employees, combining this procedure with database regeneration (<code>spring.jpa.hibernate.ddl-auto=create-drop</code>).

- **UserService.java**: implements **UserDetailsService.java**, defining the procedure for verifying user credentials ("**loadUserByUsername**").

- **UserPrincipalDTO.java**: implementation of **UserDetails.java**, used by Spring Security to transmit user data.

- **UserPasswordEncoder.java**: configures password encryption policies.

- **SecurityConfig.java**: configures the authentication policies required to access the exposed APIs.
  - Authentication type: JWT;<br>
  - Service for control logic: **UserDetailsService.java**;<br>
  - APIs not covered by authentication: <code>/user/login</code>, <code>/user/register</code>; <br>
  - Allowed IPs: <code>frontend.security.host</code>
  - Filter on HTTP requests: JwtFilter.java, UsernamePasswordAuthenticationFilter.java
 
- **JwtFilter.java**: ... TODO ...

- **JwtService.java**: ... TODO ...

<br>

### [![React][React.js]][React-url]

name: **frontend**

- **default.conf**: NGINX configuration with redirect policies. All calls from the frontend will be redirected as follows:
   - <code>localhost:3000/be-spring-security/*</code> ---> <code>be-spring-security:8080/</code>. <br>  

... TODO ...
  
<br><br>

<br>
<br>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Springboot.bdg]: https://img.shields.io/badge/Springboot-20232A?style=for-the-badge&logo=springboot&logoColor=8dc891
[springboot-url]: https://e7.pngegg.com/pngimages/931/804/png-clipart-spring-framework-software-framework-java-application-framework-web-framework-java-leaf-text-thumbnail.png
[Docker.bdg]: https://img.shields.io/badge/Docker-20232A?style=for-the-badge&logo=docker&logoColor=61DAFB
[Docker-url]: https://w7.pngwing.com/pngs/219/411/png-transparent-docker-logo-kubernetes-microservices-cloud-computing-dockers-logo-text-logo-cloud-computing.png
[Postgres.bdg]: https://img.shields.io/badge/Postgres-20232A?style=for-the-badge&logo=postgresql&logoColor=61DAFB
[Postgres-url]: https://www.postgresql.org/
[Bash.bdg]: https://img.shields.io/badge/Bash-20232A?style=for-the-badge&logo=gnubash&logoColor=D3D3D3
[Bash-url]: https://www.gnu.org/software/bash/
