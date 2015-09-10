<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Wikipedia Search</title>
<link rel="stylesheet"
	href="WebContent/static/css/bootstrap-theme.min.css">
<style>
body {
	background-image: url("WebContent/static/Wikipedia-logo-v2.svg");
	background-color: #cccccc;
}
</style>
</head>
<body>

	<h1 align="middle">Welcome to Wikipedia Search</h1>
	<div id="results"></div>
	Enter keywords:
	<input id="textbox" type="text" name="q" value="" />
	<input type="submit" id="searchButton" value="Search" />
	<script type="text/javascript">
		document.getElementById("searchButton").onclick = doSearch;

		function doSearch() {
			var v = document.getElementById("textbox").value;
			var url = "http://en.wikipedia.org/w/api.php?";
			url += "action=query";
			url += "&titles=" + encodeURIComponent(v);
			url += "&prop=images";
			url += "&imlimit=20";
			url += "&format=json";
			url += "&callback=parseResponse";

			s = document.createElement('script'); // create script element
			s.src = url;

			document.body.appendChild(s);
			console.log(url);
		}
		function parseResponse(response) {
			$('#search-container').empty();

			var x = Object.keys(response.query.pages);

			for (i = 0; i < x.length; i++) {

				console.log(response);
				output1 = '<p>' + response.query.pages[x[i]].title + '</p>'
				/*            output = '<li><iframe src = \"//www.youtube.com/embed/' + videId +'\"><iframe></li>'; 
				 *//*  $('#search-container').append(output1); */
				/*            $('#results').append(output);
				 */
				getAreaMetaInfo_Wikipedia(x[i]);

			}
		}

		function getAreaMetaInfo_Wikipedia(page_id) {

			$.ajax({
				url : 'http://en.wikipedia.org/w/api.php',
				data : {
					action : 'query',
					pageids : page_id,
					format : 'json'
				},
				dataType : 'jsonp',
				success : function(data) {
					title = data.query.pages[page_id].title.replace(' ', '_');
					$.ajax({
						url : 'http://en.wikipedia.org/w/api.php',
						data : {
							action : 'query',
							prop : 'extracts',
							format : 'json',
							exsentences : 3,
							titles : title,
						},
						dataType : 'jsonp',
						success : function(data) {

							var y = Object.keys(data.query.pages);

							for (i = 0; i < y.length; i++) {
								z = data.query.pages[y[i]].title;
								$('#search-container').append(
										"<a href='http://en.wikipedia.org/wiki/"+z+"' target='wikipedia'>"
												+ data.query.pages[y[i]].title
												+ "</a>");
								output2 = ' <p>'
										+ data.query.pages[y[i]].extract
										+ '</p>'
								/*            output = '<li><iframe src = \"//www.youtube.com/embed/' + videId +'\"><iframe></li>'; 
								
								 */

								$('#search-container').append(output2);

								/*            $('#results').append(output);
								 */

								console.log(output2);
							}

						}
					});
				}
			});
		}
	</script>
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
	<div id="container">
		<h3>Wiki Search Results</h3>
		<u1 id="results"></u1>
	</div>
	<div id="search-container"></div>



</body>
</html>


