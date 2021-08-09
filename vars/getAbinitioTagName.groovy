def call(Map tagInput = [:]) {
    loadPythonScript(name: 'createAbinitioTag.py')
    sh(returnStdout: true, script: "echo ${tagInput.buildDomain}.project_name.${tagInput.buildBranch}.${tagInput.taskId}.${tagInput.tagScope}.101.10.20210202.121212.${tagInput.tagType}")
}


