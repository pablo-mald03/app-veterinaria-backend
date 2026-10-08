CREATE TABLE appointments (
    id BIGSERIAL PRIMARY KEY,
    pet_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    room_id BIGINT,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    description VARCHAR(255) NOT NULL,
    diagnosis TEXT,
    status VARCHAR(50) DEFAULT 'SCHEDULED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_appointments_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_appointments_pet FOREIGN KEY (pet_id) REFERENCES  pets (id) ON DELETE CASCADE,
    CONSTRAINT fk_appointments_room FOREIGN KEY (room_id) REFERENCES rooms (id)
);

CREATE TABLE appointment_clients (
     appointment_id BIGINT NOT NULL,
     client_id BIGINT NOT NULL,
     user_id BIGINT NOT NULL,
     PRIMARY KEY (appointment_id, client_id, user_id),
     CONSTRAINT fk_ac_appointment FOREIGN KEY (appointment_id) REFERENCES appointments (id) ON DELETE CASCADE,
     CONSTRAINT fk_ac_client FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE,
     CONSTRAINT fk_ac_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);