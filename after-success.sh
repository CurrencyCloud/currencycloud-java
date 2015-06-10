#!/bin/bash

# This script is used by Travis-CI to publish artifacts (binary, sorce and javadoc jars) when releasing snapshots.
# This script is referenced in .travis.yml.

GIT_BRANCH=`git branch | sed -n -e 's/^\* \(.*\)/\1/p'`

echo "Travis branch:       " ${TRAVIS_BRANCH}
echo "Travis pull request: " ${TRAVIS_PULL_REQUEST}
echo "Travis JDK version:  " ${TRAVIS_JDK_VERSION}
if [ "${TRAVIS_JDK_VERSION}" == "oraclejdk7" -a "${TRAVIS_BRANCH}" == "master" -a "${TRAVIS_PULL_REQUEST}" == "false" ]; then
    mvn deploy --settings settings.xml
else
    echo "Not deploying artifacts. This is only done with non-pull-request commits to master branch with Oracle Java 7 builds."
fi
