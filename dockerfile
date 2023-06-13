FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY target/SportShop-0.0.1-SNAPSHOT.jar .

EXPOSE 8050

CMD ["java", "-jar", "SportShop-0.0.1-SNAPSHOT.jar"]
