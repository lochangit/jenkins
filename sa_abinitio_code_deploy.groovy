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
    booleanParam(name: 'ABI_MAJOR_RELEASE', defaultValue: false, description: 'Is major code release?')
    choice(name: 'ABI_TAG_SCOPE', choices: ['exact', 'project', 'eme'], description: 'Abinitio tag scope')
    choice(name: 'ABI_TAG_TYPE', choices: ['build', 'test', 'backup', 'super'], description: 'Abinitio tag type')
    text(name: 'ABI_TAG_OBJECTS', defaultValue: '', description: 'Enter the objects to tag')
    booleanParam(name: 'FORCE_BUILD', defaultValue: false, description: 'Refresh pipeline without build?')
  }

  stages {
    stage('Example') {
      steps {
        /* WRONG! */
        sh("echo ${ABI_BUILD_TASK_ID}")
      }
    }
  }
}

