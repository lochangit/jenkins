def call(Map fileName = [:]) { 
  def fileContents = libraryResource "${fileName.name}"    
  sh "echo ${ABI_ENV_EME_HOST=}"
  //return scriptcontents.replaceAll("(?m)^[ \t]*\r?\n", "").split('\n') 
} 




