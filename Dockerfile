FROM maven:3.8.1-openjdk-11-slim
WORKDIR /usr/app
COPY . /usr/app
RUN mvn clean install
EXPOSE 8080
ENTRYPOINT ["mvn", "spring-boot:run"]

