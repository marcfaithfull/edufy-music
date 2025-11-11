FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/edufy-music-0.0.1-SNAPSHOT.jar /app/myapp.jar
EXPOSE 8888
CMD ["java", "-jar", "myapp.jar"]