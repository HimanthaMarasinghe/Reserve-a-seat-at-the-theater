-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 28, 2024 at 10:00 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `theater`
--
CREATE DATABASE IF NOT EXISTS `theater` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `theater`;

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `phone_number` varchar(10) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `email` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`phone_number`, `first_name`, `last_name`, `email`) VALUES
('0771234561', 'John', 'Doe', 'john.doe@example.com'),
('0771234562', 'Jane', 'Smith', 'jane.smith@example.com'),
('0771234563', 'Michael', 'Johnson', 'michael.johnson@example.com'),
('0771234564', 'Emily', 'Davis', 'emily.davis@example.com'),
('0771234565', 'David', 'Brown', 'david.brown@example.com'),
('0771234566', 'Sarah', 'Wilson', 'sarah.wilson@example.com'),
('0771234567', 'James', 'Taylor', 'james.taylor@example.com'),
('0771234568', 'Linda', 'Martinez', 'linda.martinez@example.com'),
('0771234569', 'Robert', 'Anderson', 'robert.anderson@example.com'),
('0771234570', 'Jessica', 'Thomas', 'jessica.thomas@example.com'),
('1234567890', 'Jagath', 'Karunarathna', 'jagath@botmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `movies`
--

CREATE TABLE `movies` (
  `movie_id` varchar(6) NOT NULL,
  `title` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `movies`
--

INSERT INTO `movies` (`movie_id`, `title`) VALUES
('DM4', 'Despicable Me 4'),
('Garf', 'The Garfield Movie'),
('IO2', 'Inside Out 2'),
('KFP4', 'Kung Fu Panda 4'),
('Moa2', 'Moana 2');

-- --------------------------------------------------------

--
-- Table structure for table `tickets`
--

CREATE TABLE `tickets` (
  `ticketID` int(11) NOT NULL,
  `seatId` varchar(3) NOT NULL,
  `show_id` varchar(6) NOT NULL,
  `customer_phone` varchar(10) NOT NULL,
  `date` date NOT NULL,
  `time` enum('Morning','Evening','Night','Special') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tickets`
--

INSERT INTO `tickets` (`ticketID`, `seatId`, `show_id`, `customer_phone`, `date`, `time`) VALUES
(12, 'A09', 'DM4', '0771234561', '2024-11-11', 'Night'),
(13, 'A08', 'DM4', '0771234561', '2024-11-11', 'Night'),
(14, 'A07', 'DM4', '0771234561', '2024-11-11', 'Night'),
(15, 'A06', 'DM4', '0771234561', '2024-11-11', 'Night'),
(16, 'A04', 'DM4', '0771234561', '2024-11-11', 'Night'),
(17, 'A03', 'DM4', '0771234561', '2024-11-11', 'Night'),
(18, 'A02', 'DM4', '0771234561', '2024-11-11', 'Night'),
(19, 'O23', 'DM4', '0771234565', '2024-11-11', 'Night'),
(20, 'N23', 'DM4', '0771234565', '2024-11-11', 'Night'),
(21, 'M23', 'DM4', '0771234565', '2024-11-11', 'Night'),
(22, 'L23', 'DM4', '0771234565', '2024-11-11', 'Night'),
(23, 'K23', 'DM4', '0771234565', '2024-11-11', 'Night'),
(24, 'J23', 'DM4', '0771234565', '2024-11-11', 'Night'),
(25, 'I23', 'DM4', '0771234565', '2024-11-11', 'Night'),
(26, 'H23', 'DM4', '0771234565', '2024-11-11', 'Night'),
(27, 'G23', 'DM4', '0771234565', '2024-11-11', 'Night'),
(28, 'F23', 'DM4', '0771234565', '2024-11-11', 'Night'),
(29, 'D23', 'DM4', '0771234565', '2024-11-11', 'Night'),
(30, 'C23', 'DM4', '0771234565', '2024-11-11', 'Night'),
(31, 'B23', 'DM4', '0771234565', '2024-11-11', 'Night'),
(32, 'A23', 'DM4', '0771234565', '2024-11-11', 'Night'),
(33, 'D09', 'DM4', '0771234570', '2024-11-11', 'Night'),
(34, 'B01', 'DM4', '0771234566', '2024-11-11', 'Night'),
(35, 'D12', 'DM4', '0771234566', '2024-11-11', 'Night'),
(36, 'E30', 'DM4', '0771234566', '2024-11-11', 'Night'),
(37, 'K20', 'DM4', '0771234566', '2024-11-11', 'Night'),
(38, 'J03', 'DM4', '0771234566', '2024-11-11', 'Night'),
(39, 'F30', 'DM4', '0771234566', '2024-11-11', 'Night'),
(41, 'A02', 'IO2', '1234567890', '2024-09-30', 'Morning'),
(42, 'D30', 'IO2', '1234567890', '2024-09-30', 'Morning'),
(43, 'F23', 'IO2', '1234567890', '2024-09-30', 'Morning'),
(44, 'G28', 'IO2', '1234567890', '2024-09-30', 'Morning');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`phone_number`);

--
-- Indexes for table `movies`
--
ALTER TABLE `movies`
  ADD PRIMARY KEY (`movie_id`);

--
-- Indexes for table `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`ticketID`),
  ADD KEY `seatId` (`seatId`),
  ADD KEY `customer_phone` (`customer_phone`),
  ADD KEY `show_id` (`show_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tickets`
--
ALTER TABLE `tickets`
  MODIFY `ticketID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `tickets_ibfk_1` FOREIGN KEY (`customer_phone`) REFERENCES `customers` (`phone_number`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tickets_ibfk_2` FOREIGN KEY (`show_id`) REFERENCES `movies` (`movie_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
