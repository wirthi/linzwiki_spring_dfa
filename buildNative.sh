#!/bin/sh

export PATH="$TOOLCHAIN_DIR/bin:$PATH" #for musl
export JAVA_HOME=/graalvm/graalvm-ee-java17-22.3.0/
./mvnw -Pnative native:compile

#### IMPORTANT NOTE: you need to set the option in application.yml, server.ssl.enable to TRUE