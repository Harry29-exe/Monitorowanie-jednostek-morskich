FROM maven:3.8.4-openjdk-17 as build
COPY pom.xml /home/app/
RUN mvn -f /home/app/pom.xml verify clean --fail-never
COPY src /home/app/src
RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:17
COPY --from=build /home/app/target/Monitorowanie-jednostek-morskich-1.jar /usr/local/lib/mjm.jar

ENV secret=""
ENV clientId=""
EXPOSE 8080
ENTRYPOINT ["java","-DMJM_secret=${secret}","-DMJM_client_id=${clientId}","-jar","/usr/local/lib/mjm.jar"]