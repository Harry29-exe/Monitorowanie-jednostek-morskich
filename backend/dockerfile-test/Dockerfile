FROM openjdk:17

ENV secret=""
ENV clientId=""

ADD ./target/Monitorowanie-jednostek-morskich-1.jar ./app.jar

EXPOSE 8080
#CMD java -jar -DMJM_secret=bekon1234567890 Monitorowanie-jednostek-morskich-1.jar
ENTRYPOINT ["java","-DMJM_secret=${secret}","-DMJM_client_id=${clientId}","-jar","app.jar"]