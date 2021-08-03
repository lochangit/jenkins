def call(Map fileName = [:]) { 
  def fileContents = libraryResource "${fileName.name}"    
  return scriptcontents.replaceAll("(?m)^[ \t]*\r?\n", "").split('\n') 
} 




