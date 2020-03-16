#!/bin/bash

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

rm -rf /home/runner/work/liferay-ide/liferay-ide/liferay-ide-m2-repository/.cache/tycho
echo 00000
rm -rf /home/runner/work/liferay-ide/liferay-ide/liferay-ide-m2-repository/p2/osgi/bundle
echo 11111
./mvnw clean --fail-at-end -e -Dmaven.repo.local=${GITHUB_WORKSPACE}/liferay-ide-m2-repository verify -Dliferay.bundles.dir="./tests-resources"

checkError
