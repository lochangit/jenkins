def call(Map fileName = [:]) { 
  def fileContents = libraryResource "${fileName.name}"    
  return fileContents.replaceAll("(?m)^[ \t]*\r?\n", "").split('\n') as List
} 




