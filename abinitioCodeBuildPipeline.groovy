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
  parameters {
    booleanParam(name: 'REFRESH_PIPELINE', defaultValue: false, description: 'Refresh pipeline without build?')
    string(name: 'ABI_BUILD_TASK_ID', defaultValue: 'Task Id', description: 'Build task id. Format:Userstory_TaskId, Userstory_DefectId')
    string(name: 'ABI_BUILD_COMMENT', defaultValue: 'Task Id', description: 'Build task id. Format:Userstory_TaskId, Userstory_DefectId')
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

  // Set additional environment variables

  environment {
	ABI_ENV_EME_HOST=getEnvVariables(nodeName : env.NODE_NAME, variableName : "ABI_ENV_EME_HOST")
  }

    stages {
        stage('Initialize') {
            input {
                	message "Continue with build?"
                	ok "Yes"
                	parameters {
                    	string(name: 'ABI_BUILD_TASK_ID', defaultValue: params.ABI_BUILD_TASK_ID)
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

			echo env.NODE_NAME
			//setEnvVariables(name: "abinitioEnvAgent01.env")
            	}
        }
    }

}

