FROM adoptopenjdk/openjdk12:latest
MAINTAINER Jaeyeon Kim <jaeyeonling@gmail.com>

ADD /build/libs/boiled-egg.jar boiled-egg.jar
EXPOSE 8080:80
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "boiled-egg.jar"]