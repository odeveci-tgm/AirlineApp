<html>
    <head>
        <title>
            Fluginformationen
        </title>
    </head>
    <body>
        <div>
            <div>
                <div align="center">
                    <h1 align="center">
                        Fluginformationen
                    </h1>
                    
                </div>
				<a href="Index.php">Flugnummer</a>
                <div >
                    <?php
						//config file impotiert
                        include 'config.php';
                        //connect im try block
                        try {
                            //db connect mit PDO $dbtype wird je nach datenbank angepasst 
                            $conn = new PDO("$dbtype:host=$hostname;dbname=$dbname", $username, $password);
                            $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

                            //prepared statement http://php.net/manual/de/pdo.prepared-statements.php 
							//für tipp fehler und falschen eingaben
                            $stmt = $conn -> prepare("SELECT * FROM flights WHERE airline LIKE :airlinecode AND flightnr=:flightnumber");
                            $stmt -> bindParam(":airlinecode", $airlinecode);
                            $stmt -> bindParam(":flightnumber", $flightnumber);

                            //Erste zwei Chars und letzten drei werden getrennt für die DB
							//kA klappt nicht hahahahahah
							//Osman hab es geändert damit zb "VP343" auf "VP" "343" getrennt wird
                            $input = $_GET['fnr'];
                            $airlinecode = substr($input, 0, 2);
                            $flightnumber = substr($input, 2, 5);
                            $stmt -> execute();
                            if($stmt -> rowCount() > 0){
                                $flight = $stmt -> fetch();
                                $start_apc = $flight[3];
                                $dest_apc = $flight[5];
                            ?>

                            <div>
                                <div>
                                    <h2 align="center">
                                        Abflug
                                    </h2>
                                    <!-- Print of Start Country -->
									<div align="center">
                                    <b>Land: </b> 
									<?php
                                    $result = $conn -> query("SELECT country FROM airports WHERE airportcode LIKE '$start_apc'");
                                    $row = $result -> fetch();
                                    $start_cc = $row[0];

                                    $result = $conn -> query("SELECT name FROM countries WHERE code LIKE '$start_cc'");
                                    $row = $result -> fetch();
                                    echo $row[0];
                                    ?><br>

                                    <!-- Print of Start City -->
                                    <b>Stadt: </b>
                                    <?php
                                    $result = $conn -> query("SELECT city FROM airports WHERE airportcode LIKE '$start_apc'");
                                    $row = $result -> fetch();
                                    echo $row[0];
                                    ?><br>

                                    <!-- Print of Start Airport -->
                                    <b>Flughafen: </b>
                                    <?php
                                    $result = $conn -> query("SELECT name FROM airports WHERE airportcode LIKE '$start_apc'");
                                    $row = $result -> fetch();
                                    echo $row[0] . " (" . $start_apc . ")";
                                    ?><br>

                                    <!-- Print of Start Date -->
                                    <b>Datum: </b>
                                    <?php
                                    $result = $conn -> query("SELECT EXTRACT(DAY FROM departure_time), EXTRACT(MONTH FROM departure_time), EXTRACT(YEAR FROM departure_time) FROM flights WHERE airline LIKE '$airlinecode' AND flightnr = $flightnumber");
                                    $row = $result -> fetch();
                                    echo str_pad($row[0], 2, "0", STR_PAD_LEFT) . "." . str_pad($row[1], 2, "0", STR_PAD_LEFT) . "." . $row[2];
                                    ?>
                                    <br>

                                    <!-- Print of Start Time -->
                                    <b>Uhrzeit: </b>
                                    <?php
                                    $result = $conn -> query("SELECT EXTRACT(HOUR FROM departure_time), EXTRACT(MINUTE FROM departure_time), EXTRACT(SECOND FROM departure_time) from flights WHERE airline LIKE '$airlinecode' AND flightnr = $flightnumber");
                                    $row = $result -> fetch();
                                    echo str_pad($row[0], 2, "0", STR_PAD_LEFT) . ":" . str_pad($row[1], 2, "0", STR_PAD_LEFT) . ":" . str_pad($row[2], 2, "0", STR_PAD_LEFT);
                                    ?>
									</div>

                                    
                                </div>
                                <div>
                                    <h2 align="center">
                                        Ankunft
                                    </h2>
									<div align="center">
                                    <!-- Print of Destination Country -->
                                    <b>Land: </b> <?php
                                    $result = $conn -> query("SELECT country FROM airports WHERE airportcode LIKE '$dest_apc'");
                                    $row = $result -> fetch();
                                    $dest_cc = $row[0];

                                    $result = $conn -> query("SELECT name FROM countries WHERE code LIKE '$dest_cc'");
                                    $row = $result -> fetch();
                                    echo $row[0];
                                    ?><br>

                                    <!-- Print of Destination City -->
                                    <b>Stadt: </b>
                                    <?php
                                    $result = $conn -> query("SELECT city FROM airports WHERE airportcode LIKE '$dest_apc'");
                                    $row = $result -> fetch();
                                    echo $row[0];
                                    ?><br>

                                    <!-- Print of Destination Airport -->
                                    <b>Flughafen: </b>
                                    <?php
                                    $result = $conn -> query("SELECT name FROM airports WHERE airportcode LIKE '$dest_apc'");
                                    $row = $result -> fetch();
                                    echo $row[0] . " (" . $dest_apc . ")";
                                    ?><br>

                                    <!-- Print of Destination Date -->
                                    <b>Datum: </b>
                                    <?php
                                    $result = $conn -> query("SELECT EXTRACT(DAY FROM destination_time), EXTRACT(MONTH FROM destination_time), EXTRACT(YEAR FROM destination_time) FROM flights WHERE airline LIKE '$airlinecode' AND flightnr = $flightnumber");
                                    $row = $result -> fetch();
                                    echo str_pad($row[0], 2, "0", STR_PAD_LEFT) . "." . str_pad($row[1], 2, "0", STR_PAD_LEFT) . "." . $row[2];
                                    ?>
                                    <br>

                                    <!-- Print of Destination Time -->
                                    <b>Uhrzeit: </b>
                                    <?php
                                    $result = $conn -> query("SELECT EXTRACT(HOUR FROM destination_time), EXTRACT(MINUTE FROM destination_time), EXTRACT(SECOND FROM destination_time) from flights WHERE airline LIKE '$airlinecode' AND flightnr = $flightnumber");
                                    $row = $result -> fetch();
                                    echo str_pad($row[0], 2, "0", STR_PAD_LEFT) . ":" . str_pad($row[1], 2, "0", STR_PAD_LEFT) . ":" . str_pad($row[2], 2, "0", STR_PAD_LEFT);
                                    ?>
									</div>
                                </div>
                            </div>
                                <div>
                                    <h2 align="center">
                                        Passagierliste
                                    </h2>
                                    <table align="center">
                                        <tr>
                                            <th>
                                                Vorname
                                            </th>
                                            <th>
                                                Nachname
                                            </th>
                                            <th>
                                                Sitznummer
                                            </th>
                                        </tr>
                                        <?php
                                        $result = $conn -> query("SELECT firstname, lastname, rownr, seatposition, id FROM passengers WHERE airline LIKE '$airlinecode' AND flightnr = $flightnumber ORDER BY 3 ASC, 4 ASC");
                                        while($row = $result -> fetch()){
                                            echo "<tr>";
                                            echo "<td>" . $row[0] . "</td>";
                                            echo "<td>" . $row[1] . "</td>";
                                            echo "<td>" . $row[2] . $row[3] . "</td>";
                                            echo "<form action='Kick.php' method='post'>";
                                            echo "<td>";
                                            echo "<input type='text' style='visibility:hidden;' value=$row[4] name='passenger_id'>";
                                            echo "<button type='submit'>Kick</button></td>";
                                            echo "</form>";
                                            echo "</tr>";
                                        }
                                        ?>
                                    </div>
                                    </table>
									<div align="center">
                                    <?php               
                                    echo $result -> rowCount();
                                    echo " von ";
                                    $result = $conn -> query("SELECT maxseats, manufacturer, type FROM planes WHERE id = $flight[6]");
                                    $row = $result -> fetch();
                                    echo $row[0];
                                    echo " Sitzen belegt<br>";
                                    echo "Flugzeug ";
                                    echo $row[1] . " " . $row[2];
                                    $result
                                    ?>
                                    </div>
                                </div>
                            </div>
                            <?php
                            } else {
                                ?>
                                <div>
                                <?php
        
                                echo "Falsche Eingabe";
                                ?>
                            </div>
                            <?php
                            }


                            $conn = null;
                        } catch(Exception $e){
                           
                            echo "Error: " . $e->getMessage();
                        }
                        ?>
                </div>
            </div>
        </div>
    </body>
</html>
