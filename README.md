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


