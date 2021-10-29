package com.ileiwe.data.repository;

import com.ileiwe.data.model.Authority;
import com.ileiwe.data.model.LearningParty;
import com.ileiwe.data.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql("/db/insert.sql")
class LearningPartyRepositoryTest {

    @Autowired
    LearningPartyRepository learningPartyRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Transactional
    @Rollback(value = false)

    void createLearningPartyWithStudentRoleTest(){
        LearningParty learningUser = new LearningParty ("gozie@yahoo.com",
                "gozie123",
                new Authority ( Role.ROLE_STUDENT) );

        log.info ( "Before saving --> {}", learningUser );
        learningPartyRepository.save ( learningUser );
        assertThat(learningUser.getId ()).isNotNull ();
        assertThat ( learningUser.getEmail () ).isEqualTo ( "gozie@yahoo.com" );
        assertThat ( learningUser.getAuthorities ().get ( 0 ).getAuthority () ).isEqualTo ( Role.ROLE_STUDENT );
        assertThat ( learningUser.getAuthorities ().get ( 0 ).getId () ).isNotNull ();
        log.info ( "After Saving --> {}", learningUser );

    }
    @Test
    void LearningPartyCanNotHaveSameEmailTest(){
        LearningParty learningUser = new LearningParty ("gozie@yahoo.com",
                "gozie123",
                new Authority ( Role.ROLE_STUDENT) );
        LearningParty learningUser1 = new LearningParty ("gozie@yahoo.com",
                "gozie123",
                new Authority ( Role.ROLE_STUDENT) );
        log.info ( "Before saving --> {}", learningUser );
        learningPartyRepository.save ( learningUser );
       assertThrows ( DataIntegrityViolationException.class, ()->learningPartyRepository.save ( learningUser1 ) ) ;
        log.info ( "After Saving --> {}", learningUser );

    }

    @Test
    void LearningPartyFieldCannotBeNullTest(){

        LearningParty learningUser1 = new LearningParty (null,
                null,
                new Authority ( Role.ROLE_STUDENT) );
        log.info ( "Before saving --> {}", learningUser1 );
        assertThrows ( ConstraintViolationException.class, ()->learningPartyRepository.save ( learningUser1 ) ) ;
        log.info ( "After Saving --> {}", learningUser1 );}

    @Test
    void LearningPartyFieldCannotBeEmptyTest(){

        LearningParty learningUser1 = new LearningParty (" ",
              "",
              new Authority ( Role.ROLE_STUDENT) );
       assertThrows ( ConstraintViolationException.class, ()-> learningPartyRepository.save ( learningUser1 )) ;

    }


    @AfterEach
    void tearDown() {
    }

}