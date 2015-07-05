<?php

  switch($_GET['page']) {
  case "request":
    $page = "includes/request.php";
    break;
  case "request-success":
    $page = "includes/request-success.php";
    break;
  case "liability":
    $page = "includes/liability.php";
    break;
  case "liability-success":
    $page = "includes/liability-success.php";
    break;
  case "fileupload":
    $page = "includes/fileupload.php";
    break;

  default: $page = "includes/request.php";
  }


?>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimal-ui" />
    <meta name="apple-mobile-web-app-status-bar-style" content="yes" />
    <!--<link rel="icon" href="../../favicon.ico">-->

    <title>BVIS Customer WebApp</title>

    <link rel="apple-touch-icon" href="assets/img/touch-icon.png">
    <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/bootstrap-theme.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="assets/css/styles.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="assets/js/jquery-2.1.4.min.js"></script>
    <script src="assets/js/jquery.form.js"></script> 
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="assets/js/bootstrap.min.js"></script>
    <script>
      var camundaserver = 'http://localhost:8080/engine-rest/engine/default/message';
      var imagefolder = 'http://capitol.jonasgerlach.de/customerWebApp/uploads/';
      var contractfolder = 'http://capitol.jonasgerlach.de/customerWebApp/contracts/';
    </script>
  </head>

  <body>

  <!-- Fixed navbar -->
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">
            <img alt="Brand" src="assets/img/bvis_logo.png" height="20">
            <img alt="Brand" src="assets/img/capitol_logo.png" height="25">
          </a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li class="<?php if($_GET['page'] == 'request') echo 'active'; ?>"><a href="index.php?page=request" target="_self">Request Offer</a></li>
            <li class="<?php if($_GET['page'] == 'fileupload') echo 'active'; ?>"><a href="index.php?page=fileupload" target="_self">Contract Upload</a></li>
            <li class="<?php if($_GET['page'] == 'liability') echo 'active'; ?>"><a href="index.php?page=liability" target="_self">New Insurance Claim</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>



    <div class="container">

      <?php include($page); ?>

      <footer class="footer">
        <p>&copy; BVIS & Capitol 2015</p>
      </footer>

    </div> <!-- /container -->


    <script>
    // prevent homescreen app from opening links in external browser
    var a=document.getElementsByTagName("a");
    for(var i=0;i<a.length;i++) {
        if(!a[i].onclick && a[i].getAttribute("target") != "_blank") {
            a[i].onclick=function() {
                    window.location=this.getAttribute("href");
                    return false; 
            }
        }
    }
    </script>
  </body>
</html>
