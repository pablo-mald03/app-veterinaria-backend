-- Seeder: vaccine category
INSERT INTO categories (name, alias, description, status, created_at)
VALUES ('Vacunación',
        'vacunacion',
        'Categoría principal de vacunas',
        TRUE,
        NOW())
    ON CONFLICT (alias) DO NOTHING;