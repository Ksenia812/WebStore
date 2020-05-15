<?php

// Папка для хранения изображений
define('TMP_PATH', '/var/web/zabbix_images/');

$resources = array();

//Графики
$resources[] = array('url' => 'http://адрес_сервера/zabbix/chart2.php?graphid=534&width=1000');
$resources[] = array('url' => 'http://адрес_сервера/zabbix/chart2.php?graphid=524&width=800');
//Карта сети
$resources[] = array('url' => 'http://адрес_сервера/zabbix/map.php?sysmapid=1');

//Получаем изображения
foreach($resources as $k => $res)
{
$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, $res['url']);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);

$file = curl_exec($ch);
if($file) file_put_contents(TMP_PATH . 'img' . $k . '.png', $file);
curl_close($ch);
}
?>