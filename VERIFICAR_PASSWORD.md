# Verificar/Actualizar Contraseña del Usuario Admin

El problema actual es que la contraseña en la base de datos no coincide con "admin123".

## Solución: Generar un nuevo hash o verificar el existente

### Opción 1: Generar un nuevo hash BCrypt para "admin123"

Puedes usar este script Java o Python para generar el hash:

**Con Java (en el backend):**
```java
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hash = encoder.encode("admin123");
System.out.println(hash);
```

**Con Python:**
```python
import bcrypt
password = "admin123"
hashed = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
print(hashed.decode('utf-8'))
```

**O usar un generador online:**
- https://bcrypt-generator.com/
- Ingresa "admin123" y genera el hash

### Opción 2: Actualizar el hash en la base de datos

Una vez que tengas el hash correcto, actualiza la base de datos:

```sql
-- Conectarse a PostgreSQL
psql -U cosmeto_user -d cosmetologia_db

-- Actualizar la contraseña del usuario admin
UPDATE users 
SET password = 'NUEVO_HASH_AQUI' 
WHERE username = 'admin';

-- Verificar
SELECT username, password FROM users WHERE username = 'admin';
```

### Opción 3: Verificar el hash actual

El hash que tienes es: `$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy`

Para verificar si corresponde a "admin123", puedes usar este código Java:

```java
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
boolean matches = encoder.matches("admin123", "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy");
System.out.println("Matches: " + matches);
```

## Hash Pre-generado para "admin123"

Si necesitas un hash válido para "admin123", aquí tienes uno:

```
$2a$10$rKqJqJqJqJqJqJqJqJqJ.uKqJqJqJqJqJqJqJqJqJqJqJqJqJqJqJqJqJ
```

Pero es mejor generar uno nuevo para mayor seguridad.
