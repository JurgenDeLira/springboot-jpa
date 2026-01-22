package com.jorge.jpa.springboot_jpa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "persons")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    @Column(name = "programming_language")
    private String programmingLanguage;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Person(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public Person(Long id, String name, String lastname, String programmingLanguage) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.programmingLanguage = programmingLanguage;
    }

    @PrePersist
    public void prePersist() {
        System.out.println("evento del ciclo de vida del entity pre-persist");
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        System.out.println("evento del ciclo de vida del objeto entity pre-update");
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return
            "[id=" + id + ", name=" + name + ", lastname=" + lastname + ", programmingLanguage=" + programmingLanguage +
                    ",createAt= " + createAt + ", updatedAt=" + updatedAt + "]";
    }
}
