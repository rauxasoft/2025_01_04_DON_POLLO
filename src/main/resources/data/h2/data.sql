INSERT INTO PROVEEDORES (ID, NOMBRE, VIA, POBLACION, CODIGO_POSTAL, PROVINCIA, PAIS) VALUES
(1000, 'HERMANOS MATA, S.L.', 'Avda del Gas 120', 'Montcada i Reixac', '08023', 'Barcelona', 'España'),
(1001, 'COMERCIAL GUGU S.A.', 'c/Picasso, 2', 'Barcelona', '08012', 'Barcelona', 'España');

INSERT INTO EQUIPOS (ID, NOMBRE, FECHA_FUNDACION) VALUES
(50, 'F.C. Reus', '1907-11-15'),
(51, 'Unio Esportiva Mollerusa', '1911-03-22');

INSERT INTO JUGADORES (ID, NOMBRE, DORSAL, ID_EQUIPO) VALUES
(1000, 'Honorio Sánchez', 1, 50),
(1001, 'Carlos Sánchez', 2, 50),
(1002, 'Roberto Sánchez', 3, 50),
(1003, 'Agapito Ruiz', 1, 51),
(1004, 'Carlitos Ruiz', 10, 51),
(1005, 'José Ramón Moreno', 7, 51);

INSERT INTO LIBROS (ISBN, TITULO, NUMERO_PAGINAS, EXPLICITO) VALUES
('ES762555', 'La Biblia del F.C. Barcelona', 125, FALSE),
('ES902245', 'Cocina fácil', 89, FALSE),
('ES200934', 'Java SE 1', 620, FALSE),
('ES908335', 'Los secretos de Java', 243, TRUE);

INSERT INTO JUGUETES (ID, NOMBRE, PRECIO, TIPO, FECHA_ALTA) VALUES
(100, 'Mecano', 120.0, 'ACCION', '2012-09-18'),
(101, 'Vinicius JR', 2.0, 'PUZZLE', '2017-10-21'),
(102, 'La Switch', 24.0, 'CONSOLA', '2012-02-25'),
(103, 'Mario Karts', 80.0, 'CONSOLA', '2017-10-12');