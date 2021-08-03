def call(Map envConfig = [:]) {
	withEnv(readFileAsList(name: "${envConfig.name}")) {
    		sh "echo ${ABI_ENV_EME_HOST}"
	}
}



