# The Goose Game Kata
Goose game is a game where two or more players move pieces around a track by rolling a die. The aim of the game is to reach square number sixty-three before any of the other players and avoid obstacles. ([wikipedia](https://en.wikipedia.org/wiki/Game_of_the_Goose))

## Building and running 

- This application is written using Java 11 and Maven at least 3.4.X
- By default Java 11 is required also to run the application. As an alternative you can try to compile for a lower JDK version changing "maven.compiler.target" property in pom.xml file
- To run the application one can:
    - run it through maven with the following command "mvn exec:java"
    - from command line with standard java $JAVA_HOME/bin/java --modulepath <PROJECT_ROOT>/target/classes -m com.xpeppers.goosegame/com.xpeppers.goosegame.App
    - from command line running the jar $JAVA_HOME/bin/java -jar <PROJECT_ROOT>/target/goosegame-1.0-SNAPSHOT.jar

