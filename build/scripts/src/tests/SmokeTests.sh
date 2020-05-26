# create liferay 7.3 workspace and set workspace plugin to 2.4.0
mkdir ws-smoke-test
cd ws-smoke-test
blade init -v 7.3
sed -i 's/2.3.1/2.4.0/g' settings.gradle

# clean gradle.properties and set product key
:>gradle.properties
echo "liferay.workspace.product = dxp-7.2-ga1" > gradle.properties

# init bundle and check tomcat version
./gradlew initBundle

if [ $? -eq 0 ]
then
    "Task initBundle build successfully"
else
    "Task initBundle build failed"
fi

BUNDLE_FILE="liferay-dxp-tomcat-7.2.10-ga1-20190531140450482.tar.gz"
if [ ! -f "${HOME}/.liferay/bundles/${BUNDLE_FILE}"]
then
    echo "Bundle file doesn't exist."
    exit 0

if [ ! -d "bundles/tomcat-9.0.17" ]
then
    echo "Tomcat version is correct."
    exit 0
fi

# create a mvc-portlet project and check no version in build.gradle
blade create -t mvc-portlet sample
FIND_STR="version:"
FIND_IN_FILE="modules/sample/build.gradle"

if [ "grep -0 '$FIND_STR' < $FIND_IN_FILE|wc -l"==1 ]
then
    echo "Target platform works. There are no versions except for cssBuilder."
else
    echo "Please check build.gradle file."
    exit 0
fi

# build workspace project
./gradlew build
if [ $? -eq 0 ]
then
    echo "Workspace project build successfully"
else
    echo "Workspace project build failed"
    exit 0
fi

# start and stop server
blade server start

FIND_START_STR="Server startup in"
FIND_START_IN_FILE="bundles/tomcat-9.0.17/logs/catalina.out"
if [ `grep -c "$FIND_START_STR" $FIND_START_IN_FILE` -ne '0' ]
then
    echo "Server start successfully"
else
    echo "Error to start server"
    exit 0
blade server stop

# docker container start and stop
./gradlew startDockerContainer
then
    "Docker container start successfully"
else
    "Failed to start docker conatiner"
fi

./gradlew stopDockerContainer
if [ $? -eq 0 ]
then
    "Docker container stop successfully"
else
    "Failed to stop docker container"
fi

cd ..
rm -rf ws-smoke-testFIND_IN_FILE
