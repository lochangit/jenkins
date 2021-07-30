/* Echo environment parameter */
def echoEnvVars() {
	CURR_JAVA_VERSION = sh "java --version"
	CURR_HOST_NAME = sh "hostname"
	echo "Java version : ${CURR_JAVA_VERSION}"
	echo "Host name : ${CURR_HOST_NAME}"
        echo "Build id : ${env.BUILD_NUMBER}"	
	echo "Current working dir : ${pwd()}"
    	echo "Path : ${env.PATH}"    
}

