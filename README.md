### Air Quality Server app

#### Project setup

* Java 24
* Springboot 3.4.5
* Gradle 8.14

#### Project configuration

Add rabbitMQ properties to `.env` file:

```
RABBITMQ_HOST=cow.rmq2.cloudamqp.com
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=......
RABBITMQ_PASSWORD=........
RABBITMQ_VIRTUAL_HOST=......

RABBITMQ_QUEUE=air-quality-queue (by default)
RABBITMQ_QUEUE_TTL=60000 (60 sec by defauult)
RABBITMQ_EXCHANGE=air-quality-exchange (by default)
RABBITMQ_ROUTING_KEY=air-quality-routing-key (by default)

# Interval in milliseconds (60 seconds)
APP_SERVICE_SCHEDULER_CRONE=0 * * * * *
```

Note: for CloudAMQP RabbitMQ provider `RABBITMQ_VIRTUAL_HOST` is `RABBITMQ_USERNAME`

#### API endpoints

1. REST
    - /v1/api/statistics

2. WebSocket
    - ws://HOST/sensor-data

#### Provider

* https://www.cloudamqp.com (Message Queues in the Cloud)

##### CloudAMQP RabbitMQ provider

link: https://api.cloudamqp.com

plan: 'Little Lemur'

* Open Connections: 0 of 20
* Max Idle Queue Time: 28 days
* Queues: 2 of 150
* Messages: 7 of 1 000 000
* Queue Length: 1 of 10 000

##### Switching to CloudAMQP

I had the same error when I switched to Cloud AMQP.

link: https://stackoverflow.com/questions/72248024/switching-to-cloudamqp-gives-com-rabbitmq-client-shutdownsignalexception

As you mentioned, the virtual host was missing from the properties:

```
spring.rabbitmq.virtual-host=user_name

spring.rabbitmq.host=.....
spring.rabbitmq.username=user_name
spring.rabbitmq.password=........
spring.rabbitmq.port=5672
```

#### Java code style

Java code style refers to the conventions and guidelines that developers follow when writing Java code to ensure
consistency and readability.

project: google-java-format,
link: https://github.com/google/google-java-format/blob/master/README.md#intellij-jre-config

#### Spring Actuator

* [Spring Boot Actuator: Health check, Auditing, Metrics gathering and Monitoring](https://www.callicoder.com/spring-boot-actuator/#:~:text=You%20can%20enable%20or%20disable,the%20identifier%20for%20the%20endpoint)

Endpoint ID Description:

* /actuator/info - displays information about your application.
* /actuator/health - displays your application’s health status.

Enable info and health endpoint in *.yaml file

```
management:
  endpoints:
    web:
      exposure:
        include: health,info
```

Actuator endpoints:

* /actuator
* /actuator/health
* /actuator/info

#### Gradle

##### Gradle Versions Plugin

Displays a report of the project dependencies that are up-to-date, exceed the latest version found, have upgrades, or
failed to be resolved, info: https://github.com/ben-manes/gradle-versions-plugin

command:

```
gradle dependencyUpdates
```

##### Gradle wrapper

Gradle Wrapper Reference:
https://docs.gradle.org/current/userguide/gradle_wrapper.html

How to Upgrade Gradle Wrapper:
https://dev.to/pfilaretov42/tiny-how-to-upgrade-gradle-wrapper-3obl

```
./gradlew wrapper --gradle-version latest
```

##### Gradle ignore test

To skip any task from the Gradle build, we can use the -x or –exclude-task option. In this case, we’ll use “-x test” to
skip tests from the build.

To see it in action, let’s run the build command with -x option:

```
gradle clean build -x test
```