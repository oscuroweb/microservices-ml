# microservices-ml
Microservices with spring-boot and Machine Learning with Apache Spark ML. The aim of this solution is to use as sample of a pure Java reference architecture based on Spring Boot plus Apache Spark to solve machine learning problems.

## Quick start

This section can help you to start the solution without a deep knowledge of the different technologies used. Please follow the instructions and PR if more information is needed.

### Maven

A very simple process if you know Maven and Java, this is the recommended way if you want to explore or improve the project after the quick start.

#### Prerrequisites

##### Java

You need a JDK installed to compile all the projects, you can download the latest version from [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), [OpenJDK](http://openjdk.java.net/install/) or [Azul Zulu (not tested)](https://www.azul.com/downloads/zulu/)

You can check that Java is available with the command below:

_java -version_

##### Maven

You also need Apache Maven installed. You can find installation instructions [here](https://maven.apache.org/download.cgi)

You can check your maven installation with the command below:

_mvn -version_

##### Spark

You can download Apache Spark [here](https://spark.apache.org/downloads.html). Please select the package type prebuild for Hadoop. Please note that you will need the installation path in the compilation and running process.

#### Process

##### Compilation

1. Clone project in a local directory and enter into microservices-ml folder.
2. Go to income-predictor-dto project.
3. Into this project run _mvn clean install_ execution should finish with a _BUILD SUCCESS_ message
4. Go to income-predictor-ml project.
5. Edit the application.properties file and change _spark_home, models_path_ and _dataset_file_ to the ones in your local machine.
6. Into this project run _mvn clean install_ execution should finish with a _BUILD SUCCESS_ message.
7. Go to income-predictor-service project.
8. Edit the application.properties file and change _spark_home, models_path_ and _dataset_file_ to the ones in your local machine.
9. Into this project run _mvn clean install_ execution should finish with a _BUILD SUCCESS_ message.
10. Go to income-predictor-vaadin project.
11. Into this project run _mvn clean install_ execution should finish with a _BUILD SUCCESS_ message.
12. Now all artifacts are generated and ready to be executed.

##### Running

We need to run all three applications to have the solution working, to do so we only need to follow the instructions below:

1. Go to _income-predictor-ml/target_
1. Check that the file _income-predictor-ml-0.0.1-SNAPSHOT.jar_ exists. If not you should follow the steps described in __Compilation__ section.
1. Run the project using the command _java -jar income-predictor-ml-0.0.1-SNAPSHOT.jar_, application should now connect to spark hadoop and read input file and create a model using this data.
1. Go to _income-predictor-service/target_
1. Check that the file _income-predictor-service-0.0.1-SNAPSHOT.jar_ exists. If not you should follow the steps described in __Compilation__ section.
1. Run the project using the command _java -jar income-predictor-service-0.0.1-SNAPSHOT.jar_, now service application should be availabe at port 8082.
1. Go to _income-predictor-vaadin/target_
1. Check that the file _income-predictor-vaadin-0.0.1-SNAPSHOT.jar_ exists. If not you should follow the steps described in __Compilation__ section.
1. Run the project using the command _java -jar income-predictor-vaadin-0.0.1-SNAPSHOT.jar_, now web application should be available at port 8080.
1. You can open your browser and go to [http://localhost:8080](http://localhost:8080) to check the solution.

![alt Screen](https://github.com/oscuroweb/microservices-ml/blob/master/images/Capture.png)

### Docker

TBD

#### Prerrequisites

##### Docker

#### Process

## Topology

![alt Topology](https://github.com/oscuroweb/microservices-ml/blob/master/images/Topology.png)

This solution has three different projects:

- income-predictor-dto
- income-predictor-service
- income-predictor-ml
- income-predictor-vaadin

You can see below a description for each project.

### income-predictor-dto

![alt income-predictor-dto project class diagram](https://github.com/oscuroweb/microservices-ml/blob/master/images/DTO-Diagram.png)

This project includes all Data Transfer Objects needed in the solution, it also includes all the types needed to process the input file.

### income-predictor-ml

![alt income-predictor-ml project class diagram](https://github.com/oscuroweb/microservices-ml/blob/master/images/ML-Diagram.png)

The Machine-Learning project itself, implements the algo.

### income-predictor-service

![alt income-predictor-service project class diagram](https://github.com/oscuroweb/microservices-ml/blob/master/images/Service-Diagram.png)

Service that exposes the different endpoints to be consumed.

### income-predictor-vaadin

![alt income-predictor-service project class diagram](https://github.com/oscuroweb/microservices-ml/blob/master/images/Vaadin-Diagram.png)

Web application that shows the results.

## Process description

You can find the full description in [slideshare](https://www.slideshare.net/oscuroweb/integrando-machine-learning-y-microservicios) (in spanish, english translation on going).

![alt Process description](https://github.com/oscuroweb/microservices-ml/blob/master/images/Process.png)

## Contacts

### Rafa Hidalgo
![alt Twitter](https://github.com/oscuroweb/microservices-ml/blob/master/images/Twitter_Icon.png) https://twitter.com/oscuroweb <br />
![alt Github](https://github.com/oscuroweb/microservices-ml/blob/master/images/GitHub-Mark.png)https://github.com/oscuroweb  <br />

### Julio Palma
![alt Twitter](https://github.com/oscuroweb/microservices-ml/blob/master/images/Twitter_Icon.png) https://twitter.com/restalion <br />
![alt Github](https://github.com/oscuroweb/microservices-ml/blob/master/images/GitHub-Mark.png)https://github.com/restalion <br />

