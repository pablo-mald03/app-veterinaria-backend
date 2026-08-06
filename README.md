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
* Administración de usuarios, roles y permisos.
* API REST preparada para integraciones futuras.

---

# 🚀 Stack Tecnológico

* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* PostgreSQL
* Lombok
* MapStruct
* OpenAPI / Swagger

---


# 🔒 Seguridad

La autenticación y autorización estarán basadas en **JSON Web Tokens (JWT)**, permitiendo un acceso seguro a los recursos de la aplicación mediante un esquema stateless.

Entre las características contempladas se encuentran:

* Autorización basada en roles.
* Validación de permisos.
* Manejo centralizado de excepciones.

---

# 🛠️ Buenas prácticas

Durante el desarrollo se seguirán principios y estándares ampliamente utilizados en aplicaciones empresariales con Spring.

* Arquitectura limpia y modular.
* Principios SOLID.
* Validación de datos.
* Documentación automática de la API.

---

# 📂 Estado del proyecto

> 🚧 En desarrollo.


# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.happypets.app-veterinaria-backend' is invalid and this project uses 'com.happypets.app_veterinaria_backend' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/4.1.0/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.1.0/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/4.1.0/reference/web/servlet.html)
* [Spring Security](https://docs.spring.io/spring-boot/4.1.0/reference/web/spring-security.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/4.1.0/reference/using/devtools.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/4.1.0/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Validation](https://docs.spring.io/spring-boot/4.1.0/reference/io/validation.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.


