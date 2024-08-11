FROM gradle:8.5-jdk21

WORKDIR /app

COPY . /app

RUN gradle installDist

CMD ./build/install/app/bin/app
