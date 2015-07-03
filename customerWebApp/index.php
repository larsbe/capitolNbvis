<?php

  switch($_GET['page']) {
  case "request":
    $page = "includes/request.php";
    break;
  case "liability":
    $page = "includes/liability.php";
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
    <!--<link rel="icon" href="../../favicon.ico">-->

    <title>BVIS Customer WebApp</title>

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
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="assets/js/bootstrap.min.js"></script>
  </head>

  <body>

    <div class="container">
      <div class="header clearfix">
        <nav>
          <ul class="nav nav-pills pull-right">
            <li role="presentation" class="active"><a href="index.php?page=request">Request Offer</a></li>
            <li role="presentation"><a href="index.php?page=liability">New Insurance Claim</a></li>
          </ul>
        </nav>
        <!--<h3 class="text-muted">BVIS</h3>-->
        <img src="assets/img/bvis_logo.png" border="0" alt="" height="40" />
        <img src="assets/img/capitol_logo.png" border="0" alt="" height="40" />
      </div>

      <?php include($page); ?>

      <footer class="footer">
        <p>&copy; BVIS & Capitol 2015</p>
      </footer>

    </div> <!-- /container -->



  </body>
</html>
