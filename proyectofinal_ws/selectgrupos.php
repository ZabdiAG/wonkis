<?php
	$host='localhost';
	$uname='root';
	$pwd='root';
	$db="webservice_db";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	$query=mysql_query("SELECT * from grupo",$con);
	$rows = array();
	while($r=mysql_fetch_assoc($query)){
		$rows[] = $r;
	}
	$data = $rows;

	//print(json_encode($data));
	//echo json_last_error();
	print(json_encode($data));
	echo json_last_error();
	mysql_close($con);
	die();
?>
