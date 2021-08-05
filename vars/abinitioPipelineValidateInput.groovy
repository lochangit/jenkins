/*
   - Validate user inputs provided to Abinitio Build pipeline
*/

def call(Map inputParams = [:]) {

	// Validation for task ID
    	if( !(inputParams.taskId ==~ /([a-zA-Z]{2}\d{4}_[a-zA-Z]{2}\d{4})/)) {
        	 throw new Exception("Invalid input task id : ${inputParams.taskId}.Correct and resubmit build") 
    	}
}

