-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Generation Time: Nov 29, 2023 at 08:02 PM
-- Server version: 8.0.18
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `thetechcompanydb`
--

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
CREATE TABLE IF NOT EXISTS `department` (
  `NumberOfDepartments` int(100) NOT NULL AUTO_INCREMENT,
  `DepartmentName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`NumberOfDepartments`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `department`
--

INSERT INTO `department` (`NumberOfDepartments`, `DepartmentName`) VALUES
(1, 'IT'),
(2, 'Design'),
(3, 'Business Development'),
(4, 'Marketing'),
(5, 'Finance'),
(6, 'Administration'),
(7, 'Operation'),
(8, 'Production');

-- --------------------------------------------------------

--
-- Table structure for table `designation`
--

DROP TABLE IF EXISTS `designation`;
CREATE TABLE IF NOT EXISTS `designation` (
  `NumberOfDesignation` int(100) NOT NULL AUTO_INCREMENT,
  `DesignationName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`NumberOfDesignation`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `designation`
--

INSERT INTO `designation` (`NumberOfDesignation`, `DesignationName`) VALUES
(1, 'Software Engineer'),
(2, 'System Analyst'),
(3, 'Project Lead'),
(4, 'Program Manager'),
(5, 'Operational Manager');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `EPFNumber` varchar(10) NOT NULL,
  `EPFName` varchar(50) DEFAULT NULL,
  `EPFAddress` varchar(50) DEFAULT NULL,
  `EPFTelephoneNumber` varchar(10) DEFAULT NULL,
  `EMail` varchar(50) DEFAULT NULL,
  `Department` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Designation` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`EPFNumber`),
  UNIQUE KEY `EPFTelephoneNumber` (`EPFTelephoneNumber`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`EPFNumber`, `EPFName`, `EPFAddress`, `EPFTelephoneNumber`, `EMail`, `Department`, `Designation`) VALUES
('20000', 'Aysha', 'No.10, Duplication Road, Colombo 10', '0759911772', 'aysha@gmail.com', 'IT', 'Software Engineer'),
('20001', 'Maya', 'No.08, Oak Street, Colombo 7', '0778829992', 'maya@gmail.com', 'Design', 'Project Lead'),
('20002', 'Kalai Vaani', 'No.45, Robert Road, Colombo 2', '0766665180', 'kalaivaani@gmail.com', 'Business Development', 'System Analyst'),
('20003', 'Kalai Vaani', 'No.5, Senanaayaka Road, Colombo 3', '0765623234', 'kalaivaani@gmail.com', 'Marketing', 'Program Manager'),
('20004', 'Raj Kamal', 'No.98, Closen Street, Colombo 9', '0771211134', 'rajkamal@gmail.com', 'Operation', 'Operational Manager');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `UserID` varchar(10) NOT NULL,
  `FirstName` varchar(40) DEFAULT NULL,
  `LastName` varchar(40) DEFAULT NULL,
  `UserName` varchar(40) DEFAULT NULL,
  `Password` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Password` (`Password`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserID`, `FirstName`, `LastName`, `UserName`, `Password`) VALUES
('MAN001', 'Mayooran', 'Devaraj', 'Manager@TMDstart', '5643219867'),
('ASS002', 'Dinesh', 'Karthik', 'Assist@TDKAssis', '5454545454'),
('ASS003', 'Ram', 'Kumar', 'Assist@TRMAssis', '1234567890');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
