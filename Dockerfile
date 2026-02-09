FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

# ⭐ VERY IMPORTANT (fixes your error)
RUN chmod +x mvnw

# build jar
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java","-jar","target/moneytracker-0.0.1-SNAPSHOT.jar"]
