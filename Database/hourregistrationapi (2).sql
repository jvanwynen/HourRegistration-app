-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Gegenereerd op: 01 jun 2020 om 12:00
-- Serverversie: 10.4.10-MariaDB
-- PHP-versie: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hourregistrationapi`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `company`
--

CREATE TABLE `company` (
  `id` int(11) NOT NULL,
  `name` varchar(55) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Gegevens worden geëxporteerd voor tabel `company`
--

INSERT INTO `company` (`id`, `name`, `password`) VALUES
(1, 'no_company_yet', '$2y$10$mrFpt7neUy7aE.FXvO/KnOPsW.MJDiwF3VjRtlETonDm7rMW.z0Wq'),
(2, 'Verbeter je website', '$2y$10$Lzdw3BMFzTFqbNl0V4f8z.5lHwNdO1WqXbd1jDyjetJ9taqvCC3TW'),
(3, 'All Round', '$2y$10$jcSSm5qC6J9JzHh.vRGoN.avPO.0CNBGfJ9Xws/oWQSRmDAwNk.v.'),
(15, 'test12345', '$2y$10$7nWMqqY8Gj6SPxv73Sn7FeeJXlkq7wjMoKFnvSZS0VVSTZ5M0kjNa'),
(16, 'alleswerk', '$2y$10$yOKdXEva6bdHaqSiypjl9OJ8CWe0kQp1bhlCqkBIcTFRhbOQ.n8oe'),
(17, 'alleswerk', '$2y$10$aS1TkyNYvYKhEcTQFKGtrOpLUDiFLMiuXOZ4BzHjTahWbSQIk0nhS');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `hour`
--

CREATE TABLE `hour` (
  `id` int(11) NOT NULL,
  `hours` int(11) DEFAULT NULL,
  `date_added` datetime NOT NULL,
  `project_id` int(11) NOT NULL,
  `user_id` varchar(29) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `migration_versions`
--

CREATE TABLE `migration_versions` (
  `version` varchar(14) COLLATE utf8mb4_unicode_ci NOT NULL,
  `executed_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Gegevens worden geëxporteerd voor tabel `migration_versions`
--

INSERT INTO `migration_versions` (`version`, `executed_at`) VALUES
('20200330071459', '2020-03-30 07:15:29'),
('20200330071659', '2020-03-30 07:18:11'),
('20200330072014', '2020-03-30 07:20:22'),
('20200330072255', '2020-03-30 07:23:04'),
('20200330073301', '2020-03-30 07:33:35'),
('20200330074116', '2020-03-30 07:41:30'),
('20200330074211', '2020-03-30 07:42:20'),
('20200330074619', '2020-03-30 07:46:25'),
('20200330113406', '2020-03-30 11:34:24'),
('20200330113550', '2020-03-30 11:35:58'),
('20200330113701', '2020-03-30 11:37:05'),
('20200330113824', '2020-03-30 11:38:29'),
('20200330114357', '2020-03-30 11:44:03'),
('20200330114819', '2020-03-30 11:48:24'),
('20200409133129', '2020-04-09 13:31:49'),
('20200409133819', '2020-04-09 13:47:15'),
('20200409134023', '2020-04-09 13:47:15'),
('20200409134826', '2020-04-09 13:48:31'),
('20200409134933', '2020-04-09 13:49:39'),
('20200409135754', '2020-04-09 13:58:02');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `project`
--

CREATE TABLE `project` (
  `id` int(11) NOT NULL,
  `name` varchar(55) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tag` varchar(55) COLLATE utf8mb4_unicode_ci NOT NULL,
  `company_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `user`
--

CREATE TABLE `user` (
  `id` varchar(29) COLLATE utf8mb4_unicode_ci NOT NULL,
  `admin` tinyint(1) NOT NULL,
  `firstname` varchar(55) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastname` varchar(55) COLLATE utf8mb4_unicode_ci NOT NULL,
  `company_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Gegevens worden geëxporteerd voor tabel `user`
--

INSERT INTO `user` (`id`, `admin`, `firstname`, `lastname`, `company_id`) VALUES
('103797143833347304268', 1, 'Joost', 'van Wijnen', 16),
('9223372036854775807', 0, 'Leonard', 'Bos', 1);

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `hour`
--
ALTER TABLE `hour`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_701E114E166D1F9C` (`project_id`),
  ADD KEY `IDX_701E114EA76ED395` (`user_id`);

--
-- Indexen voor tabel `migration_versions`
--
ALTER TABLE `migration_versions`
  ADD PRIMARY KEY (`version`);

--
-- Indexen voor tabel `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_2FB3D0EE979B1AD6` (`company_id`);

--
-- Indexen voor tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_8D93D649979B1AD6` (`company_id`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `company`
--
ALTER TABLE `company`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT voor een tabel `hour`
--
ALTER TABLE `hour`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `project`
--
ALTER TABLE `project`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Beperkingen voor geëxporteerde tabellen
--

--
-- Beperkingen voor tabel `hour`
--
ALTER TABLE `hour`
  ADD CONSTRAINT `FK_701E114E166D1F9C` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  ADD CONSTRAINT `FK_701E114EA76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Beperkingen voor tabel `project`
--
ALTER TABLE `project`
  ADD CONSTRAINT `FK_2FB3D0EE979B1AD6` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

--
-- Beperkingen voor tabel `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK_8D93D649979B1AD6` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
