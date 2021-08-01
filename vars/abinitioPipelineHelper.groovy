/* Echo environment parameter */
def echoEnvVars() {
	CURR_JAVA_VERSION = sh "java --version"
	CURR_HOST_NAME = sh "hostname"
        CURR_WORK_DIR = sh "pwd"
	echo "Java version : ${CURR_JAVA_VERSION}"
	echo "Host name : ${CURR_HOST_NAME}"
        echo "Build id : ${env.BUILD_NUMBER}"	
	echo "Current working dir : ${CURR_WORK_DIR}"
    	echo "Path : ${env.PATH}"    
}

