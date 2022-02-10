
# Thumby: Thumbnail Generator!

Thumby is a thumbnail generator as a web service. Pass an image or a pdf with an optional size to the service and get a thumbnail image back.
It supports pdfs and image files.

# Requirements

 1. Java 11
 2. Maven Build Tool

# Getting Started

## Download

#### With Console
    # Select desired location
    cd /myPath
    
    # Download thumby
    git clone https://github.com/hbz/to.science.thumbs.git
    
    # Go to project directory
    cd to.science.thumbs

#### With Eclipse IDE

    Import -> Maven -> Existing Maven Project -> Select project root directory 

or

    Git Repositories -> Clone a Git Repository -> Enter https://github.com/hbz/to.science.thumbs.git

## Configurations

#### For usage in development mode
    
    To work with thumby in dev mode (localhost) you have to make sure that the embedded Tomcat Server in 
    Spring is enabled and that the internal application.properties file inside the classpath is used. 
    Follow these steps to realize this:
    
    1. Comment out <scope>provided</scope> in the pom.xml (line 40) to enable the embedded Tomcat Server
    
    2. Comment out the annotation @PropertySource("file:/etc/thumby.properties") in class "ThumbyConfiguration"
       (line 32), then automatically the internal application.properties file in src/java/resources will be used,
       otherwise thumby will look for a thumby.properties file locally in /etc.
    
#### For usage in production mode
    
    After finishing in development mode and thumby is ready again for deploying on an external server, you have
    to undo the steps 1. and 2. from above.

## Run

#### With Console/Maven

    # Navigate to project root directory
    cd to.science.thumbs/thumby
    
    # Start thumby
    mvn spring-boot:run

#### With Eclipse IDE

    Right Click on thumby project -> Run as -> Java application

Open in your preferred browser:
http://localhost:8080/thumby (or the port you defined in internal application.properties file)

### Test thumby
Fill in the url of the desired Resource to generate a thumbnail, the size and if the cache should be first cleaned up. 
For example:

    url       -->		https://mars.nasa.gov/system/resources/detail_files/26252_PIA24810---Mound-2D-web.jpg
    size      -->		200
    refresh   -->		Ja


That's all! Thumbnail will be generated.

### Deploy to external Server

    With the following maven command you can produce a thumby.war file in target folder:
    mvn clean install

## License

GNU AFFERO GENERAL PUBLIC LICENSE
Version 3, 19 November 2007
