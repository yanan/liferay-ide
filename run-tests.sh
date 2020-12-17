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

cat /proc/sys/fs/file-max
who
ulimit -Hn
ulimit -Hn

sudo sysctl -w fs.file-max=5000000
ulimit -Hn
ulimit -Hn
cat /proc/sys/fs/file-max

echo 1111
cat /proc/self/limits
./mvnw clean --fail-at-end -e -Dmaven.repo.local=${GITHUB_WORKSPACE}/liferay-ide-m2-repository verify -Dliferay.bundles.dir="./tests-resources"

checkError