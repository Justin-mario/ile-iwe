package com.ileiwe.data.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String firstname;
    @NotBlank @NotNull
    @Column(nullable = false)
    private String lastname;
    private LocalDate dob;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade = CascadeType.PERSIST)
    private LearningParty learningParty;

    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Course> enrolledCourses;

}
