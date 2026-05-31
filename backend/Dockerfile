# ── Aşama 1: Build ───────────────────────────────────────────────
FROM amazoncorretto:21-alpine AS build
WORKDIR /app

# Önce sadece pom.xml kopyala — bağımlılıkları cache'le
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Sonra kaynak kodu kopyala ve build et
COPY src ./src
RUN ./mvnw package -DskipTests -B

# ── Aşama 2: Runtime ─────────────────────────────────────────────
FROM amazoncorretto:21-alpine
WORKDIR /app

# Güvenlik: root olmayan kullanıcı
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]