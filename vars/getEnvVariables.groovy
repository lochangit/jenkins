/*
   - Get environment variables from configuration file
   - Pass resource file name as input
*/

def call(Map envConfig = [:]) {

    	def varMap = []
	def varValue = 'none'

    	if( envConfig.nodeName == 'jenkins-agent-01' ){
        	varMap 	 = readFileAsList(name: "abinitioEnvAgent01.env")
      
		varValue = def value = mymap[envConfig.variableName] ?: "novalue1"
    	}
    	else {
        	varMap 	 = readFileAsList(name: "abinitioEnvAgent02.env")
		varValue = def value = mymap[envConfig.variableName] ?: "default"
    	}

 return varValue
}



