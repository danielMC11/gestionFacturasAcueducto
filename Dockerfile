# 1. Imagen base con Java 21 (ligera)
FROM eclipse-temurin:21-jre-alpine

# 2. Copiar el "Fat JAR" (el que no es .original) al contenedor
COPY target/gestionAcueducto-0.0.1-SNAPSHOT.jar app.jar

# 3. Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]