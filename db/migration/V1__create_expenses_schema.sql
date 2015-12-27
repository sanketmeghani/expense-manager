CREATE TABLE IF NOT EXISTS `expense` (

`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
`date` date,
`amount` decimal(10,2),
`reason` varchar(250)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;  