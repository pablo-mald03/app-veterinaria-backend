-- =========================================================
-- 1. ROLES
-- =========================================================
INSERT INTO roles (alias, name, description, created_at)
VALUES ('admin', 'ADMIN', 'Acceso total al sistema', NOW()),
       ('recepcionista', 'RECEPCIONISTA', 'Gestion de clientes, mascotas, citas y facturacion', NOW()),
       ('veterinario', 'VETERINARIO', 'Gestion clinica: mascotas, citas y vacunacion', NOW());

-- =========================================================
-- 2. PERMISSIONS (module, action, description)
-- =========================================================
INSERT INTO permissions (module, action, description, created_at)
VALUES ('usuarios', 'ver', 'Ver usuarios', NOW()),
       ('usuarios', 'crear', 'Crear usuarios', NOW()),
       ('usuarios', 'editar', 'Editar usuarios', NOW()),
       ('usuarios', 'eliminar', 'Eliminar usuarios', NOW()),

       ('roles', 'ver', 'Ver roles', NOW()),
       ('roles', 'crear', 'Crear roles', NOW()),
       ('roles', 'editar', 'Editar roles', NOW()),
       ('roles', 'eliminar', 'Eliminar roles', NOW()),

       ('permisos', 'ver', 'Ver permisos', NOW()),
       ('permisos', 'asignar', 'Asignar permisos a roles', NOW()),

       ('clientes', 'ver', 'Ver clientes', NOW()),
       ('clientes', 'crear', 'Crear clientes', NOW()),
       ('clientes', 'editar', 'Editar clientes', NOW()),
       ('clientes', 'eliminar', 'Eliminar clientes', NOW()),

       ('mascotas', 'ver', 'Ver mascotas', NOW()),
       ('mascotas', 'crear', 'Crear mascotas', NOW()),
       ('mascotas', 'editar', 'Editar mascotas', NOW()),
       ('mascotas', 'eliminar', 'Eliminar mascotas', NOW()),

       ('citas', 'ver', 'Ver citas', NOW()),
       ('citas', 'crear', 'Crear citas', NOW()),
       ('citas', 'editar', 'Editar citas', NOW()),
       ('citas', 'cancelar', 'Cancelar citas', NOW()),
       ('citas', 'eliminar', 'Eliminar citas', NOW()),

       ('salas', 'ver', 'Ver salas', NOW()),
       ('salas', 'crear', 'Crear salas', NOW()),
       ('salas', 'editar', 'Editar salas', NOW()),
       ('salas', 'eliminar', 'Eliminar salas', NOW()),

       ('inventario', 'ver', 'Ver inventario', NOW()),
       ('inventario', 'crear', 'Crear items de inventario', NOW()),
       ('inventario', 'editar', 'Editar items de inventario', NOW()),
       ('inventario', 'eliminar', 'Eliminar items de inventario', NOW()),

       ('categorias', 'ver', 'Ver categorias', NOW()),
       ('categorias', 'crear', 'Crear categorias', NOW()),
       ('categorias', 'editar', 'Editar categorias', NOW()),
       ('categorias', 'eliminar', 'Eliminar categorias', NOW()),

       ('compras', 'ver', 'Ver compras', NOW()),
       ('compras', 'crear', 'Crear compras', NOW()),
       ('compras', 'editar', 'Editar compras', NOW()),
       ('compras', 'eliminar', 'Eliminar compras', NOW()),

       ('facturacion', 'ver', 'Ver facturas', NOW()),
       ('facturacion', 'crear', 'Crear facturas', NOW()),
       ('facturacion', 'anular', 'Anular facturas', NOW()),

       ('pagos', 'ver', 'Ver pagos', NOW()),
       ('pagos', 'registrar', 'Registrar pagos', NOW()),
       ('pagos', 'anular', 'Anular pagos', NOW()),

       ('vacunacion', 'ver', 'Ver vacunaciones', NOW()),
       ('vacunacion', 'crear', 'Registrar vacunaciones', NOW()),
       ('vacunacion', 'editar', 'Editar vacunaciones', NOW()),
       ('vacunacion', 'eliminar', 'Eliminar vacunaciones', NOW()),

       ('logs', 'ver', 'Ver logs de auditoria', NOW());

-- =========================================================
-- 3. ROLE_PERMISSIONS MAPPING
-- =========================================================

-- ADMIN: grant all permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
         CROSS JOIN permissions p
WHERE r.name = 'ADMIN';

-- RECEPTIONIST
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
         JOIN permissions p ON (p.module, p.action) IN (
                                                        ('clientes', 'ver'), ('clientes', 'crear'),
                                                        ('clientes', 'editar'),
                                                        ('mascotas', 'ver'), ('mascotas', 'crear'),
                                                        ('mascotas', 'editar'),
                                                        ('citas', 'ver'), ('citas', 'crear'), ('citas', 'editar'),
                                                        ('citas', 'cancelar'),
                                                        ('salas', 'ver'),
                                                        ('inventario', 'ver'),
                                                        ('facturacion', 'ver'), ('facturacion', 'crear'),
                                                        ('pagos', 'ver'), ('pagos', 'registrar'),
                                                        ('vacunacion', 'ver')
    )
WHERE r.name = 'RECEPCIONISTA';

-- VETERINARY
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
         JOIN permissions p ON (p.module, p.action) IN (
                                                        ('clientes', 'ver'),
                                                        ('mascotas', 'ver'), ('mascotas', 'editar'),
                                                        ('citas', 'ver'), ('citas', 'editar'), ('citas', 'cancelar'),
                                                        ('salas', 'ver'),
                                                        ('inventario', 'ver'),
                                                        ('vacunacion', 'ver'), ('vacunacion', 'crear'),
                                                        ('vacunacion', 'editar')
    )
WHERE r.name = 'VETERINARIO';

-- =========================================================
-- 4. ADMIN INITIAL USERS
-- =========================================================
INSERT INTO users (identification, name, first_name, password, email, phone, user_registry, status, created_at)
VALUES ('73891720200', 'Pablo', 'Admin',
        '1d3988e2cba831131d45c039a681c7ba5a1b1fa4215174ba8a13d63537367c2fcc3bec84b83ed9b78b2f0216250ea5be',
        'pabloadmin@gmail.com', '58143215', 'pabloadmin', TRUE, NOW()),
       ('63892120211', 'Cristian', 'Admin',
        'c57b95d5ec5c7f5e19926e2317062f8b01ef2ba7feeb7a09234a13d232be445e402dc84413dcd10144c4965122830f9d',
        'cristianadmin@gmail.com', '59113213', 'cristianadmin', TRUE, NOW());

-- =========================================================
-- 5. GRANT ROLE ADMINS FOR USERS
-- =========================================================
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         CROSS JOIN roles r
WHERE u.email IN ('pabloadmin@gmail.com', 'cristianadmin@gmail.com')
  AND r.name = 'ADMIN';