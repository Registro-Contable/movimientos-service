FROM openjdk:14

ARG JAR_FILE=target/*.jar
ARG START_FILE=.sh/startup.sh

COPY ${JAR_FILE} app.jar
COPY ${START_FILE} startup.sh
RUN chmod 777 startup.sh

ENTRYPOINT ["./startup.sh","./app.jar"]