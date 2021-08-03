def call(Map fileName = [:]) { 
  def fileContents = libraryResource "${fileName.name}"    
  echo fileContents
  //return scriptcontents.replaceAll("(?m)^[ \t]*\r?\n", "").split('\n') 
} 




