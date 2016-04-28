[![Build Status](https://travis-ci.org/SynthesisProject/server.svg?branch=master)](https://travis-ci.org/SynthesisProject/server) [![License](https://img.shields.io/badge/License-AGPLv3-blue.svg)](https://en.wikipedia.org/wiki/Affero_General_Public_License)
[![Coverity Scan Build Status](https://scan.coverity.com/projects/8683/badge.svg)](https://scan.coverity.com/projects/synthesisproject-server)

# Synthesis Server Synchronization Engine

###[Synthesis](http://synthesisproject.github.io/server)
Student access to continued broadband connectivity, influenced by a number of external factors, plays a major role in the effective delivery of learning content. In the light of this, OPENCOLLAB has developed a multiple device platform (laptop, tablet, mobile phone, USB , etc.) that can present a variety of content and functionality to students in an on/offline learning content management solution, thus allowing them continued access to resources wherever they are, affording a much richer and more seamless teaching and learning experience. Synthesis currently includes integrates with Sakai and Moodle. The Synthesis server project was developed to accommodate easy implementation of any other content source.

The Synthesis mobile [mobile](https://github.com/SynthesisProject/mobile) is the only openly available client tool at the moment. The LMS tools that it supports are Calendar, Resources(files) and Announcements.

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


