/*
   - Get environment variables from configuration file
   - Pass resource file name as input
*/

def call(Map envConfig = [:]) {

    	def varMap = [:]
	def varValue = 'none'

    	if( envConfig.nodeName == 'jenkins-agent-01' ){
        	varMap 	 = readFileAsMap(name: "abinitioEnvAgent01.env")  
		varMap.each{ k, v -> println "${k}:${v}" }    
		//varValue = varMap[${envConfig.variableName}] ?: "novalue1"
    	}
    	else {
		
        	varMap 	 = readFileAsMap(name: "abinitioEnvAgent02.env") 
		varMap.each{ k, v -> println "${k}:${v}" }
		//varValue = varMap[${envConfig.variableName}] ?: "default"
    	}

 return varValue
}



