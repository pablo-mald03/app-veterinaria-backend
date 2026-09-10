-- =========================================================
-- 1. ROLES
-- =========================================================
INSERT INTO roles (alias, name, description)
VALUES ('admin', 'ADMIN', 'Acceso total al sistema'),
       ('recepcionista', 'RECEPCIONISTA', 'Gestion de clientes, mascotas, citas y facturacion'),
       ('veterinario', 'VETERINARIO', 'Gestion clinica: mascotas, citas y vacunacion');

-- =========================================================
-- 2. PERMISSIONS (module, action, description)
-- =========================================================
INSERT INTO permissions (module, action, description)
VALUES ('usuarios', 'ver', 'Ver usuarios'),
       ('usuarios', 'crear', 'Crear usuarios'),
       ('usuarios', 'editar', 'Editar usuarios'),
       ('usuarios', 'eliminar', 'Eliminar usuarios'),

       ('roles', 'ver', 'Ver roles'),
       ('roles', 'crear', 'Crear roles'),
       ('roles', 'editar', 'Editar roles'),
       ('roles', 'eliminar', 'Eliminar roles'),

       ('permisos', 'ver', 'Ver permisos'),
       ('permisos', 'asignar', 'Asignar permisos a roles'),

       ('clientes', 'ver', 'Ver clientes'),
       ('clientes', 'crear', 'Crear clientes'),
       ('clientes', 'editar', 'Editar clientes'),
       ('clientes', 'eliminar', 'Eliminar clientes'),

       ('mascotas', 'ver', 'Ver mascotas'),
       ('mascotas', 'crear', 'Crear mascotas'),
       ('mascotas', 'editar', 'Editar mascotas'),
       ('mascotas', 'eliminar', 'Eliminar mascotas'),

       ('citas', 'ver', 'Ver citas'),
       ('citas', 'crear', 'Crear citas'),
       ('citas', 'editar', 'Editar citas'),
       ('citas', 'cancelar', 'Cancelar citas'),
       ('citas', 'eliminar', 'Eliminar citas'),

       ('salas', 'ver', 'Ver salas'),
       ('salas', 'crear', 'Crear salas'),
       ('salas', 'editar', 'Editar salas'),
       ('salas', 'eliminar', 'Eliminar salas'),

       ('inventario', 'ver', 'Ver inventario'),
       ('inventario', 'crear', 'Crear items de inventario'),
       ('inventario', 'editar', 'Editar items de inventario'),
       ('inventario', 'eliminar', 'Eliminar items de inventario'),

       ('categorias', 'ver', 'Ver categorias'),
       ('categorias', 'crear', 'Crear categorias'),
       ('categorias', 'editar', 'Editar categorias'),
       ('categorias', 'eliminar', 'Eliminar categorias'),

       ('compras', 'ver', 'Ver compras'),
       ('compras', 'crear', 'Crear compras'),
       ('compras', 'editar', 'Editar compras'),
       ('compras', 'eliminar', 'Eliminar compras'),

       ('facturacion', 'ver', 'Ver facturas'),
       ('facturacion', 'crear', 'Crear facturas'),
       ('facturacion', 'anular', 'Anular facturas'),

       ('pagos', 'ver', 'Ver pagos'),
       ('pagos', 'registrar', 'Registrar pagos'),
       ('pagos', 'anular', 'Anular pagos'),

       ('vacunacion', 'ver', 'Ver vacunaciones'),
       ('vacunacion', 'crear', 'Registrar vacunaciones'),
       ('vacunacion', 'editar', 'Editar vacunaciones'),
       ('vacunacion', 'eliminar', 'Eliminar vacunaciones'),

       ('logs', 'ver', 'Ver logs de auditoria');

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
INSERT INTO users (identification, name, first_name, password, email, phone, user_registry)
VALUES ('73891720200', 'Pablo', 'Admin',
        '1d3988e2cba831131d45c039a681c7ba5a1b1fa4215174ba8a13d63537367c2fcc3bec84b83ed9b78b2f0216250ea5be',
        'pabloadmin@gmail.com', '58143215', 'pabloadmin'),
       ('63892120211', 'Cristian', 'Admin',
        'c57b95d5ec5c7f5e19926e2317062f8b01ef2ba7feeb7a09234a13d232be445e402dc84413dcd10144c4965122830f9d',
        'cristianadmin@gmail.com', '59113213', 'cristianadmin');

-- =========================================================
-- 5. GRANT ROLE ADMINS FOR USERS
-- =========================================================
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         CROSS JOIN roles r
WHERE u.email IN ('pabloadmin@gmail.com', 'cristianadmin@gmail.com')
  AND r.name = 'ADMIN';