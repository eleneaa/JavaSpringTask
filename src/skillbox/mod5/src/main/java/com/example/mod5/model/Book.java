package com.example.mod5.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "books")
@FieldNameConstants
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private String author;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

}