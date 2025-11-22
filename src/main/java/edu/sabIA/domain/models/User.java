package edu.sabIA.domain.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    private UUID id;
    private String username;
    private String name;
    private String email;
    private String passwordHash;

    public User(){}
    public User(String username, String name, String email, String passwordHash) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }
}
