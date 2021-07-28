#!/usr/bin/env groovy

def ab_eme_branch_list = []

node('jenkins-agent-01') {
   stage('Pre build') {
       // Pre build configuration stage
       def ab_eme_branches = sh script: "ls -l /home", returnStdout:true
       ab_eme_branch_list = ab_eme_branches.trim().tokenize("\n")
   }
}


pipeline {
  agent any
  parameters {
    booleanParam(name: 'REFRESH_PIPELINE', defaultValue: false, description: 'Refresh pipeline without build?')
    string(name: 'ABI_BUILD_TASK_ID', defaultValue: 'Task Id', description: 'Build task id. Format:Userstory_TaskId, Userstory_DefectId')
    choice(name: 'ABI_BUILD_BRANCH', choices: ab_eme_branch_list, description: 'Abinitio eme branch')
    choice(name: 'ABI_CHECKOUT_ENV', choices: ['BATCH', 'ONLINE', 'BATCH_AND_ONLINE'], description: 'Code checkout environment')
    choice(name: 'ABI_BUILD_DOMAIN', choices: ['con', 'com', 'ids', 'cnc', 'ufo', 'uda'], description: 'Build domain name')
    choice(name: 'ABI_RELEASE_SCOPE', choices: ['major', 'minor', 'patch'], description: 'Code release scope')
    choice(name: 'ABI_TAG_SCOPE', choices: ['exact', 'project', 'eme'], description: 'Abinitio tag scope')
    choice(name: 'ABI_TAG_TYPE', choices: ['build', 'test', 'backup', 'super'], description: 'Abinitio tag type')
    text(name: 'ABI_TAG_OBJECTS', defaultValue: '', description: 'Enter the objects to tag')
    booleanParam(name: 'FORCE_BUILD', defaultValue: false, description: 'Force build with no validations')
  }

  // Add timestamps to the console log
  options {
    timestamps()
  }

    stages {
        stage('Example') {
            input {
                message "Should we continue?"
                ok "Yes, we should."
                submitter "alice,bob"
                parameters {
                    string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
                }
            }
            steps {
                echo "Hello, ${PERSON}, nice to meet you."
            }
        }
    }

}

