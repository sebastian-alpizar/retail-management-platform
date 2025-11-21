# 🛒 Retail Management Platform
Sistema distribuido de punto de venta con Frontend y Backend independientes, comunicación por sockets y arquitectura MVC.

## 📌 Descripción General
Retail Management Platform es una aplicación distribuida desarrollada en Java que implementa un sistema completo de punto de venta (SPV) bajo una arquitectura moderna.
El proyecto está dividido en Backend, Frontend, y un módulo compartido de Entidades, siguiendo buenas prácticas de modularización y comunicación cliente-servidor mediante sockets y threads.

Este sistema permite registrar usuarios, clientes, cajeros, productos y facturas, además de gestionar procesos comerciales en tiempo real con múltiples usuarios conectados simultáneamente.

Incluye:

- Arquitectura distribuida: Frontend y Backend ejecutándose como procesos independientes
- Comunicación bidireccional mediante sockets TCP
- Notificaciones asíncronas del Backend hacia todos los Frontend
- Gestión de facturas y facturas en trámite transferibles entre usuarios
- Lista de usuarios conectados en tiempo real
- Separación estricta por capas (Datos, Lógica, Presentación)
- Persistencia con MySQL

## ✨ Características Principales

✔️ Arquitectura distribuida con comunicación por sockets  
✔️ Backend multithread actuando como servidor  
✔️ Frontend con arquitectura MVC  
✔️ Transferencia de facturas en trámite entre usuarios  
✔️ Actualización en vivo de usuarios conectados  
✔️ Sistema de login y registro  
✔️ Manipulación de productos, clientes, cajeros y facturas  
✔️ Entidades compartidas mediante módulo independiente  
✔️ Persistencia de datos con MySQL  
✔️ Uso del patrón Proxy para comunicación remota

## 🏗️ Tecnologías Utilizadas
### ☕ Lenguaje

- Java 17+

### 🗄️ Backend

- Sockets TCP
- Programación concurrente con Threads
- JDBC + MySQL
- POO + DAO Pattern

### 🧩 Frontend

- Java Swing (UI)
- MVC (Model–View–Controller)
- Socket Client + Proxy Pattern

### 📦 Módulo Compartido

- Entidades POJO reutilizadas por ambos proyectos

### 🛠️ Tooling

- Maven
- NetBeans / IntelliJ IDEA
- Git & GitHub

## 🧱 Arquitectura del Proyecto

El sistema se divide en tres módulos principales:

### 🔧 BackendSPV (Servidor)

Ubicación: `/BackendSPV`

Responsabilidades:

- Procesar peticiones enviadas desde los Frontend
- Acceder a la base de datos (único punto de persistencia)
- Manejar múltiples conexiones mediante hilos
- Enviar notificaciones asíncronas a los Frontend activos
(login, logout, transferencia de facturas)
- Gestionar operaciones CRUD mediante DAOs

```bash
BackendSPV/
 ├── datos/      // DAO y conexión MySQL
 ├── logica/     // Backend, Server, hilos y procesamiento
 └── BackendSPV.java  // Entry point del servidor
```
### 🖥️ SistemaPuntoDeVenta (Frontend)

Ubicación: `/SistemaPuntoDeVenta`

Responsabilidades:

- Presentar la interfaz gráfica (Java Swing)
- Gestionar navegación y vistas (panels)
- Comunicarse con Backend mediante Proxy + Sockets
- Mostrar usuarios conectados en tiempo real
- Permitir enviar y recibir facturas en trámite
- Implementar MVC en todos los módulos de UI
- Estructura destacada:

```bash
SistemaPuntoDeVenta/
 ├── Controller/   // Controladores MVC
 ├── View/         // UI (login, panels, main window)
 ├── Model/        // Proxy y lógica local
 └── Main.java     // Entry point del Frontend
```

### 📦 SistemaEntidades (Entidades Compartidas)

Ubicación: `/SistemaEntidades`

Incluye clases como:

- Usuario
- Cliente
- Cajero
- Producto
- Factura
- LineaFactura
- Mensaje (para comunicación por sockets)

Estas clases son utilizadas por Backend y Frontend como librería.

## 🧰 Requisitos Previos

Asegúrate de tener instalado:

- Java 17+
- Maven 3+
- MySQL 8
- IDE recomendado: NetBeans / IntelliJ IDEA

## 🚀 Instalación
1️⃣ Clonar el repositorio

```bash
git clone https://github.com/sebastian-alpizar/retail-management-platform.git
cd retail-management-platform
```

## ⚙️ Configuración del Backend
2️⃣ Configurar la base de datos MySQL

Crea una base llamada:

```bash
retail_spv
```

Actualiza el archivo `.env` del backend:

```bash
DB_HOST=localhost
DB_PORT=3306
DB_NAME=ProyectoDatabase
DB_USER=root
DB_PASSWORD=
```
