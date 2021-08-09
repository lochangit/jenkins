/*
   	- Validate user inputs provided to Abinitio Build pipeline
   	- Task ID : If task is not in this format AANNNN_AANNNN then throw an error.
	- Task ID : If task is not in this format AANNNN_AANNNN then throw an error.
	- Task comment : If task is blank, then throw an error.
	- Build branch : If build branch is blank, then throw an error.
	- Check out environment : Check out environment is not either of these ['batch', 'online', 'batch_and_online'], then throw an error.
	- Build domain : Build domain is not either of these ['con', 'com', 'ids', 'cnc', 'ufo', 'uda'], then throw an error.
	- Release scope : Release scope is not either of these ['exact', 'project', 'eme'], then throw an error.
	- Tag type : Tag type is not either of these ['build', 'test', 'backup', 'super'], then throw an error.
	- Object list : If object list is blank, then throw an error.
*/

def call(Map inputParams = [:]) {

	// Validation for task ID
    	if( !(inputParams.taskId ==~ /([a-zA-Z]{2}\d{4}_[a-zA-Z]{2}\d{4})/)) {
        	 throw new Exception("Invalid task id : ${inputParams.taskId}.Correct and resubmit the build") 
    	}

	// Validation for task comment
        if (!inputParams.taskComments?.trim()) {
        	 throw new Exception("Task comments is blank. Correct and resubmit the build") 
    	}

	// Validation for branch
        if (!inputParams.buildBranch?.trim()) {
        	 throw new Exception("Build branch is blank. Correct and resubmit the build") 
    	}

	// Validation for checkout environment
        if ( !(inputParams.checkoutEnv in ['batch', 'online', 'batch_and_online']) ) {
        	 throw new Exception("Checkout environment is blank. Correct and resubmit the build") 
    	}

	// Validation for build domain
        if ( !(inputParams.buildDomain in ['con', 'com', 'ids', 'cnc', 'ufo', 'uda']) ) {
        	 throw new Exception("Build domain is blank. Correct and resubmit the build") 
    	}

	// Validation for release scope
        if ( !(inputParams.releaseScope in ['minor', 'patch', 'major']) ) {
        	 throw new Exception("Release scope is blank. Correct and resubmit the build") 
    	}

	// Validation for tag scope
        if ( !(inputParams.tagScope in ['exact', 'project', 'eme']) ) {
        	 throw new Exception("Tag scope is blank. Correct and resubmit the build") 
    	}

	// Validation for tag type
        if ( !(inputParams.tagType in ['build', 'test', 'backup', 'super']) ) {
        	 throw new Exception("Tag type is blank. Correct and resubmit the build") 
    	}

	// Validation for objects list
        if (!inputParams.objectList?.trim()) {
        	 throw new Exception("Objects list is blank. Correct and resubmit the build") 
    	}
}

