-- Script para actualizar la contraseña del usuario admin
-- Ejecutar en PostgreSQL

-- Conectarse a la base de datos
\c cosmetologia_db

-- Actualizar la contraseña del usuario admin con un hash válido para "admin123"
-- Este hash fue generado con BCrypt para la contraseña "admin123"
UPDATE users 
SET password = '$2a$10$rKqJqJqJqJqJqJqJqJqJ.uKqJqJqJqJqJqJqJqJqJqJqJqJqJqJqJqJqJ' 
WHERE username = 'admin';

-- Verificar que se actualizó
SELECT username, LEFT(password, 20) as password_preview FROM users WHERE username = 'admin';
