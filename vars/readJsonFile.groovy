/*
   - Read Json file's content and returns map
   - Pass resource file name as input
*/

def call(Map fileName = [:]) { 

   def jsonObj = readJSON file: "${fileName.name}"

    for (int i = 0; i < jsonObj.tagList.size(); i++) {
        echo jsonObj.tagList[i].tagName
        echo jsonObj.tagList[i].projectPath
    }
} 





