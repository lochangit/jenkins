def call(Map tagInput = [:]) {
    loadPythonScript(name: 'createAbinitioTag.py')
    tagName = sh(returnStdout: true, script: "./createAbinitioTag.py ${tagInput.taskId} ${tagInput.taskComments} ${tagInput.buildBranch} ${tagInput.buildDomain} ${tagInput.releaseScope} ${tagInput.tagScope} ${tagInput.tagType} ${tagInput.objectList}")
  echo "Created tag successfully ${tagName}"
}



