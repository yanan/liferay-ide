#!/bin/bash
echo 0000
ls -al /home/runner/work/_temp/50266612-1576-4459-8385-0d1b88989ce0
echo 0000------------
ls -al ${GITHUB_WORKSPACE}/liferay-ide-m2-repository/.cache/download-maven-plugin/
echo 11111
ls -al /home/runner/work/_temp
echo 22222222222222222
ls -al ${GITHUB_WORKSPACE}/liferay-ide-m2-repository/.cache/download-maven-plugin/

function archiveTests {
	tar cvzf surefire-reports.tar.gz $(find . -name '*surefire-reports*')
}

function checkError {
	local retcode=$?

	if [ $retcode -ne 0 ]; then
		archiveTests

		exit $retcode
	fi
}

./mvnw -e -Dmaven.repo.local=${GITHUB_WORKSPACE}/liferay-ide-m2-repository verify -Dliferay.bundles.dir="./tests-resources"
echo 33333333333333333
ls -al ${GITHUB_WORKSPACE}/liferay-ide-m2-repository/.cache/download-maven-plugin/
echo 4444

checkError
