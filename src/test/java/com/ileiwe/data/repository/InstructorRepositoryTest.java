package com.ileiwe.data.repository;

import com.ileiwe.data.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ileiwe.data.model.Gender.FEMALE;
import static com.ileiwe.data.model.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql("/db/insert.sql")
class InstructorRepositoryTest {
    @Autowired
    InstructorRepository instructorRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void saveInstructorAsLearningPartyTest(){
        LearningParty user = new LearningParty ("trainer@mail.com",
                "trainer123",
                new Authority ( Role.ROLE_INSTRUCTOR) );

        Instructor instructor = Instructor.builder ()
                .firstname ( "John" )
                .lastname ( "Alao" )
                .learningParty ( user )
                .build ();
        log.info ( "Instructor before saving --> {}", instructor );
        instructorRepository.save ( instructor );
        assertThat(instructor.getId ()).isNotNull ();
        assertThat ( instructor.getLearningParty ().getId () ).isNotNull ();
        log.info ( "Instructor after saving --> {}", instructor );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void instructorFirstAndLastNameCannotBeNullTest(){
        LearningParty user = new LearningParty ("trainer@mail.com",
                "trainer123",
                new Authority ( Role.ROLE_INSTRUCTOR) );

        Instructor instructor = Instructor.builder ()
                .firstname ( null )
                .lastname ( null )
                .learningParty ( user )
                .build ();
        log.info ( "Instructor before saving --> {}", instructor );
       assertThrows ( ConstraintViolationException.class, ()-> instructorRepository.save ( instructor )) ;
        log.info ( "Instructor After saving --> {}", instructor );
    }

    @Test
    void instructorFirstAndLastNameCannotBeEmptyTest(){
        LearningParty user = new LearningParty ("trainer@mail.com",
                "trainer123",
                new Authority ( Role.ROLE_INSTRUCTOR) );

        Instructor instructor = Instructor.builder ()
                .firstname ( "")
                .lastname ( "" )
                .learningParty ( user )
                .build ();
        log.info ( "Instructor before saving --> {}", instructor );
        assertThrows ( ConstraintViolationException.class, ()-> instructorRepository.save ( instructor )) ;
        log.info ( "Instructor After saving --> {}", instructor );

    }

    @Test
    @Transactional
    @Rollback(value = false)
    void updateInstructorCredentialTest(){
        LearningParty user = new LearningParty ("trainer@mail.com",
                "trainer123",
                new Authority ( Role.ROLE_INSTRUCTOR) );

        Instructor instructor = Instructor.builder ()
                .firstname ( "Ken")
                .lastname ( "Beck" )
                .learningParty ( user )
                .build ();
        log.info ( "Instructor before saving --> {}", instructor );
        instructorRepository.save ( instructor );
        assertThat(instructor.getId ()).isNotNull ();
        assertThat ( instructor.getLearningParty ().getId () ).isNotNull ();
        assertThat ( instructor.getFirstname () ).isEqualTo ( "Ken" );
        assertThat ( instructor.getLastname () ).isEqualTo ( "Beck" );

        Instructor foundInstructor = instructorRepository.findById ( instructor.getId () ).orElse ( null );

        assertThat ( foundInstructor ).isNotNull ();
        assertThat ( foundInstructor.getBio () ).isNull ();
        assertThat ( foundInstructor.getGender () ).isNull ();
        assertThat ( foundInstructor.getSpecialization () ).isNull ();
        foundInstructor.setBio ( "I am a natural born and passionate teacher" );
        foundInstructor.setGender ( FEMALE);
        foundInstructor.setSpecialization ( "Java" );

        instructorRepository.save ( foundInstructor );

        assertThat ( foundInstructor.getFirstname () ).isEqualTo ( "Ken" );
        assertThat ( foundInstructor.getLastname () ).isEqualTo ( "Beck" );
        assertThat ( foundInstructor.getGender () ).isEqualTo ( FEMALE);
        assertThat ( foundInstructor.getSpecialization () ).isEqualTo ( "Java" );
        assertThat ( foundInstructor.getBio () ).isEqualTo ( "I am a natural born and passionate teacher" );
        log.info ( "Instructor After saving --> {}", foundInstructor );

    }

//    @Test
//    @Transactional
//    @Rollback(value = false)
//    void updateInstructorAsLearningPartyTest(){
//        LearningParty user = new LearningParty ("trainer@mail.com",
//                "trainer123",
//                new Authority ( Role.ROLE_INSTRUCTOR) );
//
//        Instructor instructor = Instructor.builder ()
//                .firstname ( "John" )
//                .lastname ( "Alao" )
//                .learningParty ( user )
//                .build ();
//        log.info ( "Instructor before saving --> {}", instructor );
//        instructorRepository.save ( instructor );
//        assertThat(instructor.getId ()).isNotNull ();
//        assertThat ( instructor.getLearningParty ().getId () ).isNotNull ();
//        assertThat ( instructor.getFirstname () ).isEqualTo ( "John" );
//        assertThat ( instructor.getLastname () ).isEqualTo ( "Alao" );
//        log.info ( "Instructor after saving --> {}", instructor );
//        instructor.setGender ( MALE );
//        instructor.setSpecialization ( "Database" );
//        instructor.setBio ( "Passionate about impacting knowledge" );
//        Course course = new Course ();
//        course.setInstructor ( instructor );
//        course.setDateCreated ( LocalDateTime.now () );
//        course.setTitle ( "Programming With Python" );
//        assertThat ( instructor.getCourses ().get ( 0 ) ).isEqualTo ( course );
//        assertThat ( instructor.getLearningParty ().getId () ).isNotNull ();
//        assertThat ( instructor.getFirstname () ).isEqualTo ( "John" );
//        assertThat ( instructor.getLastname () ).isEqualTo ( "Alao" );
//        assertThat (  instructor.getGender ()).isEqualTo ( MALE );
//        assertThat ( instructor.getBio () ).isEqualTo (  "Passionate about impacting knowledge");
//        assertThat ( instructor.getSpecialization () ).isEqualTo ( "Database" );
//    }

}