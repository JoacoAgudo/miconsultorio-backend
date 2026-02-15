# Configuración de Base de Datos - PostgreSQL

Esta guía te ayudará a configurar PostgreSQL antes de ejecutar el backend.

## Requisitos Previos

- PostgreSQL 12 o superior instalado
- Acceso a la línea de comandos o a un cliente PostgreSQL (pgAdmin, DBeaver, etc.)

## Pasos para Configurar la Base de Datos

### Opción 1: Usando psql (Línea de Comandos)

1. **Conectarse a PostgreSQL:**
   ```bash
   psql -U postgres
   ```
   (Si tienes otro usuario, reemplaza `postgres` con tu usuario)

2. **Crear la base de datos:**
   ```sql
   CREATE DATABASE cosmetologia_db;
   ```

3. **Verificar que se creó correctamente:**
   ```sql
   \l
   ```
   Deberías ver `cosmetologia_db` en la lista.

4. **Salir de psql:**
   ```sql
   \q
   ```

### Opción 2: Usando pgAdmin (Interfaz Gráfica)

1. Abre pgAdmin
2. Conecta a tu servidor PostgreSQL
3. Click derecho en "Databases" → "Create" → "Database..."
4. En "Database" escribe: `cosmetologia_db`
5. Click en "Save"

### Opción 3: Usando Docker (Si tienes PostgreSQL en Docker)

```bash
# Si tienes un contenedor PostgreSQL corriendo
docker exec -it <nombre_contenedor> psql -U postgres

# Luego ejecuta:
CREATE DATABASE cosmetologia_db;
```

## Configuración de Credenciales

### Verificar/Configurar Usuario y Contraseña

El proyecto está configurado por defecto con:
- **Usuario:** `postgres`
- **Contraseña:** `postgres`
- **Base de datos:** `cosmetologia_db`
- **Puerto:** `5432`
- **Host:** `localhost`

### Si tus credenciales son diferentes:

Edita el archivo `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/cosmetologia_db
    username: TU_USUARIO_AQUI
    password: TU_CONTRASEÑA_AQUI
```

## Verificación

Para verificar que todo está correcto:

1. **Verificar que PostgreSQL está corriendo:**
   ```bash
   # En macOS/Linux
   pg_isready
   
   # O verificar el proceso
   ps aux | grep postgres
   ```

2. **Verificar conexión:**
   ```bash
   psql -U postgres -d cosmetologia_db -c "SELECT version();"
   ```

## Migraciones Automáticas

**¡IMPORTANTE!** Las migraciones de Flyway se ejecutarán automáticamente cuando inicies el backend por primera vez. No necesitas crear las tablas manualmente.

Las migraciones crearán:
- Tabla `users` (con usuario admin por defecto)
- Tabla `patients`
- Tabla `appointments`
- Tabla `payments`

## Usuario por Defecto

Después de ejecutar las migraciones, tendrás un usuario creado automáticamente:
- **Username:** `admin`
- **Password:** `admin123`

## Solución de Problemas

### Error: "database does not exist"
- Asegúrate de haber creado la base de datos `cosmetologia_db`

### Error: "password authentication failed"
- Verifica las credenciales en `application.yml`
- O cambia la contraseña de PostgreSQL:
  ```sql
  ALTER USER postgres WITH PASSWORD 'nueva_contraseña';
  ```

### Error: "connection refused"
- Verifica que PostgreSQL esté corriendo
- Verifica el puerto (por defecto 5432)
- Verifica el host (localhost)

### Error: "permission denied"
- Asegúrate de que el usuario tenga permisos para crear tablas
- Puedes otorgar permisos:
  ```sql
  GRANT ALL PRIVILEGES ON DATABASE cosmetologia_db TO postgres;
  ```

## Comandos Útiles

```sql
-- Ver todas las bases de datos
\l

-- Conectarse a una base de datos
\c cosmetologia_db

-- Ver todas las tablas
\dt

-- Ver estructura de una tabla
\d nombre_tabla

-- Ver usuarios
\du
```

## Siguiente Paso

Una vez configurada la base de datos, puedes ejecutar el backend:

```bash
cd gestion-estetica-backend
mvn clean install
mvn spring-boot:run
```

Las migraciones se ejecutarán automáticamente y verás mensajes como:
```
Flyway Community Edition ... executing SQL ...
Successfully applied 4 migrations
```
