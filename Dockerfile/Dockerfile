FROM openjdk:8-alpine
COPY . /usr/src/myapp
WORKDIR /usr/scr/myapp
RUN mvn build
WORKDIR /usr/src/myapp/src/servidor
CMD ["java", "br.edu.ulbra.flightdelay.Main"]