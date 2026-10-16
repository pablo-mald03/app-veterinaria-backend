-- Seeder: vaccine category
INSERT INTO categories (name, alias, description, status, created_at)
VALUES ('Vacunacion',
        'vacunacion',
        'Categoría principal de vacunas',
        TRUE,
        NOW())
    ON CONFLICT (alias) DO NOTHING;

-- Seeder: vaccine category
INSERT INTO categories (name, alias, description, status, created_at)
VALUES ('Desparasitante',
        'desparasitante',
        'Categoría principal de desparasitantes',
        TRUE,
        NOW())
    ON CONFLICT (alias) DO NOTHING;