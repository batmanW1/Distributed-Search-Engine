
 
 
 

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8"> 
<meta name = "view" content = "width=device-width, initial-scale=1.0">
<title>Search Engine</title>
<link href = "http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/boostrap.min.js">

<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css">


<style>
html,body{background-color:#ffffff;}

.row{background:url(http://en.wikipedia.org/wiki/Yellowstone_Caldera#/media/File:Yellowstone_River_in_Hayden_Valley.jpg)}

body {
    background-color: background-color: #FFCC66;
}

h1 {
    color: black;
    text-align: center;
    font-family: "Times New Roman";
}

h4 {font-family: "Times New Roman";

color: black;

}






h2 {font-family: "Times New Roman", serif;

color: black;

}

p {
    font-family: "Times New Roman";
    font-size: 20px;
}

#center {
    width: 8%;
    margin: 0 auto;
}

div {
    display: block;
}


#foodie-container {
    padding: 5px;
    float: left;
    width: 980px;
}
.foodie-category {
    float: left;
    margin: 2px 5px 15px 5px;
    padding: 2px;
    width: 124px;
}
.foodie-category li {
    list-style: none !important;
}
.foodie-category a {
    color: #167;
    font: bold 12px Arial, Helvetica, sans-serif;
    text-decoration: none;
}
.foodie-category a:hover {
    text-decoration: underline;
}
.foodie-winner {
    border: 1px solid #ccc;
    float: left;
    height: 290px;
    margin: 2px 6px 10px 6px;
    overflow: hidden;
    padding: 4px;
    width: 304px;
}
article, header, nav, section {
    display: block;
}
small {
    font-size: 11px;
}
h3 {
    border-bottom: 2px dotted #ccc;
    color: #167;
    font: bold 16px Arial, Helvetica, sans-serif;
    margin: 2px 0 5px;
    padding: 2px;
}
s


</style>
</head>




<body>


<%String queryJsp = "123";
String[] titlesJsp1 = {"123Greetings","23Greetings","23Greetings","23Greetings","23Greetings","23Greetings","23Greetings","23Greetings","23Greetings","23Greetings","23Greetings"};
String titlesJsp="123Greetings" ;
String urlsJsp="http://www.123greetings.com/";
String[] urlsJsp1 = {"http://www.123greetings.com/","http://www.123greetings.com/","http://www.123greetings.com/","http://www.123greetings.com/","http://www.123greetings.com/","http://www.123greetings.com/","http://www.123greetings.com/","http://www.123greetings.com/","http://www.123greetings.com/","http://www.123greetings.com/","http://www.123greetings.com/"};
int recordsJsp1 = titlesJsp1.length;
int recordsJsp = 1;
%>


<div class="navbar navbar-default navbar-fixed-top" role="navigation">

    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" rel="home" href="/"  - Homepage">Search Engine</a>
    </div>

    <div class="collapse navbar-collapse navbar-ex1-collapse">

        

        <div class="col-sm-3 col-md-3 pull-right">
        <form class="navbar-form" role="search">
        <div class="input-group">
            <input type="text" class="form-control" placeholder="Search" name="srch-term" id="srch-term">
            <div class="input-group-btn">
                <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
            </div>
        </div>
        </form>
        </div>

    </div>
</div>

<br>
<br>
<br>



<h1> Search Result</h1>


   <div class="row">
        <div class="col-md-1">&nbsp;</div>
        <div class="col-lg-8">
            <div class="spellcheck">
             <h4>  <div id = "center"> <%= recordsJsp1%> results </div></h4>
            </div>
        </div>
    </div>
   
    <div  class ="container-fluid" >
    <%-- <% for (int i = 0; i < 10; i++) { %>
         <a href = '<%= urlsJsp[i]%>'>
            <h2> <%= i+1 %>    
            <%=    titlesJsp[i] %></h2>
            <h4><%= urlsJsp[i]%></h4>
           
         </a>
         <%} %>  --%>
         
         <script type="text/javascript">
         
            function myFunction1(){
            	$("h4").empty();
            	<%for (int j = 0; j < 10; j++){ %>
            		
            		var links = "<h2>"+ <%=(j+1)%> + "</h2>";
            		links +="<%=titlesJsp1[j]%>";
            		/*links += titlesJsp[(x-1)*10 +j] +" </h2>";*/
            		/*links += " <h4> " + urlsJsp[(x-1)*10 +j]+"</h4>";*/
            		$('h4').append(links);
            		 
            		
            
                    <%-- var links1 ;
            	   
             		    links1 = "<div class=\"modal fade\" id=\"<%= \"myModal\" + String.valueOf(j)%>\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">";
             		    
                      links1 =  "<div class=\"modal-dialog modal-lg\">";
            		    links1 +=  "<div class=\"modal-content\">";
            		    links1 += "<div class=\"modal-header\">";
            		    links1 += "<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>";
            		    links1 += "<h4 class='modal-title' id='myModalLabel'><%= titlesJsp1[i]%></h4>";
            		    links1 += "</div>";
            		                
            		    links1 += "<div class='modal-body'>";
            		     links1 += "<div>";
            		     links1 += "<iframe height = '400px' width='100%' src='<%= urlsJsp[i]%>' sandbox=''> </iframe>";
            		      links1 += "</div>";
            		      links1 += "</div>";
            		                
            		      
            		       links1 +=  "<div class='modal-footer'>";
            		                    
            		       
            		       links1 += "<button type'button' class='btn btn-default' data-dismiss='modal'>Close</button>";
            		                
            		       links1 += "</div>";
            		            
            		       links1 += "</div>";
            		        links1 += "</div>";
            		    links1 += "</div>";
            		    <% } %>   --%>
            		
     
            		
            		    
            		    
            		    
            		    
            		    
            		    
            		    
            		    
            	<% } %>
            }
 
            function myFunction2(){
            	$("h4").empty();
            	<%for (int j = 10; j < 20; j++){ %>
            		<% if (j < titlesJsp1.length) { %>
            		var links = "<h2>"+ <%=(j+1)%> + "</h2>";
            		links +="<%=titlesJsp1[j]%>";
	            		/*links += titlesJsp[(x-1)*10 +j] +" </h2>";*/
	            		/*links += " <h4> " + urlsJsp[(x-1)*10 +j]+"</h4>";*/
	            		$('h4').append(links);
	            	<% } %>
            	<% } %>
            }
            function myFunction3(){
            	$("h4").empty();
            	<%for (int j = 20; j < 30; j++){ %>
            		<% if (j < titlesJsp1.length) { %>
            		var links = "<h2>"+ <%=(j+1)%> + "</h2>";
            		links +="<%=titlesJsp1[j]%>";
	            		/*links += titlesJsp[(x-1)*10 +j] +" </h2>";*/
	            		/*links += " <h4> " + urlsJsp[(x-1)*10 +j]+"</h4>";*/
	            		$('h4').append(links);
	            	<% } %>
            	<% } %>
            }
         
            function myFunction4(){
            	$("h4").empty();
            	<%for (int j = 30; j < 40; j++){ %>
        		<% if (j < titlesJsp1.length) { %>
        		var links = "<h2>"+ <%=(j+1)%> + "</h2>";
        		links +="<%=titlesJsp1[j]%>";
            		/*links += titlesJsp[(x-1)*10 +j] +" </h2>";*/
            		/*links += " <h4> " + urlsJsp[(x-1)*10 +j]+"</h4>";*/
            		$('h4').append(links);
            	<% } %>
        	<% } %>
            }
            function myFunction5(){
            	$("h4").empty();
            	<%for (int j = 40; j < 50; j++){ %>
            		<% if (j < titlesJsp1.length) { %>
            		var links = "<h2>"+ <%=(j+1)%> + "</h2>";
            		links +="<%=titlesJsp1[j]%>";
	            		/*links += titlesJsp[(x-1)*10 +j] +" </h2>";*/
	            		/*links += " <h4> " + urlsJsp[(x-1)*10 +j]+"</h4>";*/
	            		$('h4').append(links);
	            	<% } %>
            	<% } %>
            }
            function myFunction6(){
            	$("h4").empty();
            	<%for (int j = 50; j < 60; j++){ %>
            		<% if (j < titlesJsp1.length) { %>
            		var links = "<h2>"+ <%=(j+1)%> + "</h2>";
            		links +="<%=titlesJsp1[j]%>";
	            		/*links += titlesJsp[(x-1)*10 +j] +" </h2>";*/
	            		/*links += " <h4> " + urlsJsp[(x-1)*10 +j]+"</h4>";*/
	            		$('h4').append(links);
	            	<% } %>
            	<% } %>
            }
            function myFunction7(){
            	$("h4").empty();
            	<%for (int j = 60; j < 70; j++){ %>
            		<% if (j < titlesJsp1.length) { %>
            		var links = "<h2>"+ <%=(j+1)%> + "</h2>";
            		links +="<%=titlesJsp1[j]%>";
	            		/*links += titlesJsp[(x-1)*10 +j] +" </h2>";*/
	            		/*links += " <h4> " + urlsJsp[(x-1)*10 +j]+"</h4>";*/
	            		$('h4').append(links);
	            	<% } %>
            	<% } %>
            }
         
         
         
         
         
         </script>
         
         
         <% for (int i = 0; i < recordsJsp; i++) { %>
    <div class="modal fade" id='<%= "myModal" + String.valueOf(i)%>' tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
/                </div>
                <div class="modal-body">
                    <div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <% } %>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/google_script.js"></script>
         
         </div>
         
        <%-- <%! 
        
          public void myFunction(int i, javax.servlet.jsp.JspWriter myOut){
              
             for (int j = 0; j < 10; j++) {
            	 try{
            	 myOut.println("<a href = ' urlsJsp[(i-1)*10+j]'>");
            	 myOut.println("<h2> (i-1)*10+j   ");
            	 myOut.println("titlesJsp[(i-1)*10 + j]</h2>");
            	 myOut.println("<h4> urlsJsp[(i-1)*10+j]</h4>");
            	 myOut.println("</a>");
            	 
            	 
            	 
            	 
            	 }
            	 catch(Exception e) { }
             }
        }
        	%> --%>
        	
        
        
        
        
        
       
       </div>
       <ul class="pagination pagination-lg">
  <li><button onclick="myFunction1()">1</button></li>
  <li><button onclick="myFunction2()">2</button></li>
 <li><button onclick="myFunction3()">3</button></li>
  <li><button onclick="myFunction4()">4</button></li>
  <li><button onclick="myFunction5()">5</button></li>
  <li><button onclick="myFunction6()">6</button></li>
  <li><button onclick="myFunction7()">7</button></li>
</ul>
       
       
       
      <% for (int i = 0; i < recordsJsp; i++) { %>
    <div class="modal fade" id='<%= "myModal" + String.valueOf(i)%>' tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel"><%= titlesJsp1[i]%></h4>
                </div>
                <div class="modal-body">
                    <div>
                        <iframe height = "400px" width="100%" src='<%= urlsJsp1[i]%>' sandbox=""> </iframe>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <%}%> 

    

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/google_script.js"></script>
       
       
       
       
       
       
       
       
       
       
       
   <%--  <% for (int i = 0; i < recordsJsp; i++) { %>
    <div class="modal fade" id='<%= "myModal" + String.valueOf(i)%>' tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel"><%= titlesJsp[i]%></h4>
                </div>
                <div class="modal-body">
                    <div>
                        <iframe height = "400px" width="100%" src='<%= urlsJsp[i]%>' sandbox=""> </iframe>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <% } %> --%>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/google_script.js"></script>
  
  
  
  

 
 
 
 
 
 


</body>
</html>