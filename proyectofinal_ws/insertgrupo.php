<?php
	$host='localhost';
	$uname='root';
	$pwd='root';
	$db="webservice_db";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	//$id=$_REQUEST['id'];
	$nombre=$_REQUEST['nom_grupo'];

	$flag['code']=0;

	if($r=mysql_query("insert into grupo (nombre) values('$nombre') ",$con))
	{
		$flag['code']=1;
		
	}

	print(json_encode($flag));
	mysql_close($con);
?>	