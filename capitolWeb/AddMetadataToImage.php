<?php
error_reporting(E_ALL ^ E_DEPRECATED ^ E_NOTICE ^ E_WARNING);
ini_set("display_errors", TRUE);

if(array_key_exists('imgUrl', $_GET) && array_key_exists('text', $_GET)) {

  $imageUrl = $_GET['imgUrl'];
  $savePath = downloadImage($imageUrl);

  $jpg_image = extendCanvas($savePath, 0, 50);
  $jpg_image = addTextToImage($jpg_image, $_GET['text']);
  renderImage($jpg_image);
}

function extendCanvas($pImagePath, $pWidth, $pHeight) {

  // get the image from file...
  list($width, $height) = getimagesize($pImagePath);
  $img = imagecreatefromjpeg($pImagePath);

  // make the canvas, fill it with $color
  $canvas = imagecreatetruecolor($width+$pWidth,  $height+$pHeight);
  $black = imagecolorallocate($canvas, 0, 0, 0);
  imagefilledrectangle($canvas,0,0,120,120, $color);

  // resample image and place it in center of canvas
  imagecopyresampled($canvas, $img, 0, 0, 0, 0, $width, $height, $width+$pWidth, $height+$pHeight);
  return $canvas;
}

function addTextToImage($pImage, $pText) {
  $image = $pImage;
  $white = imagecolorallocate($image, 255, 255, 255);
  $font_path = './resources/Helvetica.ttf';

  // get the image from file...
  $width = imagesx($image);
  $height = imagesy($image);

  imagettftext($image, 25, 0, 75, $height-40, $white, $font_path, $pText);
  return $image;
}

function renderImage($pImage) {
  header('Content-type: image/jpeg');
  imagejpeg($pImage);
  imagedestroy($pImage);
}

function downloadImage($pImgUrl) {
  $image =$pImgUrl;
  $rename="123";

  $ch = curl_init($image);
  curl_setopt($ch, CURLOPT_HEADER, 0);
  curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
  curl_setopt($ch, CURLOPT_BINARYTRANSFER,1);
  $rawdata=curl_exec ($ch);
  curl_close ($ch);

  $fp = fopen('./images/'.$rename.'.jpg','w');
  fwrite($fp, $rawdata); 
  fclose($fp);

  return './images/'.$rename.'.jpg';
}