FROM amazoncorretto:21

WORKDIR /app

COPY build/libs/mipmip-v2-*-SNAPSHOT.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["/bin/java", "-jar", "./app.jar"]