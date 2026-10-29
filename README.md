# 🩺 HappyPets — Sistema de Gestion Veterinaria · Backend

Backend del sistema de gestión integral para clínicas veterinarias, desarrollado con **Spring Boot 4.1.0** y desplegado automáticamente en **AWS EC2** mediante un pipeline de CI/CD con GitHub Actions.

La arquitectura está diseñada para ser robusta, escalable y mantenible, siguiendo principios de arquitectura limpia, SOLID y las mejores prácticas del ecosistema Spring.

---

## 📖 Descripción

El sistema centraliza la administración de los procesos de una clínica veterinaria mediante una **API REST** que se comunica con el frontend desarrollado en React.

Proporciona una plataforma confiable para la gestión de:

- Expedientes médicos, clientes y mascotas
- Citas y consultas médicas
- Productos, inventario y lotes
- Facturación y pagos (con generación de PDF)
- Administración de usuarios, roles y permisos

---

## ✅ Estado del proyecto

> ** Proyecto finalizado y en producción.**

El backend se encuentra completamente implementado, probado y desplegado en AWS EC2 con despliegue continuo automatizado.

---


## 📋 Documentacion Oficial de la aplicacion

- [Documentacion](https://drive.google.com/drive/folders/1rjmpRQp0Czfnl6KVu8TuPqDf1islL4gg?usp=sharing)

---

## 🚀 Stack Tecnológico

| Tecnología | Versión |
|---|---|
| Java | **21** (Eclipse Temurin) |
| Spring Boot | **4.1.0** |
| Apache Maven | **3.9.6** |
| Spring Security + JWT (JJWT) | `0.12.6` |
| Spring Data JPA + Hibernate | — |
| PostgreSQL | — |
| Flyway (migraciones) | — |
| MapStruct | `1.6.3` |
| Lombok | — |
| SpringDoc OpenAPI / Swagger UI | `3.1.0` |
| Spring Shell | `4.0.3` |
| OpenPDF | `2.0.3` |
| Spring Boot Actuator | — |
| springboot4-dotenv | `5.0.1` |
| Docker / Docker Compose | — |

---

## ✨ Funcionalidades

- Gestión de clientes y mascotas
- Administración de usuarios, roles y permisos
- Gestión de citas y consultas veterinarias
- Control de inventario y lotes
- Catálogo de productos y categorías
- Facturación y generación de reportes en PDF
- Procesamiento de pagos
- Autenticación y autorización basada en JWT
- Documentación automática de la API con Swagger UI
- Migraciones de base de datos con Flyway

---

## 🛠️ Requisitos previos

Asegúrate de tener instalado lo siguiente antes de levantar el proyecto:

- **Java 21** (Eclipse Temurin recomendado)
- **Apache Maven 3.9+** *(o usa el wrapper `./mvnw` incluido en el repositorio)*
- **PostgreSQL** — instancia local o remota accesible
- **Git**

> **Nota sobre Maven Wrapper**: El repositorio incluye `mvnw` y `mvnw.cmd`. Puedes usarlos directamente sin tener Maven instalado globalmente:
> ```bash
> chmod +x ./mvnw   # Solo en Linux/macOS
> ./mvnw --version
> ```

---

## 💻 Levantar el proyecto en local

### 1. Clona el repositorio

```bash
git clone https://github.com/pablo-mald03/app-veterinaria-backend.git
cd app-veterinaria-backend
```

### 2. Crea tu archivo `.env`

Copia el archivo de ejemplo y ajusta los valores según tu entorno local:

```bash
cp .env-example .env
```

Edita `.env` con tus credenciales de PostgreSQL y los demás valores. Ver la sección [Variables de entorno](#-variables-de-entorno) para el detalle de cada variable.

### 3. Crea la base de datos en PostgreSQL

```sql
CREATE DATABASE veterinarydb;
CREATE USER adminvet WITH PASSWORD 'admin1234';
GRANT ALL PRIVILEGES ON DATABASE veterinarydb TO adminvet;
```

> Las migraciones de esquema son gestionadas automáticamente por **Flyway** al iniciar la aplicación. No es necesario ejecutar scripts SQL manualmente.

### 4. Compila el proyecto

```bash
# Con Maven Wrapper (recomendado)
./mvnw clean compile

# O con Maven instalado globalmente
mvn clean compile
```

### 5. Ejecuta la aplicación

```bash
# Con Maven Wrapper
./mvnw spring-boot:run

# O con Maven global
mvn spring-boot:run
```

La aplicación estará disponible en: **`http://localhost:8080`**

### 6. Accede a la documentación de la API (Swagger UI)

Una vez en ejecución, abre en tu navegador:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🔑 Variables de entorno

El proyecto usa **springboot4-dotenv** para leer las variables desde un archivo `.env` en la raíz del proyecto. Crea tu archivo copiando el ejemplo:

```bash
cp .env-example .env
```

### Referencia completa de variables

```env
##########################################
# SPRING
##########################################
# Perfil activo: dev | test | prod
SPRING_PROFILES_ACTIVE=dev

# Puerto en el que escucha el servidor
SERVER_PORT=8080


##########################################
# DATABASE — PostgreSQL
##########################################
DB_HOST=localhost
DB_PORT=5432
DB_NAME=veterinarydb

DB_USERNAME=adminvet
DB_PASSWORD=admin1234


##########################################
# JWT
##########################################
# Clave secreta para firmar los tokens (usa una cadena larga y aleatoria en prod)
JWT_SECRET=secret

# Tiempo de expiración del token de acceso en milisegundos (ej: 300000 = 5 min)
JWT_EXPIRATION=300000

# Ventana de refresco del token en milisegundos (ej: 172800000 = 48 h)
JWT_REFRESH_WINDOW=172800000


##########################################
# CORS
##########################################
# URL del frontend permitida en CORS
URL_ALLOWED=http://localhost:3000


##########################################
# ROLES RESERVADOS
##########################################
RESERVED_ROLE1=ADMIN
```

> ⚠️ **Nunca subas tu archivo `.env` real al repositorio.** El archivo `.gitignore` ya lo excluye. Usa `.env-example` como plantilla.

---

## 🐳 Ejecución con Docker (opcional, local)

El `Dockerfile` utiliza una **build multi-etapa** con Maven 3.9.6 + Eclipse Temurin 21:

```bash
# Construir la imagen
docker build -t app-veterinaria-backend .

# Ejecutar el contenedor pasando el archivo .env
docker run --env-file .env -p 8080:8080 app-veterinaria-backend
```

---

## ⚙️ CI/CD — GitHub Actions

El proyecto cuenta con un pipeline completo de integración y despliegue continuo.

### Flujo de trabajo

```
Push a main / develop
        │
        ▼
 ┌─────────────────┐
 │  CI: ci.yml     │  → Compila + ejecuta pruebas unitarias
 └────────┬────────┘
          │  (solo en push a main)
          ▼
 ┌─────────────────┐
 │  CD: cd.yml     │  → Build imagen Docker → push a GHCR → deploy en EC2
 └─────────────────┘
```

### CI (`ci.yml`) — Integración Continua

Se ejecuta en cada **push o pull request** a `main` o `develop`:

1. **Compilación** — `./mvnw clean compile`
2. **Pruebas unitarias** — `./mvnw test` con perfil `test`
3. Publica reportes Surefire como artefacto de GitHub Actions (retención 7 días)

### CD (`cd.yml`) — Despliegue Continuo

Se ejecuta únicamente en **push a `main`**:

1. Inicia sesión en **GitHub Container Registry (GHCR)**
2. Construye y publica la imagen Docker con tags `latest` y `<sha>`
3. Conecta por SSH a la instancia **AWS EC2**
4. Ejecuta `docker compose pull && docker compose up -d` y limpia imágenes antiguas

### Secrets de GitHub necesarios

| Secret | Descripción |
|---|---|
| `GITHUB_TOKEN` | Generado automáticamente por GitHub Actions |
| `EC2_HOST` | IP pública o DNS de la instancia EC2 |
| `EC2_USERNAME` | Usuario SSH de la instancia (ej: `ubuntu`) |
| `VETERINARIA_SERVER` | Clave privada SSH para acceder al servidor |

---

## 🔒 Seguridad

La autenticación y autorización están basadas en **JWT (JSON Web Tokens)** con un esquema completamente stateless:

- Tokens de acceso con expiración configurable
- Ventana de refresco de token
- Autorización basada en roles y permisos
- Manejo centralizado de excepciones de seguridad
- Usuario no-root en el contenedor Docker
- Validación de datos en todos los endpoints

---

## 🛠️ Buenas prácticas implementadas

- **Arquitectura limpia y modular** — separación por capas (domain, application, infrastructure)
- **Principios SOLID**
- **Migraciones versionadas** con Flyway
- **Documentación automática** de la API con SpringDoc OpenAPI
- **Build multi-etapa** en Docker para imágenes ligeras
- **Maven Wrapper** (`mvnw`) incluido — no se requiere Maven instalado globalmente
- **Variables de entorno** externalizadas vía `.env`
- **Spring Boot Actuator** para health checks y monitoreo

---

## 📚 Referencias

- [Documentación oficial de Apache Maven](https://maven.apache.org/guides/index.html)
- [Spring Boot Maven Plugin](https://docs.spring.io/spring-boot/4.1.0/maven-plugin)
- [Spring Web (Servlet)](https://docs.spring.io/spring-boot/4.1.0/reference/web/servlet.html)
- [Spring Security](https://docs.spring.io/spring-boot/4.1.0/reference/web/spring-security.html)
- [Spring Data JPA](https://docs.spring.io/spring-boot/4.1.0/reference/data/sql.html#data.sql.jpa-and-spring-data)
- [SpringDoc OpenAPI](https://springdoc.org/)
- [JJWT](https://github.com/jwtk/jjwt)
- [Flyway](https://flywaydb.org/documentation/)

---

> **Nota**: El package base del proyecto es `com.happypets.app_veterinaria_backend` (con guion bajo) ya que `com.happypets.app-veterinaria-backend` es un nombre de paquete Java inválido.
