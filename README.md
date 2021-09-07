
# Thumby: Thumbnail Generator!

Thumby is a thumbnail generator as a web service. Pass an image or a pdf with an optional size to the service and get a thumbnail image back.
It supports pdfs and image files.

# Requirements

 1. Java 11
 2. Maven

# Getting Started

## Download

#### With Console
    # Select desired location
    cd /myPath
    
    # Thumby will be downloaded
    git clone https://github.com/hbz/to.science.thumbs.git
    
    # Go to thumby directory
    cd to.science.thumbs

#### With Eclipse IDE

    Import -> Maven -> Existing Maven Project -> Select project root directory 

or

    Git Repositories -> Clone a Git Repository -> Enter https://github.com/hbz/to.science.thumbs.git


## Configure

The thumby -> src -> main -> resources -> application.properties file provide the possibility to configure the following settings:

    
    # Specify a port
    server.port=9001 (default port)
    
    # Provide a list of servers, that thumby is allowed to connect to.
    thumby.whitelist=localhost,127.0.0.1,pbs.twimg.com
    
    # Specify a location where thumby stores thumbnails
    thumby.storageLocation=/tmp/thumby-storage
    
    

## Run

#### With Console/Maven

    # Navigate to project root directory
    cd to.science.thumbs/thumby
    
    # Start thumby
    mvn spring-boot:run

#### With Eclipse IDE

    Right Click on thumby project -> Run as -> Java application


Then go to http://localhost:9001/tools/thumby (or the port you defined above)

### Test
Fill in the url of the desired Resource to generate a thumbnail, the size and if the cache should be first cleaned up. 
For example:

    url       -->		https://repository.publisso.de/resource/frl:6414860
    size      -->		200
    refresh   -->		Ja	


That's all! Thumbnail will be generated.


## License

GNU AFFERO GENERAL PUBLIC LICENSE
Version 3, 19 November 2007
