FROM gradle:8.5-jdk21

WORKDIR /app

COPY . /app

COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
RUN gradle build

COPY src src
COPY config config

RUN gradle installDist

CMD ./build/install/app/bin/app
