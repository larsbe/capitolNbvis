<?php
error_reporting(E_ALL);
ini_set("display_errors", TRUE);

$config = parse_ini_file("./config.ini"); 
$server= $config["ipAddr"];

if(array_key_exists("ipAddr", $_POST)) {

  $configData = array(
                'ipAddr' => $_POST["ipAddr"]
                );
  write_ini_file($configData, './config.ini', false);

  echo $server." changed to ".$_POST["ipAddr"];

  $server = $_POST["ipAddr"];

}

function write_ini_file($assoc_arr, $path, $has_sections=FALSE) { 
    $content = ""; 
    if ($has_sections) { 
        foreach ($assoc_arr as $key=>$elem) { 
            $content .= "[".$key."]\n"; 
            foreach ($elem as $key2=>$elem2) { 
                if(is_array($elem2)) 
                { 
                    for($i=0;$i<count($elem2);$i++) 
                    { 
                        $content .= $key2."[] = \"".$elem2[$i]."\"\n"; 
                    } 
                } 
                else if($elem2=="") $content .= $key2." = \n"; 
                else $content .= $key2." = \"".$elem2."\"\n"; 
            } 
        } 
    } 
    else { 
        foreach ($assoc_arr as $key=>$elem) { 
            if(is_array($elem)) 
            { 
                for($i=0;$i<count($elem);$i++) 
                { 
                    $content .= $key."[] = \"".$elem[$i]."\"\n"; 
                } 
            } 
            else if($elem=="") $content .= $key." = \n"; 
            else $content .= $key." = \"".$elem."\"\n"; 
        } 
    } 

    if (!$handle = fopen($path, 'w')) { 
        return false; 
    }

    $success = fwrite($handle, $content);
    fclose($handle); 

    return $success; 
}

?>

<html>
<body>
<form action="admin.php" method="post">
  <label for="idAddr">IP-Adresse Camunda:</label>
  <input id="idAddr" name="ipAddr" type="text" value="<?=$server?>" />
  <input type="submit" />
</form>
</body>
</html>