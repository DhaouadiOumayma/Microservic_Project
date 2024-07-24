package com.example.club.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    long id;

    String nom;
    String prenom;
    Boolean status;
    String email;
    String password;
}