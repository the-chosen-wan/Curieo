FROM --platform=linux/amd64 openjdk:19

WORKDIR /app

COPY . /app

# RUN javac Curieo/Test.java
RUN chmod +x /app/run.sh

# CMD ["java", "Curieo.Test"]
ENTRYPOINT ["/app/run.sh"]