# Screeen-BroadCast
Screen BroadCast 

It is a simple java tool can broadcast screenshot to other computers.

It can cross platform between linux and windows. 

(Now just support point to point )
## Quickstart ##
download source and build it with maven

    mvm package

then use follow command to start server to receive the screen

    java -jar package-name  server  port screen-width  screen-height
    ##for example:
    java -jar broadcast-1.0-SNAPSHOT-jar-with-dependencies.jar server  12321  1024 768

use follow command to start client to catch and send screen

    java -jar package-name client host port
    #for example:
    java -jar broadcast-1.0-SNAPSHOT-jar-with-dependencies.jar client  192.168.1.2  12321  









