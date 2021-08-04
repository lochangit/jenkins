/*
   - Read file's content and returns as a list
   - Pass resource file name as input
*/

def call(Map fileName = [:]) { 
  def fileContents = libraryResource "${fileName.name}"   
  def map 	   = [:] 

	map = data.tokenize("\n").collectEntries {
    			it.tokenize("=").with {
        			[(it[0]):it[1]]
    			}
	      }

  return map
} 




