# unitedapp

The unitedapp started as a Basic Spring Boot Rest Application as a means for me to learn and practice. Where possible, and where time has permitted, I have tried to adhere to good programming practices and maintained code coverage of around 80%. 

Over time I have added various additional microservices and components so that the unitedapp is no longer an app but a collection of apps and ideas. You can try out the original unitedapp on its swagger page below:

https://unitedappapi.herokuapp.com/swagger-ui.html#

The other apps in the service are listed below:

## -----------------------------------------------------------------------

## footiestats - https://github.com/agdevtools/footiestats
Footiestats is a Basic Spring Boot Rest Application, written in kotlin. It's main purpose, aside from teaching me kotlin, was to act as a wrapper-api for an external 3rd part api product - https://api.football-data.org - footiestats provides up-to-date league tables and fixtures form the premiere league for use in the react front end - UnitedAppReact.

You can try out the footiestats app at : https://footiestats.herokuapp.com (Swagger page to be added)


## -----------------------------------------------------------------------

## UnitedAppReact - https://github.com/agdevtools/UnitedAppReact

UnitedAppReact is one of my first forays into react web applications. Again it was a learning tool but it incorporates some useful functionality such as, Auth0 authentication and nginx. Most, if not all, of the validation is carried out on the backed (unitedapp). UnitedAppReact also uses the footiestats app to get some of its content.

## Architecture

![Screenshot](unitedappArc.png)

to run locally in docker download latest potgres image and run :
docker run --name postgres -e POSTGRES_HOST_AUTH_METHOD=trust  -d postgres
