FROM amazoncorretto:19 as base
EXPOSE 8080
WORKDIR /usr/src/app
COPY --chown=app:app ./target/Todo-*.jar ./Todo.jar
ENTRYPOINT ["java","-jar","./Todo.jar"]


