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
1. Clonar el repositorio

```bash
git clone https://github.com/sebastian-alpizar/retail-management-platform.git
cd retail-management-platform
```

### ⚙️ Configuración del Backend
2. Configurar la base de datos MySQL
```bash
retail_spv
```

3. Copiar y configurar el archivo `.env` a partir de `.env.example`:
```bash
cd BackendSPV
cp .env.example .env
```
Editar `.env` con tus credenciales de base de datos y configuración de puerto:

```bash
DB_HOST=localhost
DB_PORT=3306
DB_NAME=retail_spv
DB_USER=root
DB_PASSWORD=
```

4. Construir el proyecto Backend
```bash
cd BackendSPV
mvn clean install
```

5. Iniciar el servidor Backend
```bash
BackendSPV -> BackendSPV.java
```
El servidor comenzará a escuchar en un puerto definido (ej. 5000).

### 🖥️ Ejecución del Frontend
6. Copiar y configurar `.env` a partir de `.env.example`:
```bash
cd SistemaPuntoDeVenta
cp .env.example .env
```
Editar `.env` con las credenciales y puerto del Backend al que conectarse:
```bash
APP_PROXY_HOST=localhost
APP_PROXY_PORT=12345
```

7. Construir el proyecto
```bash
mvn clean install
```

8. Ejecutar Frontend:
```bash
SistemaPuntoDeVenta -> Main.java
```

Al iniciar:
- Solicitará credenciales
- Se conectará al backend
- Mostrará la ventana principal del SPV
- Cargará los usuarios conectados en tiempo real

## 📡 Comunicación del Sistema

🔄 Flujo General

1. Frontend se conecta al servidor mediante sockets
2. Usuario hace login
3. Backend autentica y añade a la lista de usuarios conectados
4. Backend notifica a los demás Frontend
5. Frontend permite:
   - Registrar clientes, productos, cajeros
   - Crear facturas
   - Visualizar facturas históricas
6. Usuarios pueden enviar facturas en trámite a cualquier usuario activo
7. Backend reenvía la factura al destinatario
8. El destinatario puede recibirla y continuar la operación

## 📊 Ejemplos Visuales

## 🧪 Testing

Los módulos están estructurados para facilitar pruebas unitarias y pruebas manuales:

- Pruebas de sockets
- Simulación de múltiples Frontend
- Validación de concurrencia en el Backend
- Pruebas de transferencia de facturas

## 📦 Despliegue

Opciones recomendadas:

**Opción 1 – Ejecución local distribuida** 

- Backend en un proceso
- Múltiples Frontend en procesos independientes
- MySQL local

**Opción 2 – Infraestructura remota**

- Backend en una máquina o servidor
- Frontend en máquinas cliente
- Conexión por red local o VPN

## 🗺️ Roadmap

- 🔐 Sistema de roles avanzado
- 📊 Dashboard con estadísticas visuales
- 📄 Exportar facturas a PDF
- 📈 Mejoras de desempeño en sockets
- 💬 Chat interno entre usuarios
- 🏪 Multi-sucursal

## 👤 Autor

**Desarrollado por Sebastián Alpízar Porras**
GitHub: https://github.com/sebastian-alpizar
Email: sebastianalpiz@gmail.com

