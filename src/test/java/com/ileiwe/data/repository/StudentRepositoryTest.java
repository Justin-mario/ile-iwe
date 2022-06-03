package com.ileiwe.data.repository;

import com.ileiwe.data.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import static com.ileiwe.data.model.Gender.FEMALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
public class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @Test
    void saveStudentAsLearningPartyTest(){
        LearningParty user = new LearningParty ("st@mail.com",
                "student123",
                new Authority ( Role.ROLE_STUDENT) );

        Student student = Student.builder ()
                .firstname ( "Frank" )
                .lastname ( "Joe" )
                .learningParty ( user )
                .build ();
        log.info ( "Student before saving --> {}", student );
        studentRepository.save ( student );
        assertThat(student.getId ()).isNotNull ();
        assertThat ( student.getLearningParty ().getId () ).isNotNull ();
        log.info ( "Student after saving --> {}", student );
    }

    @Test
    void studentFirstAndLastNameCannotBeNullTest(){
        LearningParty user = new LearningParty ("student1@mail.com",
                "student123",
                new Authority ( Role.ROLE_STUDENT) );

        Student student = Student.builder ()
                .firstname ( null )
                .lastname ( null )
                .learningParty ( user )
                .build ();
        log.info ( "Student before saving --> {}", student );
        assertThrows ( ConstraintViolationException.class, ()-> studentRepository.save ( student )) ;
        log.info ( "student After saving --> {}", student );
    }

    @Test
    void studentFirstAndLastNameCannotBeEmptyTest(){
        LearningParty user = new LearningParty ("student2@mail.com",
                "trainer123",
                new Authority ( Role.ROLE_STUDENT) );

        Student student = Student.builder ()
                .firstname ( "")
                .lastname ( "" )
                .learningParty ( user )
                .build ();
        log.info ( "Student before saving --> {}", student );
        assertThrows ( ConstraintViolationException.class, ()-> studentRepository.save ( student )) ;
        log.info ( "Student After saving --> {}", student );

    }

    @Test
    @Transactional
//    @Rollback(value = false)
    void updateStudentCredentialTest(){
        LearningParty user = new LearningParty ("ken41@mail.com",
                "ken12344",
                new Authority ( Role.ROLE_STUDENT) );

        Student student = Student.builder ()
                .firstname ( "Kenneth")
                .lastname ( "Beckon" )
                .learningParty ( user )
                .build ();
        log.info ( "Student before saving --> {}", student );
        studentRepository.save ( student );
        assertThat(student.getId ()).isNotNull ();
        assertThat ( student.getLearningParty ().getId () ).isNotNull ();
        assertThat ( student.getFirstname () ).isEqualTo ( "Kenneth" );
        assertThat ( student.getLastname () ).isEqualTo ( "Beckon" );

        Student foundStudent = studentRepository.findById ( student.getId () ).orElse ( null );

        assertThat ( foundStudent ).isNotNull ();
        assertThat ( foundStudent.getGender () ).isNull ();

        foundStudent.setGender ( FEMALE);


        studentRepository.save ( foundStudent );

        assertThat ( foundStudent.getFirstname () ).isEqualTo ( "Kenneth" );
        assertThat ( foundStudent.getLastname () ).isEqualTo ( "Beckon" );
        assertThat ( foundStudent.getGender () ).isEqualTo ( FEMALE);
        log.info ( "Student After saving --> {}", foundStudent );

    }


}
