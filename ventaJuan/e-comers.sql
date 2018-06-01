-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-05-2018 a las 22:05:01
-- Versión del servidor: 10.1.31-MariaDB
-- Versión de PHP: 7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `e-comers`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito`
--

CREATE TABLE `carrito` (
  `id_carrito` int(4) NOT NULL,
  `id_usuario` int(4) NOT NULL,
  `id_producto` int(50) NOT NULL,
  `c_talle` varchar(10) DEFAULT NULL,
  `c_cantidad` varchar(4) DEFAULT NULL,
  `c_color` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `carrito`
--

INSERT INTO `carrito` (`id_carrito`, `id_usuario`, `id_producto`, `c_talle`, `c_cantidad`, `c_color`) VALUES
(25, 1, 21, 'L', '1', 'darkblue'),
(49, 1, 19, 'S', '1', 'green'),
(50, 1, 21, 'L', '2', 'black'),
(51, 16, 22, 'L', '2', 'silver'),
(55, 17, 21, 'L', '7', 'black'),
(56, 17, 23, 'L', '2', 'white'),
(59, 18, 19, 'S', '1', 'red'),
(60, 18, 21, 'L', '2', 'black'),
(61, 19, 19, 'L', '1', 'red');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `otroscolores`
--

CREATE TABLE `otroscolores` (
  `id_otrosColores` int(4) NOT NULL,
  `id_producto` int(4) NOT NULL,
  `o_foto` varchar(50) DEFAULT NULL,
  `o_fotob` varchar(100) DEFAULT NULL,
  `o_color` varchar(50) DEFAULT NULL,
  `o_talleS` int(11) NOT NULL DEFAULT '0',
  `o_talleM` int(11) NOT NULL DEFAULT '0',
  `o_talleL` int(11) NOT NULL DEFAULT '0',
  `o_talleXL` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `otroscolores`
--

INSERT INTO `otroscolores` (`id_otrosColores`, `id_producto`, `o_foto`, `o_fotob`, `o_color`, `o_talleS`, `o_talleM`, `o_talleL`, `o_talleXL`) VALUES
(4, 19, 'DSC_0702.JPG', NULL, 'darkblue', 0, 0, 0, 0),
(5, 19, 'DSC_0701.JPG', NULL, 'green', 1, 0, 0, 0),
(6, 19, 'DSC_0668.JPG', 'DSC_0668@.JPG', 'red', 101, 100, 99, 98),
(7, 21, 'DSC_0806.JPG', 'DSC_0806@.JPG', 'black', 101, 100, 99, 98),
(8, 22, 'DSC_0639.JPG', 'DSC_0639@.JPG', 'silver', 101, 100, 99, 98),
(9, 23, 'DSC_0884.JPG', 'DSC_0884@.JPG', 'white', 0, 100, 99, 98),
(10, 24, 'DSC_0878.JPG', 'DSC_0878@.JPG', 'white', 101, 100, 0, 98);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id_producto` int(4) NOT NULL,
  `p_nombre` varchar(70) NOT NULL,
  `p_precio` int(30) DEFAULT NULL,
  `p_tipo` varchar(70) DEFAULT NULL,
  `p_descripcion` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id_producto`, `p_nombre`, `p_precio`, `p_tipo`, `p_descripcion`) VALUES
(19, 'Vestido rayas bordo', 1190, 'vestido', 'vestidos a rayas con diversos colores'),
(21, 'Vestido Julieta Modal Premium', 990, 'vestido ', 'Vestido premium'),
(22, 'Vestido lanilla', 1190, 'vestido ', 'vestido con diseño unico'),
(23, 'Blusa tul bordado natura', 990, 'blusa', 'hay varios motivos'),
(24, 'Sweater blanco hilo bordado', 990, 'blusa', 'eliminar comentario');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `u_id` int(4) NOT NULL,
  `u_mail` varchar(60) NOT NULL,
  `u_nombreUsuario` varchar(60) NOT NULL,
  `u_clave` varchar(40) NOT NULL,
  `u_tipo` varchar(50) NOT NULL,
  `activo` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`u_id`, `u_mail`, `u_nombreUsuario`, `u_clave`, `u_tipo`, `activo`) VALUES
(1, 'lucio@gmail.com', 'lucio', '1234', 'cliente', NULL),
(16, 'juan@gmail.com', 'juan', '1234', 'cliente', NULL),
(17, 'noelai@gmail.com', 'noelia', '1234', 'cliente', NULL),
(18, 'alvaro@gmail.com', 'alvaro', '1234', 'cliente', NULL),
(19, 'fer@gmail.com', 'fer', '1234', 'cliente', NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD PRIMARY KEY (`id_carrito`);

--
-- Indices de la tabla `otroscolores`
--
ALTER TABLE `otroscolores`
  ADD PRIMARY KEY (`id_otrosColores`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id_producto`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`u_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `carrito`
--
ALTER TABLE `carrito`
  MODIFY `id_carrito` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;

--
-- AUTO_INCREMENT de la tabla `otroscolores`
--
ALTER TABLE `otroscolores`
  MODIFY `id_otrosColores` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id_producto` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `u_id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
