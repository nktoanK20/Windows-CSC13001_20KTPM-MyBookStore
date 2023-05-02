-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.24-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP DATABASE `book-store-management-db`;

-- Dumping database structure for book-store-management-db
CREATE DATABASE IF NOT EXISTS `book-store-management-db` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `book-store-management-db`;

-- Dumping structure for table book-store-management-db.account
CREATE TABLE IF NOT EXISTS `account` (
  `id` varchar(10) NOT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `is_active` tinyint(4) DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table book-store-management-db.account: ~5 rows (approximately)
DELETE FROM `account`;
INSERT INTO `account` (`id`, `username`, `password`, `is_active`) VALUES
	('ACC01', 'nguyenkhai', '5f4dcc3b5aa765d61d8327deb882cf99', 1),
	('ACC02', 'nguyenloc', '5f4dcc3b5aa765d61d8327deb882cf99', 1),
	('ACC03', 'admin', '5f4dcc3b5aa765d61d8327deb882cf99', 1),
	('ACC04', 'employee', '5f4dcc3b5aa765d61d8327deb882cf99', 1);

-- Dumping structure for table book-store-management-db.author
CREATE TABLE IF NOT EXISTS `author` (
  `id` varchar(10) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `address` varchar(80) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `is_disabled` tinyint(4) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table book-store-management-db.author: ~5 rows (approximately)
DELETE FROM `author`;
INSERT INTO `author` (`id`, `name`, `address`, `phone`) VALUES
	('TG01', 'Paulo Coelho', ' Rio De Janeiro, Rio de Janeiro, Brasil', '01234568'),
	('TG02', 'Dale Carnegie', 'Forest Hills, Thành phố New York, Tiểu bang New York, Hoa Kỳ', '23654923'),
	('TG03', 'Paul Knoepfler', 'undefine', '02136486'),
	('TG04', 'Stephen Hawking', ' Oxford, Vương Quốc Anh', '23652867'),
	('TG05', 'Michael Talbot', 'Grand Rapids, Michigan, Mỹ', '03265895');

-- Dumping structure for table book-store-management-db.book
CREATE TABLE IF NOT EXISTS `book` (
  `id` varchar(10) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `id_publisher` varchar(10) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `stock` int(11) DEFAULT 0,
  `total_purchase` int(11) DEFAULT 0,
  `release_date` date DEFAULT NULL,
  `is_enabled` tinyint DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `FK_book_publisher` (`id_publisher`),
  CONSTRAINT `FK_book_publisher` FOREIGN KEY (`id_publisher`) REFERENCES `publisher` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table book-store-management-db.book: ~5 rows (approximately)
DELETE FROM `book`;
INSERT INTO `book` (`id`, `name`, `id_publisher`, `stock`, `release_date`, `price`) VALUES
	('SACH01', 'Nhà giả kim', 'NXB02', 100, '2023-05-02', 45000),
	('SACH02', 'Đắc nhân tâm', 'NXB01', 100, '2023-05-02', 90000),
	('SACH03', 'Tế bào gốc', 'NXB03', 100, '2023-05-02', 50000),
	('SACH04', 'Lỗ đen', 'NXB05', 100, '2023-05-02', 85000),
	('SACH05', 'Vũ Trụ toàn ảnh', 'NXB04', 100, '2023-05-02', 100000);

-- Dumping structure for table book-store-management-db.book_author
CREATE TABLE IF NOT EXISTS `book_author` (
  `id_book` varchar(10) NOT NULL,
  `id_author` varchar(10) NOT NULL,
  PRIMARY KEY (`id_book`,`id_author`),
  KEY `FK_book_author-author` (`id_author`),
  CONSTRAINT `FK_book_author-author` FOREIGN KEY (`id_author`) REFERENCES `author` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_book_author-book` FOREIGN KEY (`id_book`) REFERENCES `book` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table book-store-management-db.book_author: ~6 rows (approximately)
DELETE FROM `book_author`;
INSERT INTO `book_author` (`id_book`, `id_author`) VALUES
	('SACH01', 'TG01'),
	('SACH01', 'TG03'),
	('SACH02', 'TG02'),
	('SACH03', 'TG03'),
	('SACH04', 'TG04'),
	('SACH05', 'TG05');

-- Dumping structure for table book-store-management-db.book_category
CREATE TABLE IF NOT EXISTS `book_category` (
  `id_book` varchar(10) NOT NULL,
  `id_category` varchar(10) NOT NULL,
  PRIMARY KEY (`id_book`,`id_category`),
  KEY `FK_book_category-category` (`id_category`),
  CONSTRAINT `FK_book_category-book` FOREIGN KEY (`id_book`) REFERENCES `book` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_book_category-category` FOREIGN KEY (`id_category`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table book-store-management-db.book_category: ~6 rows (approximately)
DELETE FROM `book_category`;
INSERT INTO `book_category` (`id_book`, `id_category`) VALUES
	('SACH01', 'CATG02'),
	('SACH01', 'CATG03'),
	('SACH02', 'CATG04'),
	('SACH03', 'CATG01'),
	('SACH04', 'CATG05'),
	('SACH05', 'CATG06');

-- Dumping structure for table book-store-management-db.category
CREATE TABLE IF NOT EXISTS `category` (
  `id` varchar(10) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `is_enabled` tinyint(4) DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table book-store-management-db.category: ~6 rows (approximately)
DELETE FROM `category`;
INSERT INTO `category` (`id`, `name`, `description`) VALUES
	('CATG01', 'Đời sống', 'Giới thiệu về chủ đề đời sống '),
	('CATG02', 'Khoa học Kỹ thuật', 'Giới thiệu về chủ đề Khoa học, kỹ thuật'),
	('CATG03', 'Y học', 'Giới thiệu về chủ đề y học'),
	('CATG04', 'Thời trang', 'Giới thiệu về chủ đề thời trang'),
	('CATG05', 'Giáo dục', 'Giới thiệu về chủ đề giáo dục'),
	('CATG06', 'Thiếu nhi', 'Giới thiệu về chủ đề thiếu nhi');

-- Dumping structure for table book-store-management-db.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `id` varchar(10) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `official_customer` tinyint(4) DEFAULT 0 COMMENT '1: official customer\n0: anonymus customer',
  `discount` double DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table book-store-management-db.customer: ~5 rows (approximately)
DELETE FROM `customer`;
INSERT INTO `customer` (`id`, `name`, `official_customer`, `discount`) VALUES
	('CUS01', 'Lê Văn B', 1, 0.05),
	('CUS02', 'Anonymous', 0, 0),
	('CUS03', 'Anonymous', 0, 0),
	('CUS04', 'Huỳnh Văn A', 1, 0.05),
	('CUS05', 'Anonymous', 0, 0);

-- Dumping structure for table book-store-management-db.import_sheet
CREATE TABLE IF NOT EXISTS `import_sheet` (
  `id` varchar(10) NOT NULL,
  `create_at` date DEFAULT NULL,
  `id_employee` varchar(10) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `total_cost` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_importSheet-user` (`id_employee`),
  CONSTRAINT `FK_importSheet-user` FOREIGN KEY (`id_employee`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table book-store-management-db.import_sheet: ~2 rows (approximately)
DELETE FROM `import_sheet`;
INSERT INTO `import_sheet` (`id`, `create_at`, `id_employee`, `total_cost`) VALUES
	('IPS01', '2022-11-10', 'USER01', 200000),
	('IPS02', '2020-10-10', 'USER02', 300000),
	('IPS03', '2021-11-11', 'USER04', 400000);

-- Dumping structure for table book-store-management-db.import_sheet_book
CREATE TABLE IF NOT EXISTS `import_sheet_book` (
  `id_importSheet` varchar(10) NOT NULL,
  `id_publisher` varchar(10) NOT NULL,
  `id_book` varchar(10) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `import_price` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_importSheet`,`id_book`),
  KEY `FK_importSheet_book-book` (`id_book`),
  CONSTRAINT `FK_importSheet_book-book` FOREIGN KEY (`id_book`) REFERENCES `book` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_importSheet_book-importSheet` FOREIGN KEY (`id_importSheet`) REFERENCES `import_sheet` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table book-store-management-db.import_sheet_book: ~4 rows (approximately)
DELETE FROM `import_sheet_book`;
INSERT INTO `import_sheet_book` (`id_importSheet`, `id_book`, `quantity`, `import_price`) VALUES
	('IPS01', 'SACH01', 2, 45000),
	('IPS01', 'SACH02', 3, 90000),
	('IPS01', 'SACH03', 3, 50000),
	('IPS02', 'SACH04', 1, 85000);

-- Dumping structure for table book-store-management-db.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `id` varchar(10) NOT NULL,
  `create_at` date DEFAULT NULL,
  `create_by` varchar(10) DEFAULT NULL,
  `bought_by` varchar(10) DEFAULT NULL,
  `sum_cost` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_order_user` (`create_by`),
  KEY `FK_order_customer` (`bought_by`),
  CONSTRAINT `FK_order_customer` FOREIGN KEY (`bought_by`) REFERENCES `customer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_order_user` FOREIGN KEY (`create_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table book-store-management-db.orders: ~5 rows (approximately)
DELETE FROM `orders`;
INSERT INTO `orders` (`id`, `create_at`, `create_by`, `bought_by`, `sum_cost`) VALUES
	('ORD01', '2022-11-20', 'USER01', 'CUS02', 90000),
	('ORD02', '2022-10-10', 'USER02', 'CUS01', 100000),
	('ORD03', '2022-12-04', 'USER01', 'CUS03', 85000),
	('ORD04', '2021-09-07', 'USER04', 'CUS04', 75000),
	('ORD05', '2020-12-16', 'USER02', 'CUS03', 120000);

-- Dumping structure for table book-store-management-db.order_detail
CREATE TABLE IF NOT EXISTS `order_detail` (
  `id_order` varchar(10) NOT NULL,
  `id_book` varchar(10) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_order`,`id_book`),
  KEY `FK_order_detail-book` (`id_book`),
  CONSTRAINT `FK_order_detail-book` FOREIGN KEY (`id_book`) REFERENCES `book` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_order_detail-order` FOREIGN KEY (`id_order`) REFERENCES `orders` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table book-store-management-db.order_detail: ~4 rows (approximately)
DELETE FROM `order_detail`;
INSERT INTO `order_detail` (`id_order`, `id_book`, `quantity`, `price`) VALUES
	('ORD01', 'SACH01', 2, 45000),
	('ORD02', 'SACH01', 1, 45000),
	('ORD02', 'SACH03', 1, 50000),
	('ORD03', 'SACH02', 2, 90000);

-- Dumping structure for table book-store-management-db.promotion
CREATE TABLE IF NOT EXISTS `promotion` (
  `id` varchar(10) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `percent` double DEFAULT NULL,
  `apply_option` varchar(45) DEFAULT NULL,
  `limit_orders` int(11) DEFAULT NULL,
  `is_enabled` tinyint(4) DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table book-store-management-db.promotion: ~3 rows (approximately)
DELETE FROM `promotion`;
INSERT INTO `promotion` (`id`, `name`, `description`, `start_date`, `end_date`, `percent`, `apply_option`, `limit_orders`) VALUES
	('KM01', 'Khuyến mãi 01', 'Mô tả của khuyến mãi 01', '2022-12-01', '2024-12-05', 0.2, 'All', 50);

-- Dumping structure for table book-store-management-db.promotion_book
CREATE TABLE IF NOT EXISTS `promotion_book` (
  `id_promotion` varchar(10) NOT NULL,
  `id_book` varchar(10) NOT NULL,
  PRIMARY KEY (`id_promotion`,`id_book`),
  KEY `FK_promotion_book-book` (`id_book`),
  CONSTRAINT `FK_promotion_book-book` FOREIGN KEY (`id_book`) REFERENCES `book` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_promotion_book-promotion` FOREIGN KEY (`id_promotion`) REFERENCES `promotion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table book-store-management-db.promotion_book: ~5 rows (approximately)
DELETE FROM `promotion_book`;
INSERT INTO `promotion_book` (`id_promotion`, `id_book`) VALUES
	('KM01', 'SACH01');

-- Dumping structure for table book-store-management-db.promotion_order
CREATE TABLE IF NOT EXISTS `promotion_order` (
  `id_promotion` varchar(10) NOT NULL,
  `id_order` varchar(10) NOT NULL,
  PRIMARY KEY (`id_promotion`,`id_order`),
  KEY `FK_promotion_order-order` (`id_order`),
  CONSTRAINT `FK_promotion_order-order` FOREIGN KEY (`id_order`) REFERENCES `orders` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_promotion_order-promotion` FOREIGN KEY (`id_promotion`) REFERENCES `promotion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table book-store-management-db.promotion_order: ~5 rows (approximately)
DELETE FROM `promotion_order`;

-- Dumping structure for table book-store-management-db.publisher
CREATE TABLE IF NOT EXISTS `publisher` (
  `id` varchar(10) NOT NULL,
  `name` varchar(80) DEFAULT NULL,
  `address` varchar(80) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `is_disabled` tinyint(4) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table book-store-management-db.publisher: ~5 rows (approximately)
DELETE FROM `publisher`;
INSERT INTO `publisher` (`id`, `name`, `address`, `phone`) VALUES
	('NXB01', 'Nhà xuất bản Hồng Đức', '65 Tràng Thi, Hàng Bông, Hoàn Kiếm, Hà Nội', '024 3926 0024'),
	('NXB02', 'Nhà xuất bản Kim Đồng', '55 Quang Trung, Hà Nội, Việt Nam', '(024) 39434730'),
	('NXB03', 'Nhà xuất bản Khoa học và Kỹ thuật', '70 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội', '024 3822 0686'),
	('NXB04', 'Nhà xuất bản ĐHQG-HCM', 'Phòng 501, Nhà Điều hành ĐHQG-HCM, phường Linh Trung, quận Thủ Đức, TP Hồ Chí Mi', '028 6681 7058'),
	('NXB05', 'Nhà xuất bản Thanh Niên', '143 Pasteur, Phường 6, Quận 3, Thành phố Hồ Chí Minh', '028 3910 6963');

-- Dumping structure for table book-store-management-db.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` varchar(10) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `id_account` varchar(10) DEFAULT NULL,
  `address` varchar(80) DEFAULT NULL,
  `role` tinyint(4) DEFAULT NULL COMMENT '1: admin\n0: employee',
  PRIMARY KEY (`id`),
  KEY `FK_user_account` (`id_account`),
  CONSTRAINT `FK_user_account` FOREIGN KEY (`id_account`) REFERENCES `account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table book-store-management-db.user: ~5 rows (approximately)
DELETE FROM `user`;
INSERT INTO `user` (`id`, `name`, `id_account`, `address`, `role`) VALUES
	('USER01', 'Nguyễn Hữu Khải', 'ACC01', 'Quận 5, TPHCM', 0),
	('USER02', 'Nguyễn Hữu Lộc', 'ACC02', 'Quận 5, TPHCM', 0),
	('USER03', 'Nguyễn Văn Phú', 'ACC03', 'Quận 5, TPHCM', 1),
	('USER04', 'Nguyễn Khánh Toàn', 'ACC04', 'Quận 5, TPHCM', 0);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
