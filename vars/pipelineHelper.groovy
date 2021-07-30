/* Echo environment parameter */
def echoEnvVars() {
	CURR_JAVA_VERSION = sh "java --version"
	CURR_HOST_NAME = sh "hostname"
	echo "Build number : ${CURR_JAVA_VERSION}"
	echo "Build number : ${CURR_HOST_NAME}"
        echo "Build number : ${env.BUILD_NUMBER}"	
	echo "Current working dir : ${pwd()}"
    	echo "Path : ${env.PATH}"    
    	//echo "Abinitio EME host : ${env.ABI_EME_HOST}" 
        //echo "Abinitio EME root : ${env.ABI_EME_ROOT}" 
	//echo "Working directory : ${env.BUILD_WORK_DIR}" 
	//echo "Log directory : ${env.BUILD_LOG_DIR}"
}

