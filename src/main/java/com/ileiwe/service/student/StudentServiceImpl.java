package com.ileiwe.service.student;

import com.ileiwe.data.dto.StudentPartyDto;
import com.ileiwe.data.model.*;
import com.ileiwe.data.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public Student save(StudentPartyDto studentDto) {
        if (studentDto == null) {
            throw new IllegalArgumentException ("Student cannot be null ");

        }
        LearningParty learningParty = new LearningParty (studentDto.getEmail (), passwordEncoder.encode ( studentDto.getPassword ()),
                new Authority ( Role.ROLE_STUDENT) );
        Student student = Student.builder ()
                .firstname ( studentDto.getFirstname () )
                .lastname ( studentDto.getLastname () )
                .learningParty ( learningParty )
                .build ();
        return studentRepository.save ( student );

    }

    @Override
    public Student findById(Long studentId) {
        return studentRepository.findById ( studentId ).orElse ( null );
    }
}
