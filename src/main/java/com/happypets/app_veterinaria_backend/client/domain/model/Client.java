package com.happypets.app_veterinaria_backend.client.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    private Long id;
    private String dpi;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
