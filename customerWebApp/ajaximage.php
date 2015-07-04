<?php
$path = "./uploads/";

$valid_formats = array("jpg", "png", "gif", "bmp","jpeg");
if(isset($_POST) and $_SERVER['REQUEST_METHOD'] == "POST")
{
$name = $_FILES['fileupload']['name'];
$size = $_FILES['fileupload']['size'];
if(strlen($name))
{
list($txt, $ext) = explode(".", $name);
if(in_array($ext,$valid_formats))
{

$actual_image_name = 'upload'.time();
$tmp = $_FILES['fileupload']['tmp_name'];
if(move_uploaded_file($tmp, $path.$actual_image_name.'.'.$ext)) {
	echo $actual_image_name.'.'.$ext;
}
else
echo "failed";
}
else
echo "Invalid file format.."; 
}
else
echo "Please select image..!";
exit;
}