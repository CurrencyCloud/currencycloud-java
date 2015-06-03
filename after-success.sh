#!/bin/bash

JAVA_VERSION=`java -version 2>&1 | sed 's/java version "\(.*\)\.\(.*\)\..*"/\1\2/; 1q'`
GIT_BRANCH=`git branch | sed -n -e 's/^\* \(.*\)/\1/p'`

echo "Java version:        " ${JAVA_VERSION}
echo "git branch:          " ${GIT_BRANCH}
echo "Travis pull request: " ${TRAVIS_PULL_REQUEST}
if [ ${JAVA_VERSION} == "17" -a ${GIT_BRANCH} == "master" -a "${TRAVIS_PULL_REQUEST}" == "false" ]; then
    mvn deploy --settings settings.xml
else
    echo "Not deploying artefacts. This is only done with commits to master branch with Java 7 builds."
fi
