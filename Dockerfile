FROM openjdk:8
WORKDIR /
CMD ["echo", "Welcome to Simplilearn"]
COPY /TanKyRestaurant-1-0.0.1-SNAPSHOT.jar TanKy.jar
ENTRYPOINT ["java","-jar","/TanKy.jar"]

