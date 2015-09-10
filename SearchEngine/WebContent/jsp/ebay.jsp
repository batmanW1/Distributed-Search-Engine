<html>
<head>
<title>eBay Search Results</title>
   <style>
   body  {
       background-image: url("../../static/image/ebay.JPG");
       background-color: #cccccc;
   } 
   </style>
</head>
<body background="eBay.JPG">
<h1 align="middle">Welcome to EBay Search</h1>
<div id="results"></div>
   Enter search keywords: <input id="textbox" type="text" name="q" value=""/>
   <input type="submit" id="searchButton" value="Search"/>
   
   <script type="text/javascript">



   //Parse the response and display search results

function _cb_findItemsByKeywords(root) { //find item by keywords
  var items = root.findItemsByKeywordsResponse[0].searchResult[0].item || [];
  var html = [];
  html.push('<table width="100%" border="0" cellspacing="0" cellpadding="3"><tbody>');
  for (var i = 0; i < items.length; ++i) {
    var item = items[i];
    var title = item.title;
    var pic = item.galleryURL;
    var viewitem = item.viewItemURL;

    if (null != title && null != viewitem) {
      
    	html.push('<tr><td>' + '<img src="' + pic + '" border="0">' + '</td>' +
        '<td><a href="' + viewitem + '" target="_blank">' + title + '</a></td></tr>');
     }
  }
  
   html.push('</tbody></table>');
   document.getElementById("results").innerHTML = html.join("");
}
   
   
   
   
//Create a JavaScript array of the item filters you want to use in your request

var filterarray = [
  {"name":"MaxPrice",
    "value":"25",
    "paramName":"Currency",
    "paramValue":"USD"},
    {"name":"FreeShippingOnly",
    "value":"true",
    "paramName":"",
    "paramValue":""},
    {"name":"ListingType",
    "value":["AuctionWithBIN", "FixedPrice"],
    "paramName":"",
     "paramValue":""},
];


//define a variable for url filter
var urlfilter = "";
function buildURLArray() {
  //iterate through filter array
  for(var i=0; i<filterarray.length; i++) {
    var itemfilter = filterarray[i];
    // Iterate through each parameter in each item filter
    for(var index in itemfilter) {
    // Check to see if the paramter has a value (some don't)
    if (itemfilter[index] !== "") {
      if (itemfilter[index] instanceof Array) {
        for(var r=0; r<itemfilter[index].length; r++) {
          var value = itemfilter[index][r];
          urlfilter += "&itemFilter\(" + i + "\)." + index + "\(" + r + "\)=" + value ;
        }
      }
    else {
      urlfilter += "&itemFilter\(" + i + "\)." + index + "=" + itemfilter[index];
    }
   }
  }
 }
} 
// Execute the function to build the URL filter
buildURLArray(filterarray);
document.getElementById("searchButton").onclick = doSearch;
function doSearch() {
   var v = document.getElementById("textbox").value;
   //build url request
   var url = "http://svcs.ebay.com/services/search/FindingService/v1";
   url += "?OPERATION-NAME=findItemsByKeywords";
   url += "&SERVICE-VERSION=1.0.0";
   url += "&SECURITY-APPNAME=tq3d82220-4827-4b7b-9f5b-5d6045fb618";
   url += "&GLOBAL-ID=EBAY-US";
   url += "&RESPONSE-DATA-FORMAT=JSON";
   url += "&callback=_cb_findItemsByKeywords";
   url += "&REST-PAYLOAD";
   url += "&keywords=" + encodeURIComponent(v);
   url += "&paginationInput.entriesPerPage=6";
   url += urlfilter;
   //Send the request
   s=document.createElement('script'); // create script element
   s.src= url;
   document.body.appendChild(s);
}
</script>
</body>
</html>