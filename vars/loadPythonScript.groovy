def call(Map pyScript = [:]) { 
  def scriptcontents = libraryResource "${pyScript.name}"    
  writeFile file: "${pyScript.name}", text: scriptcontents 
  sh "chmod a+x ./${pyScript.name}"
} 
