package com.behl.salamanca.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import javax.persistence.*;


@Entity
@Table(name = "book")
@Data
public class Book implements Serializable {

	private static final long serialVersionUID = -4579057256750790460L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String author;

    @PrePersist
    void setUp() {
        this.id = UUID.randomUUID();
    }
}
