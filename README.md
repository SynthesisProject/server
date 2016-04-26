# server

[![Build Status](https://travis-ci.org/SynthesisProject/server.svg?branch=master)](https://travis-ci.org/SynthesisProject/server)

# Synthesis Server Synchronization Engine

###[Synthesis](http://synthesisproject.github.io/server)

## Requirements
Java JDK 7 or 8
Maven 3.1+
Tomcat 7+
MySQL 5.5+

## Build
mvn clean install

## Deploy
Deploy synthesis-war

## DB setup
- Install MySQL 5.5+
- Run synthesis-service/src/main/sql/create-mysql.sql
- Run synthesis-service/src/main/sql/demo-data-mysql.sql

## Configure 
- Add 'set "SYNTHESIS_HOME=/opt/yourSynthesisHome"' to your sentenv file in tomcat/bin
- Copy the synthesis-war/src/main/webapp/WEB-INF/config/application.properties to your home folder
- Update application.properties

## JavaDoc
[api](http://synthesisproject.github.io/server/javadoc/service/apidocs/)
[service](http://synthesisproject.github.io/server/javadoc/service/apidocs/)
[shared](http://synthesisproject.github.io/server/javadoc/service/apidocs/)
[sakai-client](http://synthesisproject.github.io/server/javadoc/service/apidocs/)
[sakai-impl](http://synthesisproject.github.io/server/javadoc/service/apidocs/)
[moodle-client](https://help.github.com/pages)
[moodle-impl](https://help.github.com/pages)


