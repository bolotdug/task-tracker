mvn clean install -DskipTests
docker build . --tag task-tracker:latest
docker-compose -f docker-compose.yml up -d
