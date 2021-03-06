package com.ileiwe.data.model;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Instructor extends RepresentationModel<Instructor> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @NotNull
    @Column(nullable = false)
    private String firstname;
    @NotBlank @NotNull
    @Column(nullable = false)
    private String lastname;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String specialization;
    @Column(length = 1000)
    private String bio;


    @OneToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    private LearningParty learningParty;
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Course> courses;


}
