FROM java:8-alpine


ENV JAVA_OPTS="-Xmx32m -Xss256k"

# Entry with exec
ENTRYPOINT exec java $JAVA_OPTS -jar /opt/app.jar


# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /opt/app.jar