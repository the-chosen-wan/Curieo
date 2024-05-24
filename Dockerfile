FROM openjdk:19

WORKDIR /app

COPY . /app

RUN javac Curieo/Test.java

CMD ["java", "Curieo.Test"]