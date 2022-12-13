# star-wars-api
This is the Star Wars API project, an API built to retrieve, store and manipulate information about the Star Wars universe.

# Getting Started
In order to run this project in your local environment, we need to execute some steps first. 

## Dependencies
First of all, this project has a few dependencies to be executed locally. Please make sure that you have **installed and configured** all of them before getting started:

* [JDK 17](https://www.azul.com/downloads/?package=jdk#download-openjdk): This project runs on Java 17
* [Gradle](https://gradle.org/install/): The default build tool for this project is Gradle
* [Docker](https://docs.docker.com/get-docker/): In this project we use Docker to provide a few dependencies used by this application, such as: MongoDB and Redis

### Setting up resources with Docker
This project uses MongoDB and Redis as the data storing technologies. To run it locally, we need to set up those resources in our local environment.

In order to facilitate the process of setting up these resources, we provide a docker-compose.yml file that sets up two containers, one for MongoDB and another one for Redis. This file is located under the docker-dev directory which is located under the root of this project.

Before starting up the containers, make sure you have a network named 'bubble' created by running the command below:
```
docker network create bubble
```

First, open up a terminal window. Then, navigate to the star-wars-api project directory. Once you are in the project directory, navigate to the docker-dev directory. Once you are there, we can run the command below to start both containers:
```
docker compose up
```

This command will start both Redis and MongoDB in your local environment with the configurations provided 

### Build project with Gradle
This project uses Gradle as the default build tool. We use Gradle to compile the code and package into a jar which will be used to run the application. 

*Note: Please note that if you are importing this project into an IDE with an embedded Gradle, such as IntelliJ IDEA, you may skip this part of the tutorial and use the build and clean commands through the IDE's UI.*

To build the project, open up a new terminal window and navigate to the project directory. Once you get there, run the command below:


```
gradle wrapper
```

This will generate a gradle wrapper script (gradlew) which we will use during the build process. For more information about the Gradle Wrapper, see the reference below:
https://docs.gradle.org/current/userguide/gradle_wrapper.html#gradle_wrapper

After generating the gradlew script, run the command below:
```
./gradlew clean
```

This will clean any old builds that were generated previously. After cleaning the old builds, we generate a new one with the command below:
```
./gradlew build
```
The command above will generate a new build and thus a new jar file that will be used to run the application. After running this command we are ready to run the application.

# Running the application
After compiling the code and generating a jar, we can start running the application. Using the terminal window opened in the previous step, run the following command:
```
./gradlew bootJar
```
This will run the Spring Boot application with the jar that was previously generated during the build phase.

*Note: Please note that if you are importing this project into an IDE such as IntelliJ IDEA, you may skip this part of the tutorial and run the application through the IDE's UI.*

As seen in the application.properties file, the server is configured to run in port 8081 by default, so you may access the application through the URL below:
```
http://localhost:8081/
```

# API Documentation 

## Load Planet by ID
This endpoint retrieves a Planet from the [SWAPI Public API](https://swapi.dev/documentation), saves this Planet in this application's database and returns the payload of the Planet that was saved. If the Planet was already saved once, the result is retrieved from the database instead of calling the external API.

### Method
```
POST
```

### URL
```
http://localhost:8081/planets/{planetId}
```
**Where**

* **planetId**: id of the Planet, initially retrieved from the [SWAPI Public API](https://swapi.dev/documentation)

### Success Response
* HTTP Status Code: 200 OK
```
{
    "id": "2",
    "name": "Alderaan",
    "climate": [
        "temperate"
    ],
    "terrain": [
        "grasslands",
        "mountains"
    ],
    "films": [
        {
            "id": "1",
            "title": "A New Hope",
            "director": "George Lucas",
            "releaseDate": "1977-05-25",
            "url": "https://swapi.dev/api/films/1/"
        },
        {
            "id": "6",
            "title": "Revenge of the Sith",
            "director": "George Lucas",
            "releaseDate": "2005-05-19",
            "url": "https://swapi.dev/api/films/6/"
        }
    ]
}
```

**Where**
* **id**: id of the Planet retrieved from the [SWAPI Public API](https://swapi.dev/documentation)
* **name**: name of the Planet
* **climate**: array with a list of possible types of climate for the given Planet
* **terrain**: array with a list of possible types of terrain for the given Planet
* **films**: array with a list of Films where this Planet appeared
  * **id**: id of the Film, initially retrieved from the [SWAPI Public API](https://swapi.dev/documentation)
  * **title**: title of the Film
  * **director**: director of the Film
  * **releaseDate**: release date of the Film
  * **url**: url to get information about the Film resource in the [SWAPI Public API](https://swapi.dev/documentation)

### Error Response
If the Planet is not found in the [SWAPI Public API](https://swapi.dev/documentation)
```
{
    "message": "The requested resource was not found in the SWAPI public API",
    "code": 404
}
```

**Where**
* **message**: error message
* **code**: HTTP Status Code for the given error


## Get Planet by Name

This endpoint finds a Planet from the database, querying by its name.
### Method
```
GET
```

### URL
```
http://localhost:8081/planets?planetName={planetName}
```
**Where**

* **planetName**: query parameter to pass the name of the Planet.

### Success Response
* HTTP Status Code: 200 OK
```
{
    "id": "2",
    "name": "Alderaan",
    "climate": [
        "temperate"
    ],
    "terrain": [
        "grasslands",
        "mountains"
    ],
    "films": [
        {
            "id": "1",
            "title": "A New Hope",
            "director": "George Lucas",
            "releaseDate": "1977-05-25",
            "url": "https://swapi.dev/api/films/1/"
        },
        {
            "id": "6",
            "title": "Revenge of the Sith",
            "director": "George Lucas",
            "releaseDate": "2005-05-19",
            "url": "https://swapi.dev/api/films/6/"
        }
    ]
}
```

**Where**
* **id**: id of the Planet retrieved from the [SWAPI Public API](https://swapi.dev/documentation)
* **name**: name of the Planet
* **climate**: array with a list of possible types of climate for the given Planet
* **terrain**: array with a list of possible types of terrain for the given Planet
* **films**: array with a list of films where this Planet appeared
  * **id**: id of the Film, initially retrieved from the [SWAPI Public API](https://swapi.dev/documentation)
  * **title**: title of the Film
  * **director**: director of the Film
  * **releaseDate**: release date of the Film
  * **url**: url to get information about the Film resource in the [SWAPI Public API](https://swapi.dev/documentation)


### Error Response

If the requested Planet was not found in the database
```
{
    "message": "The requested Planet was not found",
    "code": 404
}
```

**Where**
* **message**: error message
* **code**: HTTP Status Code for the given error

## Get Planet by ID
This endpoint finds a Planet from the database, querying by its ID.
### Method
```
GET
```
### URL
```
http://localhost:8081/planets/{planetId}
```
**Where**

* **planetId**: id of the Planet, initially retrieved from the [SWAPI Public API](https://swapi.dev/documentation)

### Success Response
* HTTP Status Code: 200 OK
```
{
    "id": "2",
    "name": "Alderaan",
    "climate": [
        "temperate"
    ],
    "terrain": [
        "grasslands",
        "mountains"
    ],
    "films": [
        {
            "id": "1",
            "title": "A New Hope",
            "director": "George Lucas",
            "releaseDate": "1977-05-25",
            "url": "https://swapi.dev/api/films/1/"
        },
        {
            "id": "6",
            "title": "Revenge of the Sith",
            "director": "George Lucas",
            "releaseDate": "2005-05-19",
            "url": "https://swapi.dev/api/films/6/"
        }
    ]
}
```

**Where**
* **id**: id of the Planet retrieved from the [SWAPI Public API](https://swapi.dev/documentation)
* **name**: name of the Planet
* **climate**: array with a list of possible types of climate for the given Planet
* **terrain**: array with a list of possible types of terrain for the given Planet
* **films**: array with a list of films where this Planet appeared
  * **id**: id of the Film, initially retrieved from the [SWAPI Public API](https://swapi.dev/documentation)
  * **title**: title of the Film
  * **director**: director of the Film
  * **releaseDate**: release date of the Film
  * **url**: url to get information about the Film resource in the [SWAPI Public API](https://swapi.dev/documentation)


### Error Response

If the requested Planet was not found in the database:
```
{
    "message": "The requested Planet was not found",
    "code": 404
}
```

**Where**
* **message**: error message
* **code**: HTTP Status Code for the given error


## List Planets
This endpoint returns a paginated list of all Planets in the database.
### Method
```
GET
```
### URL
```
http://localhost:8081/planets/list?page={page}&pageSize={pageSize}
```
**Where**
* **page**: Considering that this endpoint returns a paginated response, the number of the page to be retrieved
* **pageSize**: the number of records to return in each page


### Success Response
* HTTP Status Code: 200 OK
```
{
    "id": "2",
    "name": "Alderaan",
    "climate": [
        "temperate"
    ],
    "terrain": [
        "grasslands",
        "mountains"
    ],
    "films": [
        {
            "id": "1",
            "title": "A New Hope",
            "director": "George Lucas",
            "releaseDate": "1977-05-25",
            "url": "https://swapi.dev/api/films/1/"
        },
        {
            "id": "6",
            "title": "Revenge of the Sith",
            "director": "George Lucas",
            "releaseDate": "2005-05-19",
            "url": "https://swapi.dev/api/films/6/"
        }
    ]
}
```

**Where**
* **id**: id of the Planet retrieved from the [SWAPI Public API](https://swapi.dev/documentation)
* **name**: name of the Planet
* **climate**: array with a list of possible types of climate for the given Planet
* **terrain**: array with a list of possible types of terrain for the given Planet
* **films**: array with a list of films where this Planet appeared
  * **id**: id of the Film, initially retrieved from the [SWAPI Public API](https://swapi.dev/documentation)
  * **title**: title of the Film
  * **director**: director of the Film
  * **releaseDate**: release date of the Film
  * **url**: url to get information about the Film resource in the [SWAPI Public API](https://swapi.dev/documentation)



## Delete Planet by ID
This endpoint deletes a Planet from the database, querying by its ID.

### Method
```
DELETE
```
### URL
```
http://localhost:8081/planets/{planetId}
```
**Where**

planetId: id of the Planet, initially retrieved from the [SWAPI Public API](https://swapi.dev/documentation)

### Success Response
* HTTP Status Code: 200 OK

This endpoint does not return a response payload.

# Code Coverage

To assess the code coverage of this project we included the JaCoCo plugin. To obtain a code coverage report, we need to take a few steps:

First, open a terminal window and navigate to the root directory of the project. Once you are there, make sure you already have the gradle wrapper:
```
gradle wrapper
```

If you already generated the gradle wrapper, run the following command to obtain the code coverage report:
```
./gradlew jacocoTestReport
```

To see the results, navigate to the path below, within the root directory of the project:
```
/star-wars-api/build/jacoco
```

An index.html file will be generated. You can open it on a browser to see the code coverage report.

# Logs

Besides logging to the console, this application is also configured to log to a text file located under the following path:
```
/star-wars-api/logs
```
