# Getting Started With Jakarta EE + Postgres + SonarQube + Prometheus

Steps to run this project:

1. You need docker and docker-compose installed on your machine.
2. Run ```mvn clean package``` and wait download and install of all dependencies
3. In other terminal tab run ```docker-compose up``` and wait until docker up services (  Postgres, Sonar and Prometheus  )
4. Run ```mvn clean verify sonar:sonar```  to resgitry application code on sonar quality gate
4. Run ``` mvn liberty:run ``` and wait until OpenLiberty to up apllication
5. Visit http://localhost:9080/open-api/ui for API documentation
6. Visit http://localhost:9080/metrics for system metrics
7. visit http://localhost:9080/health to see system health
8. visit http://localhost:9090/ to see Prometheus UI
9. visit http://localhost:9000/ to see Sonar Quality Gate


