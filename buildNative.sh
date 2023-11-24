#!/bin/sh

export JAVA_HOME=/graalvm/graalvm-ee-java17-22.3.0/
./mvnw -Pnative native:compile

