
# Thumbs: Thumbnail Generator!

Thumbs is a thumbnail generator as a web service. Pass an image or a pdf with an optional size to the service and get a thumbnail image back.
It supports pdfs and image files.

# Requirements

 1. Java 11 (or Java 8)
 2. Maven Build Tool

# Getting Started

## Download

#### With Console
    # Select desired location
    cd /myPath
    
    # Download thumbs
    git clone https://github.com/hbz/to.science.thumbs.git
    
    # Go to project directory
    cd to.science.thumbs

#### With Eclipse IDE

    Import -> Maven -> Existing Maven Project -> Select project root directory 

or

    Git Repositories -> Clone a Git Repository -> Enter https://github.com/hbz/to.science.thumbs.git

## Run

#### With Console/Maven

    # Navigate to project root directory
    cd to.science.thumbs/thumby
    
    # Start thumbs
    mvn spring-boot:run

#### With Eclipse IDE

    Right Click on thumbs project -> Run as -> Java application

Open in your preferred browser:
http://localhost:9001/thumbs (port is currently set to 9001, could be changed in application-dev.properties file)

### Test thumbs
Fill in the url of the desired Resource to generate a thumbnail, the size and if the cache should be first cleaned up. 
For example:

    url       -->		https://mars.nasa.gov/system/resources/detail_files/26252_PIA24810---Mound-2D-web.jpg
    size      -->		200
    refresh   -->		Ja


That's all! Thumbnail will be generated.

### Create war file to deploy to external Tomcat Server
 
    # Navigate to project root directory
    cd to.science.thumbs/thumby
    
    # Execute maven command to create thumbs.war (name defined in pom.xml) in target folder:
    mvn clean install
    
## License

GNU AFFERO GENERAL PUBLIC LICENSE
Version 3, 19 November 2007
