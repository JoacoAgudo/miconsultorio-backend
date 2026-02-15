-- Script para otorgar permisos al usuario cosmeto_user
-- Ejecutar como superusuario (postgres)

-- Conectarse a la base de datos
\c cosmetologia_db

-- Otorgar todos los permisos al usuario cosmeto_user
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO cosmeto_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO cosmeto_user;

-- Otorgar permisos para tablas futuras
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO cosmeto_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO cosmeto_user;

-- Si las tablas aún no existen, otorgar permisos para crearlas
GRANT CREATE ON SCHEMA public TO cosmeto_user;

-- Verificar permisos
\dp
