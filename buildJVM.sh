#!/bin/sh

export JAVA_HOME=/graalvm/graalvm-ee-java17-22.3.0
./mvnw package

${JAVA_HOME}/bin/java -jar target/learning-spring-0.0.1-SNAPSHOT.jar

