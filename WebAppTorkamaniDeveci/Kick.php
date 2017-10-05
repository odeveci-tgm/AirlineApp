<?php

include 'config.php';

$passenger_id = $_POST['passenger_id'];
try {
	//PDO siehe schulübung 
    $conn = new PDO("$dbtype:host=$hostname;dbname=$dbname", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $result = $conn -> query("SELECT airline, flightnr FROM passengers WHERE id=$passenger_id");
    $row = $result -> fetch();
    $fnr = $row[0] . $row[1];
    $conn -> query("DELETE FROM passengers WHERE id=$passenger_id"); //passanger löschen

    header("Location: Fluginformation.php?fnr=$fnr");

} catch(Exception $e){
    echo "Error: " . $e -> getMessage();
}

?>
