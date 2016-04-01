FROM java:8
ADD producer-0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8080
EXPOSE 1099
