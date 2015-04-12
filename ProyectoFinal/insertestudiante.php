<?php
	$host='localhost';
	$uname='root';
	$pwd='root';
	$db="webservice_db";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	//$id=$_REQUEST['id'];
	$nom_estudiante=$_REQUEST['nom_estudiante'];
	$matricula=$_REQUEST['matricula'];

	$flag['code']=0;

	if($r=mysql_query("insert into estudiantes (nombre, matricula) values('$nom_estudiante','$matricula') ",$con))
	{
		$flag['code']=1;
		
	}

	print(json_encode($flag));
	mysql_close($con);
?>	