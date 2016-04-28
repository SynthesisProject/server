[![Build Status](https://travis-ci.org/SynthesisProject/server.svg?branch=master)](https://travis-ci.org/SynthesisProject/server) [![License](https://img.shields.io/badge/License-AGPLv3-blue.svg)](https://en.wikipedia.org/wiki/Affero_General_Public_License)
[![Coverity Scan Build Status](https://scan.coverity.com/projects/8683/badge.svg)](https://scan.coverity.com/projects/synthesisproject-server)

# Synthesis Synchronization Engine
##### We have just moved this project from closed source to AGPLv3, please let us know if you any issues with missing or inaccurate documentation.

###[Synthesis](http://synthesisproject.github.io/server)

## Requirements
- Java JDK 7 or 8
- Maven 3.1+
- Tomcat 7+
- MySQL 5.5+

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
- [api](http://synthesisproject.github.io/server/javadoc/service/apidocs/)
- [service](http://synthesisproject.github.io/server/javadoc/service/apidocs/)
- [shared](http://synthesisproject.github.io/server/javadoc/service/apidocs/)
- [sakai-client](http://synthesisproject.github.io/server/javadoc/service/apidocs/)
- [sakai-impl](http://synthesisproject.github.io/server/javadoc/service/apidocs/)
- [moodle-client](https://help.github.com/pages)
- [moodle-impl](https://help.github.com/pages)


