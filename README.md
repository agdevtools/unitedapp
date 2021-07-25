# unitedapp

The unitedapp started as a Basic Spring Boot Rest Application as a means for me to learn and practice. Where possible, and where time has permitted, I have tried to adhere to good programming practices and maintained code coverage of around 80%. I have also tried to provide consistent api responses and response bodies.

**Technologies used**

- Java
- Kotlin
- React
- Javascript

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

You can try out the UnitedAppReact here: https://unitedappfrontend.herokuapp.com

## -----------------------------------------------------------------------

## Architecture

![Screenshot](unitedappArc.png)

## How to run this app:

git clone https://github.com/agdevtools/unitedapp.git

cd unitedapp

docker: 

docker run --name postgres -e POSTGRES_HOST_AUTH_METHOD=trust  -d postgres


## -----------------------------------------------------------------------

# Why do we need them?

No one likes writing documentation. Right? Mostly this is because there is a lack of incentive of structure or engineers may be unsure of how to write good documentation. This guide will attempt to address both of these issues by explaining why we need them and provide some templates and tips on how to go about creating good runbooks.

# So what makes a good runbook?

There are five attributes of any good runbook; the five A's. They must be:

- actionable. It's nice to know the big picture and architecture of a system, but when you are looking for a runbook, you're looking to take action based on a particular situation.

- accessible. If you can't find the runbook, it doesn't matter how well it is written.

- accurate. If it doesn't contain truthful information, it's worse than nothing at all.

- authoritative. It is confusing to have more than one runbook for any given process.

- adaptable. Systems evolve over time, and if you can't change your runbook, the drift will make it unusable.






The unitedapp started as a Basic Spring Boot Rest Application as a means for me to learn and practice. Where possible, and where time has permitted, I have tried to adhere to good programming practices and maintained code coverage of around 80%. I have also tried to provide consistent api responses and response bodies.

**Technologies used**

- Java
- Kotlin
- React
- Javascript

Over time I have added various additional microservices and components so that the unitedapp is no longer an app but a collection of apps and ideas. You can try out the original unitedapp on its swagger page below:
