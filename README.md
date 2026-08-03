# 🩺 Veterinary Management System - Backend

Backend del sistema de gestión integral para clínicas veterinarias, desarrollado con **Spring Boot**. Este proyecto proporciona la lógica de negocio, seguridad, persistencia de datos y servicios REST necesarios para soportar todas las operaciones del sistema.

La arquitectura está diseñada para ofrecer un backend robusto, escalable y mantenible, siguiendo las mejores prácticas del ecosistema Spring y una estructura orientada al crecimiento del proyecto.

---

# 📖 Descripción

El sistema centraliza la administración de los procesos de una clínica veterinaria mediante una API REST que permitirá la comunicación con distintas aplicaciones cliente, principalmente el frontend desarrollado en React.

Su objetivo es proporcionar una plataforma confiable para la gestión de expedientes médicos, clientes, mascotas, citas, consultas, productos, inventario, facturación, pagos y control administrativo, garantizando la integridad de la información y la seguridad de los usuarios.

Este repositorio representa únicamente la capa **Backend** del sistema.

---

# ✨ Funcionalidades generales

* Gestión de clientes y mascotas.
* Administración de expedientes veterinarios.
* Control de citas y agenda médica.
* Gestión de consultas, tratamientos e historial clínico.
* Administración de productos, medicamentos e inventario.
* Configuración de precios, promociones y costos adicionales.
* Registro y control de pagos mediante diferentes métodos.
* Administración de usuarios, roles y permisos.
* API REST preparada para integraciones futuras.

---

# 🚀 Stack Tecnológico

* Java
* Spring Boot
* Spring Web
* Spring Security
* Spring Data JPA
* Hibernate
* Bean Validation
* PostgreSQL
* Flyway
* Maven
* JWT Authentication
* Lombok
* MapStruct
* OpenAPI / Swagger

---


# 🔒 Seguridad

La autenticación y autorización estarán basadas en **JSON Web Tokens (JWT)**, permitiendo un acceso seguro a los recursos de la aplicación mediante un esquema stateless.

Entre las características contempladas se encuentran:

* Inicio de sesión seguro.
* Autorización basada en roles.
* Protección de endpoints.
* Validación de permisos.
* Manejo centralizado de excepciones.
* Validación de solicitudes mediante Bean Validation.

---

# 🛠️ Buenas prácticas

Durante el desarrollo se seguirán principios y estándares ampliamente utilizados en aplicaciones empresariales con Spring.

* Arquitectura limpia y modular.
* Principios SOLID.
* Separación de responsabilidades.
* Inyección de dependencias.
* Manejo global de excepciones.
* Validación de datos.
* Documentación automática de la API.
* Código limpio y mantenible.

---

# 📚 Documentación

La API contará con documentación interactiva utilizando **OpenAPI (Swagger)** para facilitar el consumo de los servicios REST durante el desarrollo y las pruebas.

---

# 📂 Estado del proyecto

> 🚧 En desarrollo.
