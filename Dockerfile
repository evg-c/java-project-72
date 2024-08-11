FROM gradle:8.5-jdk21

WORKDIR /app

COPY . /app

COPY ./app/gradle gradle
COPY ./app/build.gradle .
COPY ./app/settings.gradle .

COPY ./app/src src
COPY ./app/config config

RUN gradle build

RUN gradle installDist

CMD ./build/install/app/bin/app
