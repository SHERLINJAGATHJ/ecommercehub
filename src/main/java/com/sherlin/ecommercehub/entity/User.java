package com.sherlin.ecommercehub.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false,length=100)
private String name;

@Column(nullable = false,unique=true,length=255)
private String email;

@Column(nullable=false)
private String password;

@Column(nullable=false)
private LocalDateTime createdAt;

public User(String name,String email,String password){
    this.name=name;
    this.email=email;
    this.password=password;
}

   @PrePersist
   protected void onCreate(){
    createdAt=LocalDateTime.now();
   }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
