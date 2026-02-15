# Guía de Inicio Rápido - Gestión Estética

Esta guía te ayudará a configurar y ejecutar el proyecto completo.

## 📋 Requisitos Previos

### Software Necesario:
- **Java 17** o superior
- **Maven 3.6+**
- **PostgreSQL 12+**
- **Node.js 18+** y npm
- **Git** (opcional)

## 🗄️ PASO 1: Configuración de Base de Datos PostgreSQL

### 1.1 Verificar que PostgreSQL está instalado y corriendo

```bash
# Verificar si PostgreSQL está corriendo
pg_isready

# O verificar el proceso
ps aux | grep postgres
```

Si no está corriendo, inícialo:
```bash
# En macOS (con Homebrew)
brew services start postgresql

# En Linux
sudo systemctl start postgresql

# En Windows (desde Services)
# Busca "PostgreSQL" en Servicios
```

### 1.2 Crear la Base de Datos

**Opción A: Desde la línea de comandos (psql)**

```bash
# Conectarse a PostgreSQL
psql -U postgres

# Crear la base de datos
CREATE DATABASE cosmetologia_db;

# Verificar que se creó
\l

# Salir
\q
```

**Opción B: Desde pgAdmin (Interfaz Gráfica)**

1. Abre pgAdmin
2. Conecta a tu servidor PostgreSQL
3. Click derecho en "Databases" → "Create" → "Database..."
4. Nombre: `cosmetologia_db`
5. Click "Save"

**Opción C: Desde Docker**

```bash
# Si tienes PostgreSQL en Docker
docker exec -it <nombre_contenedor> psql -U postgres -c "CREATE DATABASE cosmetologia_db;"
```

### 1.3 Verificar Credenciales

El proyecto está configurado por defecto con:
- **Usuario:** `postgres`
- **Contraseña:** `postgres`
- **Base de datos:** `cosmetologia_db`
- **Puerto:** `5432`
- **Host:** `localhost`

**Si tus credenciales son diferentes**, edita:
```
gestion-estetica-backend/src/main/resources/application.yml
```

Y modifica:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/cosmetologia_db
    username: TU_USUARIO
    password: TU_CONTRASEÑA
```

### 1.4 Verificar Conexión

```bash
# Probar conexión
psql -U postgres -d cosmetologia_db -c "SELECT version();"
```

Si funciona, verás la versión de PostgreSQL.

---

## 🔧 PASO 2: Configurar y Ejecutar el Backend

### 2.1 Navegar al directorio del backend

```bash
cd gestion-estetica-backend
```

### 2.2 Instalar dependencias y compilar

```bash
mvn clean install
```

Esto descargará todas las dependencias y compilará el proyecto.

### 2.3 Ejecutar el backend

```bash
mvn spring-boot:run
```

**¡IMPORTANTE!** La primera vez que ejecutes el backend:
- Flyway ejecutará automáticamente las migraciones
- Se crearán las tablas: `users`, `patients`, `appointments`, `payments`
- Se creará el usuario por defecto: `admin` / `admin123`

Deberías ver mensajes como:
```
Flyway Community Edition ... executing SQL ...
Successfully applied 4 migrations
```

El backend estará disponible en: **http://localhost:8080**

### 2.4 Verificar que el backend funciona

Abre otra terminal y prueba:
```bash
curl http://localhost:8080/api/auth/login -X POST \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

Deberías recibir un token JWT.

---

## 🎨 PASO 3: Configurar y Ejecutar el Frontend

### 3.1 Navegar al directorio del frontend

```bash
cd ../gestion-estetica
```

### 3.2 Instalar dependencias

```bash
npm install
```

Esto descargará todas las dependencias de Node.js.

### 3.3 Ejecutar el frontend

```bash
npm run dev
```

El frontend estará disponible en: **http://localhost:3000**

### 3.4 Verificar que el frontend funciona

Abre tu navegador en `http://localhost:3000` y deberías ver la página de login.

---

## 🚀 PASO 4: Usar la Aplicación

### 4.1 Iniciar Sesión

1. Abre `http://localhost:3000`
2. Usa las credenciales:
   - **Usuario:** `admin`
   - **Contraseña:** `admin123`

### 4.2 Explorar las Funcionalidades

Una vez logueado, tendrás acceso a:

- **Dashboard:** Estadísticas generales
- **Pacientes:** Crear, editar, buscar y eliminar pacientes
- **Agenda:** Gestionar turnos/tratamientos
- **Pagos:** Registrar y gestionar pagos

---

## 🔍 Verificación Final

### Backend funcionando:
- ✅ Puerto 8080 escuchando
- ✅ Base de datos conectada
- ✅ Migraciones ejecutadas
- ✅ API respondiendo

### Frontend funcionando:
- ✅ Puerto 3000 escuchando
- ✅ Página de login visible
- ✅ Puedes iniciar sesión

---

## 🐛 Solución de Problemas Comunes

### Error: "database does not exist"
**Solución:** Crea la base de datos `cosmetologia_db` (ver PASO 1.2)

### Error: "password authentication failed"
**Solución:** Verifica las credenciales en `application.yml` o cambia la contraseña de PostgreSQL

### Error: "connection refused" (PostgreSQL)
**Solución:** Asegúrate de que PostgreSQL esté corriendo

### Error: "port 8080 already in use"
**Solución:** 
```bash
# Encontrar el proceso
lsof -ti:8080

# Matarlo
kill -9 <PID>
```

### Error: "port 3000 already in use"
**Solución:**
```bash
# Encontrar el proceso
lsof -ti:3000

# Matarlo
kill -9 <PID>
```

### Error: "Cannot find module" (Frontend)
**Solución:**
```bash
cd gestion-estetica
rm -rf node_modules package-lock.json
npm install
```

### Error: "Maven build failed"
**Solución:**
```bash
cd gestion-estetica-backend
mvn clean
mvn install
```

---

## 📝 Notas Importantes

1. **Las migraciones se ejecutan automáticamente** - No necesitas crear las tablas manualmente
2. **El usuario admin se crea automáticamente** - Username: `admin`, Password: `admin123`
3. **CORS está configurado** - El frontend en `localhost:3000` puede comunicarse con el backend en `localhost:8080`
4. **JWT tokens expiran en 24 horas** - Si expira, simplemente vuelve a iniciar sesión

---

## 🎯 Próximos Pasos

Una vez que todo esté funcionando:

1. **Explorar la aplicación** - Crea algunos pacientes, turnos y pagos de prueba
2. **Personalizar** - Modifica estilos, agrega funcionalidades según necesites
3. **Producción** - Cuando estés listo, configura para producción (cambiar JWT secret, credenciales de DB, etc.)

---

## 📚 Documentación Adicional

- **Backend:** Ver `gestion-estetica-backend/README.md`
- **Frontend:** Ver `gestion-estetica/README.md`
- **Base de Datos:** Ver `gestion-estetica-backend/SETUP_DATABASE.md`

¡Listo para empezar! 🎉
