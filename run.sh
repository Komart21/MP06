#!/bin/bash

# run.sh

# Change the working directory to where the script is located
cd "$(dirname "$0")"

# Set MAVEN_OPTS environment variable for compatibility with Java modules
export MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED"

# Main class to run; default is 'cat.iesesteveterradas.PR210Honor'
mainClass="cat.iesesteveterradas.PR210Honor"

# Check if a main class argument was passed
if [ ! -z "$1" ]; then
  mainClass="$1"
fi

echo "Setting MAVEN_OPTS to: $MAVEN_OPTS"
echo "Main Class: $mainClass"

# Maven arguments for the main class
mavenMainClassArg="-Dexec.mainClass=$mainClass"

# Remove the first argument (mainClass) so the rest can be passed to Maven as program arguments
shift

# Collect the rest of the arguments for the Java program
javaArgs="$@"

# Pass the remaining arguments to Java using exec.args
execArgsForJava="-Dexec.args=\"$javaArgs\""

echo "Maven Main Class Argument: $mavenMainClassArg"
echo "Java Program Arguments: $javaArgs"

# Execute mvn command with clean, compile, and run phases
mvn clean compile exec:java $mavenMainClassArg $execArgsForJava
