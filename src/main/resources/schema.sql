CREATE TABLE `user` (
  `dtype` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `money` decimal(19,2) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `customer_id` bigint(20) NOT NULL,
  `performer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdsq9360hc6vhmcor7d3a6bwfn` (`customer_id`),
  KEY `FK3328ep7qg5js5uee2clvojtpm` (`performer_id`),
  CONSTRAINT `FK3328ep7qg5js5uee2clvojtpm` FOREIGN KEY (`performer_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKdsq9360hc6vhmcor7d3a6bwfn` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;