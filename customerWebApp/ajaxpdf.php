<?php
$path = "./contracts/";

$valid_formats = array("pdf", "PDF");
if(isset($_POST) and $_SERVER['REQUEST_METHOD'] == "POST")
{
$name = $_FILES['fileupload']['name'];
$size = $_FILES['fileupload']['size'];
if(strlen($name))
{
list($txt, $ext) = explode(".", $name);
if(in_array($ext,$valid_formats))
{

$tmp = $_FILES['fileupload']['tmp_name'];
if(move_uploaded_file($tmp, $path.$name)) {
	echo $name;
}
else
echo "failed";
}
else
echo "Invalid file format.."; 
}
else
echo "Please select file..!";
exit;
}