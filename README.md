# Task tracker Web API
Web API for entering project data into the database (task tracker)


## Functional requirements:
- Ability to create / view / edit / delete information about projects
- Ability to create / view / edit / delete task information
- Ability to add and remove tasks from a project (one project can contain several tasks)
- Ability to view all tasks in the project

## Installation

If you don't have maven installed, firstly [install maven](https://www.baeldung.com/install-maven-on-windows-linux-mac).
Then you'll need Docker, if you don't have it, [install it](https://docs.docker.com/engine/install/).

To run the program you need to run the script "deploy.sh" by the command:
>bash deploy.sh
> 
It will run docker file and docker-compose file.


## Usage

Swagger automated documentation is on the next address:

http://localhost:8080/api/swagger-ui.html

To create, view, edit or delete just use ordinary CRUD operations.

You can use PUT method with http://localhost:8080/api/projects/{id} to assign tasks to the given project.
It's enough to add just ids of tasks to add to the given project. During updating of the project, firstly all tasks will be removed, then given tasks will be added.

Project's start date sets when status of project becomes "Active", and completion date sets when status becomes "Completed".

## Technologies
- Java 11
- Spring Boot
- Spring DATA Jpa
- Hibernate
- Liquibase
- Postgres
- Docker
- Swagger
- JUnit
- Mockito