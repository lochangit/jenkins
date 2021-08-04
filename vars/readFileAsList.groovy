/*
   - Read file's content and returns as a list
   - Pass resource file name as input
*/

def call(Map fileName = [:]) { 
  def fileContents = libraryResource "${fileName.name}"    
  return fileContents.replaceAll("(?m)^[ \t]*\r?\n", "").split('\n') as List
} 




