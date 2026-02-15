# Solución: Error de Permisos en PostgreSQL

## Error
```
ERROR: permission denied for table users
```

Este error ocurre cuando el usuario de PostgreSQL no tiene permisos para acceder a las tablas.

## Solución Rápida

### Paso 1: Conectarse a PostgreSQL como superusuario

```bash
psql -U postgres
```

Si tu usuario es diferente, usa:
```bash
psql -U tu_usuario
```

### Paso 2: Otorgar permisos al usuario

Una vez conectado, ejecuta estos comandos:

```sql
-- Conectarse a la base de datos
\c cosmetologia_db

-- Otorgar todos los permisos al usuario postgres (o tu usuario)
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO postgres;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO postgres;

-- Si las tablas aún no existen, otorgar permisos para crearlas
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO postgres;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO postgres;

-- Verificar permisos
\dp
```

### Paso 3: Verificar que las tablas existan

```sql
-- Ver todas las tablas
\dt

-- Si no hay tablas, las migraciones de Flyway deberían crearlas
-- Si las tablas existen pero no tienes permisos, ejecuta:
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO postgres;
```

### Paso 4: Si el usuario es diferente a 'postgres'

Si estás usando un usuario diferente (por ejemplo, tu usuario de macOS), necesitas:

1. **Verificar qué usuario estás usando en application.yml:**
   ```yaml
   spring:
     datasource:
       username: postgres  # <-- Este es el usuario
   ```

2. **Otorgar permisos a ese usuario específico:**
   ```sql
   GRANT ALL PRIVILEGES ON DATABASE cosmetologia_db TO tu_usuario;
   \c cosmetologia_db
   GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO tu_usuario;
   ```

## Solución Alternativa: Crear un usuario específico

Si prefieres crear un usuario dedicado para la aplicación:

```sql
-- Crear usuario
CREATE USER cosmetologia_user WITH PASSWORD 'tu_contraseña_segura';

-- Otorgar permisos
GRANT ALL PRIVILEGES ON DATABASE cosmetologia_db TO cosmetologia_user;

-- Conectarse a la base de datos
\c cosmetologia_db

-- Otorgar permisos en el esquema
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO cosmetologia_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO cosmetologia_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO cosmetologia_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO cosmetologia_user;
```

Luego actualiza `application.yml`:
```yaml
spring:
  datasource:
    username: cosmetologia_user
    password: tu_contraseña_segura
```

## Verificación

Después de otorgar permisos, verifica:

```sql
-- Ver permisos de las tablas
\dp users
\dp patients
\dp appointments
\dp payments
```

Deberías ver `postgres=arwdDxt/postgres` o similar, indicando que tienes todos los permisos.

## Reiniciar la Aplicación

Después de otorgar permisos, reinicia el backend:

```bash
# Detener el backend (Ctrl+C)
# Volver a ejecutar
mvn spring-boot:run
```

## Comandos Útiles

```sql
-- Ver todos los usuarios
\du

-- Ver el usuario actual
SELECT current_user;

-- Ver la base de datos actual
SELECT current_database();

-- Ver todos los permisos
SELECT * FROM information_schema.table_privileges 
WHERE table_schema = 'public';
```
