<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
<head>
<title>Search Engine</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

<style>
#amazon {
    position:absolute;
     width:200px;
     height:250px;
     z-index:15;
     top:50%;
     left:50%;
     margin:0px 0 0 -450px;
}

<div style='position:absolute;z-index:0;left:0;top:0;width:100%;height:100%'>
         <img src='http://www.bestfreejpg.com/wp-content/uploads/2015/02/yellowstone_desktop_wallpaper.jpg' style='width:100%;height:100%' alt='[]' />
     </div>

body{
background: url(http://www.bestfreejpg.com/wp-content/uploads/2015/02/yellowstone_desktop_wallpaper.jpg)
#fcba79 url() top center no-repeat !important;
}
</style>

</head>
<body background ="http://www.bestfreejpg.com/wp-content/uploads/2015/02/yellowstone_desktop_wallpaper.jpg">
<div class="container">

<h2 class="searching-heading" align="middle">Search Engine</h2>


<form name="searchForm" class="form-search" action="/SearchEngine/startsearch">
<input type="text" class="form-control" id="STextID" name="searchText" />
<P></P><P></P><P></P>
<P align="right">
<!-- <select class="selectpicker" font-size="12" id="searchOption" onchange="change();" name="searchOption">
 --><!-- <option value="0" selected="selected">Search Text</option>
<option value="1">Image</option> -->
<!-- </select>
 --></P>
<P></P><P></P>
<P align="middle">
<button type="submit" class="btn" align="middle" ID="SButtonID" ><font color="black"><B> Search </B></font></button>
</P>
</form>

<table><tr>
<td><button type="button" ID="Wikipedia"> <a href="wikipedia.jsp" target="_blank"><font color="black">Wikipedia</font> </a></button></td>
<td><button type="button" ID="Youtube"> <a href="youtube.jsp" target="_blank"><font color="black">Youtube</font> </a></button></td>
<td><button type="button" ID="Ebay"> <a href="ebay.jsp" target="_blank"><font color="black">Ebay</font> </a></button></td>
</table>
</div>









<div id = "amazon">

<script charset="utf-8" type="text/javascript">
amzn_assoc_ad_type = "responsive_search_widget";
amzn_assoc_tracking_id = "searcheng0676-20";
amzn_assoc_link_id = "HSU6MR4ANYBBXMOI";
amzn_assoc_marketplace = "amazon";
amzn_assoc_region = "US";
amzn_assoc_placement = "";
amzn_assoc_search_type = "search_widget";
amzn_assoc_width = 160;
amzn_assoc_height = 600;
amzn_assoc_default_search_category = "";
amzn_assoc_default_search_key = "";
amzn_assoc_theme = "light";
amzn_assoc_bg_color = "FFFFFF";
</script>
<div>


<script src="//z-na.amazon-adsystem.com/widgets/q?ServiceVersion=20070822&Operation=GetScript&ID=OneJS&WS=1&MarketPlace=US"></script>

</body>
</html> 



