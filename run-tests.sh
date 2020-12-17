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
echo 999999999999999
cat /etc/security/limits.conf
echo 000
ulimit -aH
cat /proc/sys/fs/file-max
who
ulimit -Hn
ulimit -Hn

sudo sysctl -w fs.file-max=5000000
ulimit -Hn
ulimit -Hn
cat /proc/sys/fs/file-max
echo 1111
sudo sysctl kernel.pid_max=126820
echo 2222
cat /proc/self/limits
export DISPLAY=:0
echo 666666666
ulimit -n 90000
./mvnw clean --fail-at-end -e -Dmaven.repo.local=${GITHUB_WORKSPACE}/liferay-ide-m2-repository verify -Dliferay.bundles.dir="./tests-resources"

checkError