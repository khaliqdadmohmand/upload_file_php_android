<?php

header("Content-Type: application/json");
header("Acess-Control-Allow-Origin: *");
header("Acess-Control-Allow-Methods: GET");
header("Acess-Control-Allow-Headers: Acess-Control-Allow-Headers,Content-Type,Acess-Control-Allow-Methods, Authorization");

include 'dbconfig.php'; // include database connection file

if ($result = mysqli_query($conn,'SELECT * FROM tbl_image')) {
    while ($row = $result -> fetch_row()) {
    //   printf ("%s (%s)\n", $row[0], $row[1]);
      echo json_encode(array("id" => $row[0] ,"name"=>$row[1], "status" => true));	
    }
    $result -> free_result();
  }
  else{
    $errorMSG = json_encode(array("message" => "no data found!", "status" => false));	
	echo $errorMSG;
  }



?>