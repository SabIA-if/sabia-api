from eclipse-temurin:21

workdir /app

copy target/sabIA-0.0.1-SNAPSHOT.jar app.jar

expose 8080

cmd ["java", "-jar", "app.jar"]