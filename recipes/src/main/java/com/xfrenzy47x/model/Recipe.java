package com.xfrenzy47x.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RECIPE")
public class Recipe {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotEmpty
    @Column(name = "NAME")
    private String name;

    @NotEmpty
    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "DATE")
    private LocalDateTime date;

    @NotEmpty
    @Column(name = "DESCRIPTION")
    private String description;

    @NotEmpty
    @Size(min = 1)
    @Column(name = "INGREDIENTS")
    private String[] ingredients;

    @NotEmpty
    @Size(min = 1)
    @Column(name = "DIRECTIONS")
    private String[] directions;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;
}
