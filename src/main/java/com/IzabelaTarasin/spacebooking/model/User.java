package com.IzabelaTarasin.spacebooking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String telephoneNumber;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //i tak mam dto bez hasla, ale dobra praktyka jako zabezpieczenie
    //gdyby kiedyś zwrócić encję User —
    // przy serializacji do JSON hasło nie trafia do odpowiedzi (WRITE_ONLY).
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
}
