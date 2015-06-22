-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 07, 2014 at 04:26 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `inscmtidb`
--

-- --------------------------------------------------------

--
-- Table structure for table `comentario`
--

CREATE TABLE IF NOT EXISTS `comentario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_postulacion` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `texto` text NOT NULL,
  `fecha` date NOT NULL,
  `es_sugerencia` binary(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `comentario_postulacion_idx` (`id_postulacion`),
  KEY `comentario_usuario_idx` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `datos_empresa`
--

CREATE TABLE IF NOT EXISTS `datos_empresa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_postulante` int(11) NOT NULL,
  `nombre_empresa` varchar(45) NOT NULL,
  `cargo_empresa` varchar(45) NOT NULL,
  `direccion_empresa` varchar(45) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `empresa_postulante_idx` (`id_postulante`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `documento`
--

CREATE TABLE IF NOT EXISTS `documento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `direccion` varchar(45) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `comentario` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `doc_extra`
--

CREATE TABLE IF NOT EXISTS `doc_extra` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_postulacion` int(11) NOT NULL,
  `id_documento` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `doc_extra_postulacion_idx` (`id_postulacion`),
  KEY `doc_extra_documento_idx` (`id_documento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `estado`
--

CREATE TABLE IF NOT EXISTS `estado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `estado`
--

INSERT INTO `estado` (`id`, `nombre`) VALUES
(8, 'ELIMINADA'),
(3, 'EN CONSIDERACIÓN POR EL COORDINADOR'),
(6, 'EN ESPERA DE NOTIFICACIÓN'),
(4, 'EN EVALUACIÓN POR LA COMISIÓN'),
(5, 'EN PROCESO DE DECISIÓN'),
(1, 'EN REVISIÓN'),
(2, 'EN VALIDACIÓN'),
(7, 'RESUELTA');

-- --------------------------------------------------------

--
-- Table structure for table `financiamiento`
--

CREATE TABLE IF NOT EXISTS `financiamiento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` int(11) NOT NULL,
  `detalle` text,
  PRIMARY KEY (`id`),
  KEY `financiamiento_idx` (`tipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `genero`
--

CREATE TABLE IF NOT EXISTS `genero` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `genero`
--

INSERT INTO `genero` (`id`, `nombre`) VALUES
(2, 'FEMENINO'),
(1, 'MASCULINO');

-- --------------------------------------------------------

--
-- Table structure for table `grado_academico`
--

CREATE TABLE IF NOT EXISTS `grado_academico` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_postulante` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `institucion` varchar(255) NOT NULL,
  `ano_obtencion` int(11) NOT NULL,
  `id_pais` int(11) NOT NULL,
  `id_certificado_notas` int(11) NOT NULL,
  `id_certificado_titulo` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `grado_postulante_idx` (`id_postulante`),
  KEY `grado_pais_idx` (`id_pais`),
  KEY `grado_notas_idx` (`id_certificado_notas`),
  KEY `grado_titulo_idx` (`id_certificado_titulo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `historial`
--

CREATE TABLE IF NOT EXISTS `historial` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_postulacion` int(11) NOT NULL,
  `accion` text NOT NULL,
  `fecha` date NOT NULL,
  `comentario` text NOT NULL,
  `id_usuario_rol` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `historial_postulacion_idx` (`id_postulacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `identificacion`
--

CREATE TABLE IF NOT EXISTS `identificacion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `identificacion` varchar(45) NOT NULL,
  `pais` int(11) DEFAULT NULL,
  `esRut` binary(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `identificacion_pais_idx` (`pais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `log`
--

CREATE TABLE IF NOT EXISTS `log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `accion` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `pais`
--

CREATE TABLE IF NOT EXISTS `pais` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=895 ;

--
-- Dumping data for table `pais`
--

INSERT INTO `pais` (`id`, `nombre`) VALUES
(4, 'Afganistán'),
(8, 'Albania'),
(276, 'Alemania'),
(20, 'Andorra'),
(24, 'Angola'),
(660, 'Anguila'),
(10, 'Antártida'),
(28, 'Antigua y Barbuda'),
(530, 'Antillas Holandesas'),
(682, 'Arabia Saudí'),
(12, 'Argelia'),
(32, 'Argentina'),
(51, 'Armenia'),
(533, 'Aruba'),
(36, 'Australia'),
(40, 'Austria'),
(31, 'Azerbaiyán'),
(44, 'Bahamas'),
(48, 'Bahréin'),
(50, 'Bangladesh'),
(52, 'Barbados'),
(56, 'Bélgica'),
(84, 'Belice'),
(204, 'Benín'),
(60, 'Bermudas'),
(64, 'Bhután'),
(112, 'Bielorrusia'),
(68, 'Bolivia'),
(70, 'Bosnia y Herzegovina'),
(72, 'Botsuana'),
(76, 'Brasil'),
(96, 'Brunéi'),
(100, 'Bulgaria'),
(854, 'Burkina Faso'),
(108, 'Burundi'),
(132, 'Cabo Verde'),
(116, 'Camboya'),
(120, 'Camerún'),
(124, 'Canadá'),
(148, 'Chad'),
(152, 'Chile'),
(156, 'China'),
(196, 'Chipre'),
(336, 'Ciudad del Vaticano'),
(170, 'Colombia'),
(174, 'Comoras'),
(178, 'Congo'),
(408, 'Corea del Norte'),
(410, 'Corea del Sur'),
(384, 'Costa de Marfil'),
(188, 'Costa Rica'),
(191, 'Croacia'),
(192, 'Cuba'),
(208, 'Dinamarca'),
(212, 'Dominica'),
(218, 'Ecuador'),
(818, 'Egipto'),
(222, 'El Salvador'),
(784, 'Emiratos Árabes Unidos'),
(232, 'Eritrea'),
(703, 'Eslovaquia'),
(705, 'Eslovenia'),
(724, 'España'),
(840, 'Estados Unidos'),
(233, 'Estonia'),
(231, 'Etiopía'),
(608, 'Filipinas'),
(246, 'Finlandia'),
(242, 'Fiyi'),
(250, 'Francia'),
(266, 'Gabón'),
(270, 'Gambia'),
(268, 'Georgia'),
(288, 'Ghana'),
(292, 'Gibraltar'),
(308, 'Granada'),
(300, 'Grecia'),
(304, 'Groenlandia'),
(312, 'Guadalupe'),
(316, 'Guam'),
(320, 'Guatemala'),
(254, 'Guayana Francesa'),
(324, 'Guinea'),
(226, 'Guinea Ecuatorial'),
(624, 'Guinea-Bissau'),
(328, 'Guyana'),
(332, 'Haití'),
(340, 'Honduras'),
(344, 'Hong Kong'),
(348, 'Hungría'),
(356, 'India'),
(360, 'Indonesia'),
(364, 'Irán'),
(368, 'Iraq'),
(372, 'Irlanda'),
(74, 'Isla Bouvet'),
(162, 'Isla de Navidad'),
(574, 'Isla Norfolk'),
(352, 'Islandia'),
(136, 'Islas Caimán'),
(166, 'Islas Cocos'),
(184, 'Islas Cook'),
(234, 'Islas Feroe'),
(239, 'Islas Georgias del Sur y Sandwich del Su'),
(248, 'Islas Gland'),
(334, 'Islas Heard y McDonald'),
(238, 'Islas Malvinas'),
(580, 'Islas Marianas del Norte'),
(584, 'Islas Marshall'),
(612, 'Islas Pitcairn'),
(90, 'Islas Salomón'),
(796, 'Islas Turcas y Caicos'),
(581, 'Islas Ultramarinas de Estados Unidos'),
(92, 'Islas Virgenes Británicas'),
(850, 'Islas Virgenes de los Estados Unidos'),
(376, 'Israel'),
(380, 'Italia'),
(388, 'Jamaica'),
(392, 'Japón'),
(400, 'Jordania'),
(398, 'Kazajstán'),
(404, 'Kenia'),
(417, 'Kirguistán'),
(296, 'Kiribati'),
(414, 'Kuwait'),
(418, 'Laos'),
(426, 'Lesotho'),
(428, 'Letonia'),
(422, 'Líbano'),
(430, 'Liberia'),
(434, 'Libia'),
(438, 'Liechtenstein'),
(440, 'Lituania'),
(442, 'Luxemburgo'),
(446, 'Macao'),
(807, 'Macedonia'),
(450, 'Madagascar'),
(458, 'Malasia'),
(454, 'Malaui'),
(462, 'Maldivas'),
(466, 'Malí'),
(470, 'Malta'),
(504, 'Marruecos'),
(474, 'Martinica'),
(480, 'Mauricio'),
(478, 'Mauritania'),
(175, 'Mayotte'),
(484, 'México'),
(583, 'Micronesia'),
(498, 'Moldavia'),
(492, 'Mónaco'),
(496, 'Mongolia'),
(499, 'Montenegro'),
(500, 'Montserrat'),
(508, 'Mozambique'),
(104, 'Myanmar'),
(516, 'Namibia'),
(520, 'Nauru'),
(524, 'Nepal'),
(558, 'Nicaragua'),
(562, 'Níger'),
(566, 'Nigeria'),
(570, 'Niue'),
(578, 'Noruega'),
(540, 'Nueva Caledonia'),
(554, 'Nueva Zelanda'),
(512, 'Omán'),
(528, 'Países Bajos'),
(586, 'Pakistán'),
(585, 'Palaos'),
(275, 'Palestina'),
(591, 'Panamá'),
(598, 'Papúa Nueva Guinea'),
(600, 'Paraguay'),
(604, 'Perú'),
(258, 'Polinesia Francesa'),
(616, 'Polonia'),
(620, 'Portugal'),
(630, 'Puerto Rico'),
(634, 'Qatar'),
(826, 'Reino Unido'),
(140, 'República Centroafricana'),
(203, 'República Checa'),
(180, 'República Democrática del Congo'),
(214, 'República Dominicana'),
(638, 'Reunión'),
(646, 'Ruanda'),
(642, 'Rumania'),
(643, 'Rusia'),
(732, 'Sahara Occidental'),
(882, 'Samoa'),
(16, 'Samoa Americana'),
(659, 'San Cristóbal y Nieves'),
(674, 'San Marino'),
(666, 'San Pedro y Miquelón'),
(670, 'San Vicente y las Granadinas'),
(654, 'Santa Helena'),
(662, 'Santa Lucía'),
(678, 'Santo Tomé y Príncipe'),
(686, 'Senegal'),
(688, 'Serbia'),
(690, 'Seychelles'),
(694, 'Sierra Leona'),
(702, 'Singapur'),
(760, 'Siria'),
(706, 'Somalia'),
(144, 'Sri Lanka'),
(748, 'Suazilandia'),
(710, 'Sudáfrica'),
(736, 'Sudán'),
(752, 'Suecia'),
(756, 'Suiza'),
(740, 'Surinam'),
(744, 'Svalbard y Jan Mayen'),
(764, 'Tailandia'),
(158, 'Taiwán'),
(834, 'Tanzania'),
(762, 'Tayikistán'),
(86, 'Territorio Británico del Océano Índico'),
(260, 'Territorios Australes Franceses'),
(626, 'Timor Oriental'),
(768, 'Togo'),
(772, 'Tokelau'),
(776, 'Tonga'),
(780, 'Trinidad y Tobago'),
(788, 'Túnez'),
(795, 'Turkmenistán'),
(792, 'Turquía'),
(798, 'Tuvalu'),
(804, 'Ucrania'),
(800, 'Uganda'),
(858, 'Uruguay'),
(860, 'Uzbekistán'),
(548, 'Vanuatu'),
(862, 'Venezuela'),
(704, 'Vietnam'),
(876, 'Wallis y Futuna'),
(887, 'Yemen'),
(262, 'Yibuti'),
(894, 'Zambia'),
(716, 'Zimbabue');

-- --------------------------------------------------------

--
-- Table structure for table `permiso`
--

CREATE TABLE IF NOT EXISTS `permiso` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permiso` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `permiso_UNIQUE` (`permiso`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `permiso`
--

INSERT INTO `permiso` (`id`, `permiso`) VALUES
(9, 'ADMIN_USERS'),
(6, 'DECIDIR'),
(7, 'NOTIFICAR'),
(4, 'RECHAZAR_DOC'),
(1, 'REVISAR'),
(5, 'SET_DEADLINE'),
(8, 'SUBIR_DOC'),
(2, 'VALIDAR'),
(3, 'VOTAR');

-- --------------------------------------------------------

--
-- Table structure for table `postulacion`
--

CREATE TABLE IF NOT EXISTS `postulacion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `track_hash` text,
  `id_postulante` int(11) NOT NULL,
  `id_financiamiento` int(11) NOT NULL,
  `id_carta1` int(11) NOT NULL,
  `id_carta2` int(11) NOT NULL,
  `id_cv` int(11) NOT NULL,
  `id_carta_presentacion` int(11) NOT NULL,
  `id_estado` int(11) NOT NULL,
  `deadline` date DEFAULT NULL,
  `fecha_ingreso` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `postulacion_postulante_idx` (`id_postulante`),
  KEY `postulacion_financiamiento_idx` (`id_financiamiento`),
  KEY `postulacion_carta1_idx` (`id_carta1`),
  KEY `postulacion_carta2_idx` (`id_carta2`),
  KEY `postulacion_cv_idx` (`id_cv`),
  KEY `postulacion_presentacion_idx` (`id_carta_presentacion`),
  KEY `postulacion_estado_idx` (`id_estado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `postulante`
--

CREATE TABLE IF NOT EXISTS `postulante` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `primer_nombre` varchar(45) NOT NULL,
  `segundo_nombre` varchar(45) DEFAULT NULL,
  `apellido_paterno` varchar(45) NOT NULL,
  `apellido_materno` varchar(45) DEFAULT NULL,
  `id_identificacion` int(11) NOT NULL,
  `nacionalidad` int(11) NOT NULL,
  `id_genero` int(11) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `celular` varchar(45) DEFAULT NULL,
  `pais_residencia` int(11) NOT NULL,
  `email` varchar(45) NOT NULL,
  `direccion` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_identificacion_UNIQUE` (`id_identificacion`),
  KEY `postulante_nacionalidad_idx` (`nacionalidad`),
  KEY `postulante_genero_idx` (`id_genero`),
  KEY `postulante_residencia_idx` (`pais_residencia`),
  KEY `postulante_identificacion_idx` (`id_identificacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `resolucion`
--

CREATE TABLE IF NOT EXISTS `resolucion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_postulacion` int(11) NOT NULL,
  `comentario` text,
  `id_tipo_resolucion` int(11) NOT NULL,
  `fecha` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `resolucion_postulacion_idx` (`id_postulacion`),
  KEY `resolucion_tipo_resolucion_idx` (`id_tipo_resolucion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `rol`
--

CREATE TABLE IF NOT EXISTS `rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `rol`
--

INSERT INTO `rol` (`id`, `nombre`) VALUES
(1, 'Administrador'),
(4, 'Asistente'),
(3, 'Comisionado'),
(2, 'Coordinador'),
(5, 'Jefe del PEC');

-- --------------------------------------------------------

--
-- Table structure for table `rol_permiso`
--

CREATE TABLE IF NOT EXISTS `rol_permiso` (
  `id_permiso` int(11) NOT NULL,
  `id_rol` int(11) NOT NULL,
  PRIMARY KEY (`id_permiso`,`id_rol`),
  KEY `permiso_idx` (`id_permiso`),
  KEY `rol_idx` (`id_rol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `rol_permiso`
--

INSERT INTO `rol_permiso` (`id_permiso`, `id_rol`) VALUES
(1, 1),
(1, 4),
(1, 5),
(2, 1),
(2, 3),
(2, 5),
(3, 1),
(3, 2),
(3, 3),
(4, 1),
(4, 4),
(4, 5),
(5, 1),
(5, 2),
(6, 1),
(6, 2),
(7, 1),
(7, 4),
(8, 1),
(8, 4),
(8, 5),
(9, 1),
(9, 2);

-- --------------------------------------------------------

--
-- Table structure for table `tipo_financiamiento`
--

CREATE TABLE IF NOT EXISTS `tipo_financiamiento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tipo_UNIQUE` (`tipo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `tipo_financiamiento`
--

INSERT INTO `tipo_financiamiento` (`id`, `tipo`) VALUES
(2, 'BECA'),
(3, 'EMPRESA'),
(1, 'PARTICULAR');

-- --------------------------------------------------------

--
-- Table structure for table `tipo_resolucion`
--

CREATE TABLE IF NOT EXISTS `tipo_resolucion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `tipo_resolucion`
--

INSERT INTO `tipo_resolucion` (`id`, `nombre`) VALUES
(1, 'ACEPTADA'),
(3, 'ACEPTADA_CONDICIONAL'),
(2, 'RECHAZADA');

-- --------------------------------------------------------

--
-- Table structure for table `tipo_voto`
--

CREATE TABLE IF NOT EXISTS `tipo_voto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `tipo_voto`
--

INSERT INTO `tipo_voto` (`id`, `nombre`) VALUES
(1, 'ACEPTADO'),
(2, 'RECHAZADO');

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` text NOT NULL,
  `mail` varchar(60) NOT NULL,
  `id_rol` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`,`mail`),
  KEY `rol_idx` (`id_rol`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`id`, `username`, `password`, `mail`, `id_rol`) VALUES
(1, 'admin', 'c93ccd78b2076528346216b3b2f701e6', 'adminMagisterTIDCC@mailinator.com', 1),
(2, 'sochoa', '5f4dcc3b5aa765d61d8327deb882cf99', 'sochoa@mailinator.com', 3),
(3, 'cbrideva', '5f4dcc3b5aa765d61d8327deb882cf99', 'cbrideva@mailinator.com', 5),
(4, 'yordy', '5f4dcc3b5aa765d61d8327deb882cf99', 'yordy@mailinator.com', 4),
(5, 'jpino', '5f4dcc3b5aa765d61d8327deb882cf99', 'jpino@mailinator.com', 2),
(6, 'cecilia', '5f4dcc3b5aa765d61d8327deb882cf99', 'cecilia@mailinator.com', 3),
(7, 'jfabry', '5f4dcc3b5aa765d61d8327deb882cf99', 'jfabry@mailinator.com', 3),
(10, 'teresa', '5f4dcc3b5aa765d61d8327deb882cf99', 'teresa@mailinator.com', 4);

-- --------------------------------------------------------

--
-- Table structure for table `votacion`
--

CREATE TABLE IF NOT EXISTS `votacion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_postulacion` int(11) NOT NULL,
  `usuario` int(11) NOT NULL,
  `id_tipo_voto` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `votacion_usuario_idx` (`usuario`),
  KEY `votacion_tipo_voto_idx` (`id_tipo_voto`),
  KEY `votacion_postulacion_idx` (`id_postulacion`),
  KEY `id_postulacion` (`id_postulacion`),
  KEY `id_postulacion_2` (`id_postulacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cambio_password`
--

CREATE TABLE IF NOT EXISTS `cambio_password` (
  `usuario` int(11) NOT NULL,
  `codigo` varchar(26) NOT NULL,
  `fechahora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  KEY `usuario_codigo` (`usuario`,`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `comentario`
--
ALTER TABLE `comentario`
  ADD CONSTRAINT `comentario_postulacion` FOREIGN KEY (`id_postulacion`) REFERENCES `postulacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `comentario_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `datos_empresa`
--
ALTER TABLE `datos_empresa`
  ADD CONSTRAINT `empresa_postulante` FOREIGN KEY (`id_postulante`) REFERENCES `postulante` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `doc_extra`
--
ALTER TABLE `doc_extra`
  ADD CONSTRAINT `doc_extra_documento` FOREIGN KEY (`id_documento`) REFERENCES `documento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `doc_extra_postulacion` FOREIGN KEY (`id_postulacion`) REFERENCES `postulacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `financiamiento`
--
ALTER TABLE `financiamiento`
  ADD CONSTRAINT `financiamiento_tipo_financiamiento` FOREIGN KEY (`tipo`) REFERENCES `tipo_financiamiento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `grado_academico`
--
ALTER TABLE `grado_academico`
  ADD CONSTRAINT `grado_notas` FOREIGN KEY (`id_certificado_notas`) REFERENCES `documento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `grado_pais` FOREIGN KEY (`id_pais`) REFERENCES `pais` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `grado_postulante` FOREIGN KEY (`id_postulante`) REFERENCES `postulante` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `grado_titulo` FOREIGN KEY (`id_certificado_titulo`) REFERENCES `documento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `historial`
--
ALTER TABLE `historial`
  ADD CONSTRAINT `historial_postulacion` FOREIGN KEY (`id_postulacion`) REFERENCES `postulacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `identificacion`
--
ALTER TABLE `identificacion`
  ADD CONSTRAINT `identificacion_pais` FOREIGN KEY (`pais`) REFERENCES `pais` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `postulacion`
--
ALTER TABLE `postulacion`
  ADD CONSTRAINT `postulacion_carta1` FOREIGN KEY (`id_carta1`) REFERENCES `documento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `postulacion_carta2` FOREIGN KEY (`id_carta2`) REFERENCES `documento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `postulacion_cv` FOREIGN KEY (`id_cv`) REFERENCES `documento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `postulacion_estado` FOREIGN KEY (`id_estado`) REFERENCES `estado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `postulacion_financiamiento` FOREIGN KEY (`id_financiamiento`) REFERENCES `financiamiento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `postulacion_postulante` FOREIGN KEY (`id_postulante`) REFERENCES `postulante` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `postulacion_presentacion` FOREIGN KEY (`id_carta_presentacion`) REFERENCES `documento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `postulante`
--
ALTER TABLE `postulante`
  ADD CONSTRAINT `postulante_genero` FOREIGN KEY (`id_genero`) REFERENCES `genero` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `postulante_identificacion` FOREIGN KEY (`id_identificacion`) REFERENCES `identificacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `postulante_nacionalidad` FOREIGN KEY (`nacionalidad`) REFERENCES `pais` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `postulante_residencia` FOREIGN KEY (`pais_residencia`) REFERENCES `pais` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `resolucion`
--
ALTER TABLE `resolucion`
  ADD CONSTRAINT `resolucion_postulacion` FOREIGN KEY (`id_postulacion`) REFERENCES `postulacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `resolucion_tipo_resolucion` FOREIGN KEY (`id_tipo_resolucion`) REFERENCES `tipo_resolucion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `rol_permiso`
--
ALTER TABLE `rol_permiso`
  ADD CONSTRAINT `permiso` FOREIGN KEY (`id_permiso`) REFERENCES `permiso` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `rol` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_rol` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `votacion`
--
ALTER TABLE `votacion`
  ADD CONSTRAINT `votacion_postulacion` FOREIGN KEY (`id_postulacion`) REFERENCES `postulacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `votacion_tipo_voto` FOREIGN KEY (`id_tipo_voto`) REFERENCES `tipo_voto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `votacion_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `cambio_password`
--
ALTER TABLE `cambio_password`
  ADD CONSTRAINT `cambio_password_ibfk_1` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
