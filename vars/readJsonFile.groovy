/*
   - Read Json file's content and returns map
   - Pass resource file name as input
*/

def call(Map fileName = [:]) { 
  // def jsonObj = readJSON file: "${fileName.name}"

  def jsonObj = readJson text: '''{
    "tagList": [
      		  {
        	    "tagName" 	   : "sample_tag_1",
		    "projectPath"  : "/project1/path",
    		    "objectList"   : ["obj1",
      		  	   	      "obj2" ]
      		  },

      		  {
        	    "tagName" 	   : "sample_tag_2",
		    "projectPath"  : "/project2/path",
    		    "objectList"   : ["obj4",
      		  	   	      "obj5" ]
      		  },

      		  {
        	    "tagName" 	   : "sample_tag_3",
		    "projectPath"  : "/project3/path",
    		    "objectList"   : ["obj6",
      		  	   	      "obj7" ]
      		  }
                ]
}'''

  echo jsonObj.tagList[0].tagName
} 





