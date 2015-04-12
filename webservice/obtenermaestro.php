<?php
$servername = "localhost";
$username = "root";
$password = "root";

// Create connection
$conn = new mysqli($servername, $username, $password);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}else{
echo "Connected successfully";
}

$sql = "SELECT nombre FROM users";

$result = $conn->query($sql);

//print_r($result);
var_dump($result);
if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo "id: " . $row["id"]. " - Name: " . $row["nombre"]. " " . $row["username"]. "<br>";
    }
} else {
    echo "0 results";
}
$conn->close();

?>