/*
   - Get environment variables from configuration file
   - Pass resource file name as input
*/

def call(Map envConfig = [:]) {

    	def varMap = [:]
	def varValue = 'none'

    	if( envConfig.nodeName == 'jenkins-agent-01' ){
        	varMap 	 = readFileAsMap(name: "abinitioEnvAgent01.env")  
		varValue = varMap[envConfig.variableName] ?: ""
    	}
    	else {
		
        	varMap 	 = readFileAsMap(name: "abinitioEnvAgent02.env") 
		varValue = varMap[envConfig.variableName] ?: ""
    	}

 return varValue
}



