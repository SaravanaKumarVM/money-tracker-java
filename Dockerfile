# Use official Java 17 image
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# copy project files
COPY . .

# build project
RUN ./mvnw clean package -DskipTests

# expose dynamic port for Render
EXPOSE 8080

# run jar
CMD ["sh", "-c", "java -jar target/*.jar --server.port=${PORT}"]
