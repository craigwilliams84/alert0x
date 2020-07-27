FROM openjdk:8-jre
ADD target/alert0x.jar alert0x.jar
ADD ops/docker/scripts/start-alert0x.sh start-alert0x.sh
ENV MAILGUN_API_KEY "<SET ME>"
EXPOSE 8060
CMD chmod +x start-alert0x.sh && ./start-alert0x.sh
