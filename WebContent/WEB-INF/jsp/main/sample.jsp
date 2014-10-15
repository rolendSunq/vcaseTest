<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SKT - OVP Sample Web</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- @todo: fill with your company info or remove -->
<meta name="description" content="">
<meta name="author" content="Themelize.me">

<!-- Bootstrap CSS -->
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/responsive.css" rel="stylesheet">

<!-- Flexslider -->
<link href="css/flexslider.css" rel="stylesheet">

<!-- Theme style -->
<link href="css/theme-style.css" rel="stylesheet">

<!--Your custom colour override-->
<link href="css/colour-red.css" id="colour-scheme" rel="stylesheet">

<!-- Your custom override -->
<link href="css/custom-style.css" rel="stylesheet">

<link rel="shortcut icon" href="/image/logo.png">
<link rel="apple-touch-icon-precomposed" href="/image/logo.png">
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,300|Rambla|Calligraffitti' rel='stylesheet' type='text/css'>

<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="http://sm.vcase.myskcdn.com:80/solutions/ovfp/binary/ko/swfobject.js"></script>

<script type="text/javascript">
var isMobile = {
	    Android: function() {
	        return navigator.userAgent.match(/Android/i);
	    },
	    BlackBerry: function() {
	        return navigator.userAgent.match(/BlackBerry/i);
	    },
	    iOS: function() {
	        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
	    },
	    Opera: function() {
	        return navigator.userAgent.match(/Opera Mini/i);
	    },
	    Windows: function() {
	        return navigator.userAgent.match(/IEMobile/i);
	    },
	    any: function() {
	        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
	    }
	};

$(document).ready(function() {
	$(".content_item").click(function()
	{
		
		
		
		var content_id = $(this).attr("data-content-id");
		var content_thumb = $(this).attr("data-content-thumb");
		var content_title= $(this).attr("data-content-title");
		$.getJSON("/content/player/" + content_id, null,   
			      function(data) {   
			var streaming_url = data.streaming_url;
			
			// 모바일인경우
			if(isMobile.any())
				{
				window.open(streaming_url);
				}
			else
				{
				if(streaming_url) {
					streaming_url = streaming_url.replace(/&/g, "&amp;amp;");
				}
			$('#player_modal').modal('show'); 
			$("#modal-content").empty();
			$("#modal-content").append('<object data="http://vcase.myskcdn.com/static/ovp/ovp.swf" name="ovp" id="ovp" type="application/x-shockwave-flash" align="middle" width="640" height="480" >'+
					'<param value="high" name="quality">'+ 
					'<param value="#000000" name="bgcolor">'+
					'<param value="always" name="allowscriptaccess">'+
					'<param value="true" name="allowfullscreen">'+
					'<param value="title='+content_title+'&amp;mediaUrl='+ streaming_url+'&amp;thumbUrl='+content_thumb+'&amp;pid='+${player_id}+'&amp;apiUrl=http://api.vcase.myskcdn.com&amp;autoPlay=false" name="flashvars">'+
				'</object>');
				}
		});  
	});
});
</script>


</head>
<body class="page page-index">

<!-- Modal -->
<div class="modal fade bs-example-modal-lg" id="player_modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content" id="modal-content">
    	
	<!-- 플레이어 위치 -->
    </div>
  </div>
</div>


<div id="navigation" class="wrapper">
  <div class="navbar  navbar-static-top"> 
    
    
    <!--Header & Branding region-->
    <div class="header">
      <div class="header-inner container">
        <div class="row-fluid">
          <div class="span8"> 
            <!--branding/logo--> 
            <a class="brand" href="#" title="Home">
            <h1><img src="/image/logo.png" alt="logo"/></h1>
            </a>
            <div class="slogan">VCase PC,Mobile Web Sample.</div>
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="navbar-inner"> 
        
        <!--mobile collapse menu button--> 
        <a class="btn btn-navbar pull-left" data-toggle="collapse" data-target=".nav-collapse"> 
		<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </a> 
              
		<ul class="nav user-menu pull-right">
		<!--right menu-->
			<div class="btn-group" data-toggle="buttons-radio">
			
			  <button id="lang_kr" class="btn">KOREAN</button>
			</div>
		</ul>

        <!--everything within this div is collapsed on mobile-->
        <div class="nav-collapse collapse"> 
          <!--main navigation-->
          <ul class="nav" id="main-menu">
            <li class="home-link"><a href="#"><i class="icon-home hidden-phone"></i><span class="visible-phone">Home</span></a></li>
            <li><a href="#" class="menu-item">최신 동영상</a></li>
			<li><a href="#" class="menu-item">인기 동영상</a></li>
			<li><a href="#" class="menu-item">관심 동영상</a></li>
			<li><a href="#" class="menu-item">동영상 검색</a></li>
          </ul>

        </div>
        <!--/.nav-collapse --> 
      </div>
    </div>
  </div>
</div>

<div id="content">
  <div class="container"> 
    <!-- OVERVIEW -->
    <div class="block features">
      <h2 class="title-divider"><span>최신 동영상</span></h2>
      
      <ul class="thumbnails">
      <c:forEach var="object" items="${list}" varStatus="status">
        <li id="content_item"class="span3 content_item" data-content-id="${object.content_id}" data-content-thumb = "${object.thumb_url}" data-content-title=">${object.title}"style="height:380px;"><img src="${object.thumb_url}" />
          <h3 class="title">${object.title}</h3>
          <p>재생시간 : ${object.duration}
          <br>파일크기 : ${object.file_size}
          <br>등록일시 : ${object.mod_date}</p>
        </li>
      </c:forEach>
      </ul>
    </div>


</div>

<!-- FOOTER -->
<footer id="footer">
  <div class="container">
    <div class="row-fluid">
      <div class="subfooter">
        <div class="span6">
          Copyright 2014 &copy; SKTelecom</p>
        </div>
      </div>
    </div>
  </div>
</footer>

<!-- Modal Window -->
<div id="TermsModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="TermsModal" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3>Terms</h3>
	</div>
	<div class="modal-body">
		<p>Terms…</p>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
	</div>
</div>

<div id="PrivacyModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="PrivacyModal" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3>Privacy</h3>
	</div>
	<div class="modal-body">
		<p>Privacy...</p>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
	</div>
</div>

<!-- Modal Window End -->

<!--Scripts --> 
<!-- <script src="js/jquery.js"></script>  -->

<script src="js/bootstrap-transition.js"></script> 
<script src="js/bootstrap-alert.js"></script> 
<script src="js/bootstrap-affix.js"></script> 
<script src="js/bootstrap-modal.js"></script> 
<script src="js/bootstrap-dropdown.js"></script> 
<script src="js/bootstrap-scrollspy.js"></script> 
<script src="js/bootstrap-tab.js"></script> 
<script src="js/bootstrap-tooltip.js"></script> 
<script src="js/bootstrap-popover.js"></script> 
<script src="js/bootstrap-button.js"></script> 
<script src="js/bootstrap-collapse.js"></script> 
<script src="js/bootstrap-carousel.js"></script> 
<script src="js/bootstrap-typeahead.js"></script> 

<!--Non-Bootstrap JS--> 
<script src="js/jquery.quicksand.js"></script> 
<script src="js/jquery.flexslider-min.js"></script> 

<!--Custom scripts mainly used to trigger libraries --> 
<script src="js/script.js"></script>

<script type="text/javascript" src="js/jquery.validate.js"></script>

</body>
</html>

