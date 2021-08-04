/*
   - Set environment variables from configuration file
   - Pass resource file name as input
*/

def call(Map envConfig = [:]) {
	withEnv(readFileAsList(name: "${envConfig.name}")) {
    		sh "echo ${ABI_ENV_EME_HOST}"
	}
}



