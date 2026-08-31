package com.happypets.app_veterinaria_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column (nullable = false, length = 13)
    private String dpi;

    @NotBlank (message = "El nombre es obligatorio")
    @Column (nullable = false, length = 50)
    private String firstName;

    @NotBlank (message = "El apellido es obligatorio")
    @Column (nullable = false, length = 50)
    private String lastName;

    @NotBlank (message = "El telefono es obligatorio")
    @Size(min = 8, max = 15, message = "El telefono debe de tener entre 8 y 15 caracteres")
    @Column (nullable = false, length = 15)
    private String phone;

    @Column(length = 150)
    private String address;

    @Column(length = 150)
    private String email;

}
