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

No one likes writing documentation. Right? This could be because there is a lack of incentive, structure or engineers may be unsure of how to write good documentation. This guide will attempt to address both of these issues by explaining why we need runbooks and provide some templates and helpful tips on how to go about creating them.

# So what makes a good runbook?

There are five attributes of any good runbook; the five A's. They must be:

- Actionable. 

- Accessible. 

- Accurate. 

- Authoritative & non Assuming. 

- Adaptable. 

Lets take each of the abover points.

## Actionable

The runbook should attempt to address specific things. For example, to respond to an Iris Alert, to Respond to a specific Grafana Alert. In the case of both there should be a runbook step with a title that maps directly to the alert it is trying to resolve.

## Accessible

If you can't find the runbook or it is not accessible then it doesnt matter how well it is written. It is **highly recommended** to provide a link the thr runbook in the Grafana/PagerDuty Alert. That way when a person is paged they have the runbook open right away and can begin to resolve the incident.

## Accurate

Needless to say if it doesnt contain truthful information then it is worse then no runbook at all.

## Authoritative and Non Assuming

In Highlander. There can be only one. Similarly in runbooks, **there should be only one** runbook for each process (alert) and it should not assume prior knowledge. Include helpful links to other documentaion on how to perform tasks. This allows the experienceed engineers to quickly get to the main points whilst allowing those less familiar with the services to receive the context they need.

## Adaptable

The unfortunate truth about runbooks is that the minute you create them you create technical debt. Some ways to avoid this is to:

- make sure there is a clear owner of a runbook.  
- treat a broken runbook like broken code. Fix it!
- Encourage people to use runbooks
- and most of all...celebrate them when they work!


# Runbook Template

What sort of information should a runbook contain?

## General
A quick description of the services.  1 to 2 sentences max.  Why does this service matter?  What is it's core functionality?  What Features does it provide users?

## Dashboards and monitoring
Links to the Dashboards for this service

## Alerts
Links to the Alerts for this service

## Github
Links to github for the repo.

## Jenkins
Links to jenkins pipeline for the repo.

For Every Alert there should be a corresponding section in alphabetical order
### Alert Title
Alert Description:  Why do we have this alert?  What does it mean?  What is typically the cause of this alert?

#### Remediation Steps:
Checklist manifesto style steps for how to resolve this alert.  A person who has never worked on our stack should be able to follow these steps and remediate the incident.  If it cannot be remediated, include escalation steps here.
 1. Do this
 2. Check this graph
 3. Do this thing 
 4. Do this other thing
 5. Verify service has recovered
 
## Contact Info
Team contact info.  Potentially contact info for who to escalate to.  What services do we have dependencies on?  How do we escalate to them?  Define this information here.  

## Latest Deployments
Consider using PagerDuty's new Recent Changes functionality whicg provides contect around a service. i.e. Has there been a deployment rceently that could have caused this outage/disruption? More on this soon.


