# Gestión Estética - Backend

Backend para el sistema de gestión de cosmetología médica desarrollado con Spring Boot.

## Tecnologías

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** - Autenticación JWT
- **Spring Data JPA** - Persistencia de datos
- **PostgreSQL** - Base de datos
- **Flyway** - Migraciones de base de datos
- **Lombok** - Reducción de código boilerplate
- **JWT (jjwt)** - Tokens de autenticación

## Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- PostgreSQL 12+

## Configuración

1. **Crear la base de datos PostgreSQL:**

```sql
CREATE DATABASE cosmetologia_db;
```

2. **Configurar las credenciales en `application.yml`:**

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/cosmetologia_db
    username: postgres
    password: postgres
```

3. **Configurar el secreto JWT (opcional):**

Puedes establecer la variable de entorno `JWT_SECRET` o usar el valor por defecto en `application.yml`.

## Instalación y Ejecución

1. **Instalar dependencias:**

```bash
mvn clean install
```

2. **Ejecutar la aplicación:**

```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## Migraciones de Base de Datos

Las migraciones Flyway se ejecutan automáticamente al iniciar la aplicación. Se encuentran en `src/main/resources/db/migration/`:

- `V1__create_users_table.sql` - Tabla de usuarios
- `V2__create_patients_table.sql` - Tabla de pacientes
- `V3__create_appointments_table.sql` - Tabla de turnos
- `V4__create_payments_table.sql` - Tabla de pagos

## Credenciales por Defecto

- **Usuario:** admin
- **Contraseña:** admin123

## Endpoints de la API

### Autenticación
- `POST /api/auth/login` - Iniciar sesión
- `GET /api/auth/me` - Obtener usuario actual

### Pacientes
- `GET /api/patients` - Listar pacientes (con búsqueda opcional)
- `GET /api/patients/{id}` - Obtener paciente por ID
- `POST /api/patients` - Crear paciente
- `PUT /api/patients/{id}` - Actualizar paciente
- `DELETE /api/patients/{id}` - Eliminar paciente

### Turnos
- `GET /api/appointments` - Listar turnos
- `GET /api/appointments/{id}` - Obtener turno por ID
- `POST /api/appointments` - Crear turno
- `PUT /api/appointments/{id}` - Actualizar turno
- `DELETE /api/appointments/{id}` - Eliminar turno

### Pagos
- `GET /api/payments` - Listar pagos
- `GET /api/payments/{id}` - Obtener pago por ID
- `GET /api/payments/total` - Obtener total en rango de fechas
- `POST /api/payments` - Crear pago
- `PUT /api/payments/{id}` - Actualizar pago
- `DELETE /api/payments/{id}` - Eliminar pago

## Estructura del Proyecto

```
src/main/java/com/cosmetologia/app/
├── auth/              # Autenticación y usuarios
├── patient/           # Gestión de pacientes
├── appointment/        # Gestión de turnos
├── payment/           # Gestión de pagos
├── common/            # Configuración y utilidades comunes
└── CosmetologiaApplication.java
```

## Desarrollo

Para desarrollo, puedes usar:

```bash
# Ejecutar con hot reload (requiere spring-boot-devtools)
mvn spring-boot:run

# Ejecutar tests
mvn test
```
