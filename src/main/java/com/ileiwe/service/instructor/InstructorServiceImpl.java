package com.ileiwe.service.instructor;

import com.ileiwe.data.dto.InstructorPartyDto;
import com.ileiwe.data.model.*;
import com.ileiwe.data.repository.InstructorRepository;
import com.ileiwe.data.repository.LearningPartyRepository;
import com.ileiwe.service.events.OnRegistrationCompleteEvent;
import com.ileiwe.service.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImpl implements InstructorService{

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    LearningPartyRepository learningPartyRepository;

    @Override
    public Instructor save(InstructorPartyDto instructorDto) throws UserAlreadyExistsException {
        if (instructorDto == null) {
            throw new IllegalArgumentException ("Instructor cannot be null ");

        }

       if (learningPartyRepository.findByEmail ( instructorDto.getEmail () ) == null){


        LearningParty learningParty = new LearningParty (instructorDto.getEmail (), passwordEncoder.encode ( instructorDto.getPassword ()),
                new Authority ( Role.ROLE_INSTRUCTOR) );
        Instructor instructor = Instructor.builder ()
                .firstname ( instructorDto.getFirstname () )
                .lastname ( instructorDto.getLastname () )
                .learningParty ( learningParty )
                .build ();
        eventPublisher.publishEvent (new  OnRegistrationCompleteEvent(learningParty) );
        return instructorRepository.save ( instructor );
       } else{
            throw new UserAlreadyExistsException ("User with email" + instructorDto.getEmail () + "already exist");
        }
    }

    @Override
    public Instructor findById(Long instructorId) {
        return instructorRepository.findById ( instructorId ).orElse ( null );
    }
}
