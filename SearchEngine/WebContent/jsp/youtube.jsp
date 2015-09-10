<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
 <head>
   <title> Youtube Channel Vids </title>
   <style>
      body {
        background:#f4f4f4;
        font-family:"Arial", sans-serif;
        font-size: 14px;
        color:#666;
        }
        #container{
        width:800px;
        margin:auto;
        overflow:auto;
        background:#fff;
        padding:15px;
        }
      </style>
  <label> <input id="query" value='dogs' type="text"/><button id="search-button"  onclick="search()">Search</button></label>
  </div>
  
  
      <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
      <script type="text/javascript">
      
      
      
      
/*        var channelName = 'TechGuyWeb';
 */      
/*         function getChannel(){
/* 	      var channelName = $('#query').val();
 * 
 
 
 */
	     /*  var channelName = 'TechGuyWeb';
    	  $.get(
    	  "https://www.googleapis.com/youtube/v3/channels",{
    		  part: 'contentDetails',
    		  forUsername: channelName,
    		  key: 'AIzaSyDrLewo50xpkZaTD5KGpL-DfWRuJ9CEtY0'},
    		  function(data){
    			  $.each(data.items, function(i, item){
    				console.log(item);  
    				pid = item.contentDetails.relatedPlaylists.uploads;
    				getVids(pid);
    			  })
    	  }
    	)};   */
    	
    	z
    	
    	
    	
    	function search(){
            gapi.client.setApiKey('AIzaSyDrLewo50xpkZaTD5KGpL-DfWRuJ9CEtY0');
            gapi.client.load('youtube', 'v3', function() {
                     makeRequest();
            });
    }
    function makeRequest() {
            var q = $('#query').val();
            $('#results').empty();
            
            
            var request = gapi.client.youtube.search.list({
                       q: q,
                    part: 'snippet'  
                    
            });
            request.execute(function(response) {
/*             	response.items[1].snippet.title
 
 */             
            
            $.each(response.items, function(i, item){
	
	           console.log(item);
	           videTitle = item.snippet.title;
	           videId = item.id.videoId;
	           console.log(videTitle)
 	           output1 = '<p>' + videTitle + '</p>'

               output = '<li><iframe src = \"//www.youtube.com/embed/' + videId +'\"><iframe></li>'; 
               $('#results').append(output1);
	           $('#results').append(output);
	
	   
            })
 
                    /* var str = JSON.stringify(response.items.length);
                    $('#search-container').html('<pre>' + str + '</pre>'); */
                    
            });
    }
    	
    	
    	
    		 
    	  /* function getVids(pid){
    		  $.get(
    			"https://www.googleapis.com/youtube/v3/playlistItems",{
    				part: 'snippet',
    				maxResults:10,
    				playlistId: pid,
    				key : 'AIzaSyDrLewo50xpkZaTD5KGpL-DfWRuJ9CEtY0'},
    				function(data){
    					var output;
    					$.each(data.items, function(i, item){
    						console.log(item);
    						videTitle = item.snippet.title;
    						
    						output = '<li><iframe src = \"//www.youtube.com/embed/' + videTitle +'\"><iframe></li>';
    						
    						$('#results').append(output);
    					})
    				}
    				);
    			}	     */
    		  
    
  </script>
  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
 <script src="https://apis.google.com/js/client.js?onload=googleApiClientReady"> </script>
  </head>
  <body>
    <div id = "container">
    <h1>Youtube Videos</h1>
    <u1 id = "results"></u1>
    </div>
    <div id="search-container">
  </div>
  </body>
  </html>
  

  
<!-- <!doctype html>
<html>
<head>
<title>Search</title>
</head>
<body>
  <div id="buttons">
  <label> <input id="query" value='dogs' type="text"/><button id="search-button"  onclick="keyWordsearch()">Search</button></label>
  </div>
  <div id="search-container">
  </div>

   <script>
    function keyWordsearch(){
            gapi.client.setApiKey('AIzaSyDrLewo50xpkZaTD5KGpL-DfWRuJ9CEtY0');
            gapi.client.load('youtube', 'v3', function() {
                    makeRequest();
            });
    }
    function makeRequest() {
            var q = $('#query').val();
            var request = gapi.client.youtube.search.list({
                       q: q,
                    part: 'snippet'                        
            });
            request.execute(function(response) {
                    var str = JSON.stringify(response.result);
                    $('#search-container').html('<pre>' + str + '</pre>');
            });
    }
 </script>

 <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
 <script src="https://apis.google.com/js/client.js?onload=googleApiClientReady"> </script> --> 
 
 
 
 
 
 
</body>
</html>


















