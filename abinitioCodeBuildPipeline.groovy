#!/usr/bin/env groovy

@Library('abinitioPipelineSharedLib') _


// Derive the branch names dynamically to be added to build parameters
def ab_eme_branch_list = []

// Pre build configuration stage
node('jenkins-agent-01') {
   stage('Pre build') {
       def ab_eme_branches = sh script: "ls -l /home", returnStdout:true
       ab_eme_branch_list = ab_eme_branches.trim().tokenize("\n")
   }
}



pipeline {
  agent any

  // Build input parameters to be supplied by user
  parameters {
    booleanParam(name: 'REFRESH_PIPELINE', defaultValue: false, description: 'Refresh pipeline without build?')
    string(name: 'ABI_BUILD_TASK_ID', defaultValue: 'Task Id', description: 'Build task id. Format:USNNNN_TKNNNN, USNNNN_DENNNN')
    string(name: 'ABI_BUILD_COMMENT', defaultValue: 'Task Id', description: 'Release comments')
    choice(name: 'ABI_BUILD_BRANCH', choices: ab_eme_branch_list, description: 'Abinitio eme branch')
    choice(name: 'ABI_CHECKOUT_ENV', choices: ['batch', 'online', 'batch_and_online'], description: 'Code checkout environment')
    choice(name: 'ABI_BUILD_DOMAIN', choices: ['con', 'com', 'ids', 'cnc', 'ufo', 'uda'], description: 'Build domain name')
    choice(name: 'ABI_RELEASE_SCOPE', choices: ['minor', 'patch', 'major'], description: 'Code release scope')
    choice(name: 'ABI_TAG_SCOPE', choices: ['exact', 'project', 'eme'], description: 'Abinitio tag scope')
    choice(name: 'ABI_TAG_TYPE', choices: ['build', 'test', 'backup', 'super'], description: 'Abinitio tag type')
    text(name: 'ABI_TAG_OBJECTS', defaultValue: '', description: 'Enter the objects to tag')
    booleanParam(name: 'FORCE_BUILD', defaultValue: false, description: 'Force build with no validations')
  }

  // Add timestamps to the console log
  options {
    timestamps()
  }

  environment {
		ABI_ENV_EME_HOST = ''
		ABI_ENV_EME_ROOT = ''
		ABI_ENV_AB_HOME  = ''
		ABI_TAG_NAMES    = getAbinitioTagName(taskId 		: params.ABI_BUILD_TASK_ID, 
						      buildBranch 	: params.ABI_BUILD_BRANCH,
						      buildDomain 	: params.ABI_BUILD_DOMAIN,
						      releaseScope 	: params.ABI_RELEASE_SCOPE,
						      tagScope 		: params.ABI_TAG_SCOPE,
						      tagType 		: params.ABI_TAG_TYPE,
						      objectList 	: params.ABI_TAG_OBJECTS)
  }

    stages {
        stage('Initialize') {
            input {
                	message "Continue with build?"
                	ok "Yes"
                	parameters {
                    	string(name: 'ABI_BUILD_TASK_ID', defaultValue: params.ABI_BUILD_TASK_ID)
			string(name: 'ABI_BUILD_COMMENT', defaultValue: params.ABI_BUILD_COMMENT)
			string(name: 'ABI_BUILD_BRANCH', defaultValue: params.ABI_BUILD_BRANCH)
			string(name: 'ABI_CHECKOUT_ENV', defaultValue: params.ABI_CHECKOUT_ENV)
			string(name: 'ABI_BUILD_DOMAIN', defaultValue: params.ABI_BUILD_DOMAIN)
			string(name: 'ABI_RELEASE_SCOPE', defaultValue: params.ABI_RELEASE_SCOPE)
			string(name: 'ABI_TAG_SCOPE', defaultValue: params.ABI_TAG_SCOPE)
			string(name: 'ABI_TAG_TYPE', defaultValue: params.ABI_TAG_TYPE)
			booleanParam(name: 'FORCE_BUILD', defaultValue: params.FORCE_BUILD)
                	}
            }

            	steps {
                	script { 
				// Customize the build display name with task id prefixed to build number
				currentBuild.displayName = "${ABI_BUILD_TASK_ID}#${BUILD_NUMBER}"
                	}

			// Validate input parameters
			abinitioPipelineValidateInput(taskId 		: params.ABI_BUILD_TASK_ID, 
						      taskComments 	: params.ABI_BUILD_COMMENT,
						      buildBranch 	: params.ABI_BUILD_BRANCH,
						      checkoutEnv 	: params.ABI_CHECKOUT_ENV,
						      buildDomain 	: params.ABI_BUILD_DOMAIN,
						      releaseScope 	: params.ABI_RELEASE_SCOPE,
						      tagScope 		: params.ABI_TAG_SCOPE,
						      tagType 		: params.ABI_TAG_TYPE,
						      objectList 	: params.ABI_TAG_OBJECTS)		       
            	}

    		post {

        		success {
            			echo "Initialization complete with following parameters : "

                		script { 
    					if(params.REFRESH_PIPELINE) {
        					echo "REFRESH_PIPELINE : True"
    					} else {
        					echo "REFRESH_PIPELINE : False"
    					}
                		}

                    		echo "ABI_BUILD_TASK_ID : ${params.ABI_BUILD_TASK_ID}"
                    		echo "ABI_BUILD_COMMENT : ${params.ABI_BUILD_COMMENT}"
				echo "ABI_BUILD_BRANCH : ${params.ABI_BUILD_BRANCH}"
				echo "ABI_CHECKOUT_ENV : ${params.ABI_CHECKOUT_ENV}"
				echo "ABI_BUILD_DOMAIN : ${params.ABI_BUILD_DOMAIN}"
				echo "ABI_RELEASE_SCOPE : ${params.ABI_RELEASE_SCOPE}"
				echo "ABI_TAG_SCOPE : ${params.ABI_TAG_SCOPE}"
				echo "ABI_TAG_TYPE : ${params.ABI_TAG_TYPE}"
				echo "ABI_TAG_OBJECTS : ${params.ABI_TAG_OBJECTS}"

                		script { 
    					if(params.FORCE_BUILD) {
        					echo "FORCE_BUILD : True"
    					} else {
        					echo "FORCE_BUILD : False"
    					}
                		}
        		}

        		failure {
            			// sendNotifications currentBuild.result
                                echo "Build failed"
        		}
    		}

        }

        stage('CreateTag') {

  		// Set additional environment variables from configuration files based on agent name
  		environment {
			ABI_ENV_EME_HOST = getEnvVariables(nodeName : env.NODE_NAME, variableName : "ABI_ENV_EME_HOST")
			ABI_ENV_EME_ROOT = getEnvVariables(nodeName : env.NODE_NAME, variableName : "ABI_ENV_EME_ROOT")
			ABI_ENV_AB_HOME  = getEnvVariables(nodeName : env.NODE_NAME, variableName : "ABI_ENV_AB_HOME")
  		}

            	steps {
			echo "Creating tag with input parameters..."

			// Create tag
			createAbinitioTag(taskId 	: params.ABI_BUILD_TASK_ID, 
					  taskComments 	: params.ABI_BUILD_COMMENT,
					  buildBranch 	: params.ABI_BUILD_BRANCH,
					  buildDomain 	: params.ABI_BUILD_DOMAIN,
					  releaseScope 	: params.ABI_RELEASE_SCOPE,
					  tagScope 	: params.ABI_TAG_SCOPE,
					  tagType 	: params.ABI_TAG_TYPE,
					  objectList 	: params.ABI_TAG_OBJECTS
					  tagNames	: env.ABI_TAG_NAMES)
            	}

    		post {

        		success {
            			echo "Created tag successfully ${env.ABI_TAG_NAMES}"
        		}

        		failure {
            			// sendNotifications currentBuild.result
                                echo "Build failed"
        		}
    		}
        }





    }
}

