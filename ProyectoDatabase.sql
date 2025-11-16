CREATE DATABASE ProyectoDatabase;
USE ProyectoDatabase;


CREATE TABLE Usuario (
    id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE Cajero (
    id VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE,
    telefono VARCHAR(20),
    email VARCHAR(100)
);
CREATE TABLE Cliente (
    id VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE,
    telefono VARCHAR(20),
    email VARCHAR(100),
    descuento DECIMAL(5, 2)
);
CREATE TABLE Producto (
    codigo VARCHAR(50) PRIMARY KEY,
    descripcion VARCHAR(255),
    unidadMedida VARCHAR(50),
    precioUnitario DECIMAL(10, 2),
    existencias INT,
    categoria VARCHAR(100)
);
CREATE TABLE Factura (
    id VARCHAR(50) PRIMARY KEY,
    cliente_nombre VARCHAR(100),
    cajero_nombre VARCHAR(100),
    fecha DATE,
    formaPago VARCHAR(50),
    total DECIMAL(10, 2),
    FOREIGN KEY (cliente_nombre) REFERENCES Cliente(nombre),
    FOREIGN KEY (cajero_nombre) REFERENCES Cajero(nombre)
);
CREATE TABLE LineaFactura (
    factura_id VARCHAR(50),
    producto_codigo VARCHAR(50),
    cantidad INT,
    descuento DECIMAL(5, 2),
    total DECIMAL(10, 2),
    FOREIGN KEY (factura_id) REFERENCES Factura(id)
);
CREATE TABLE Venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    facturaId VARCHAR(36),
    categoria VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    month INT NOT NULL,
    totalLinea DOUBLE NOT NULL,
    cliente VARCHAR(255) NOT NULL,
    FOREIGN KEY (facturaId) REFERENCES Factura(id)
);