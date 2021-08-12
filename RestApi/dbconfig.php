<?php

$DBhost = "localhost";
$DBuser = "root";
$DBpassword ="";
$DBname="upload_db";

$conn = mysqli_connect($DBhost, $DBuser, $DBpassword, $DBname); 

if(!$conn){
	die("Connection failed: " . mysqli_connect_error());
}

?> 