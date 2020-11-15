# unitedapp
Spring Boot Basic Rest Application connecting to heroku postgres instace (previously remote AWS Cassandra instance).

to run locally in docker download latest potgres image and run :
docker run --name postgres -e POSTGRES_HOST_AUTH_METHOD=trust  -d postgres
