package com.ileiwe.controller;

import com.ileiwe.data.dto.InstructorPartyDto;
import com.ileiwe.data.dto.StudentPartyDto;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.model.Student;
import com.ileiwe.service.exception.UserAlreadyExistsException;
import com.ileiwe.service.instructor.InstructorService;
import com.ileiwe.service.student.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class RegistrationController {

    @Autowired
    InstructorService instructorServiceImpl;

    @Autowired
    StudentService studentServiceImpl;

    @PostMapping("/instructor")
    public ResponseEntity<?> registerAsInstructor(@RequestBody InstructorPartyDto instructorPartyDto){
        log.info ( "Instructor Object --> {}", instructorPartyDto );
        try {
            return
                    ResponseEntity.ok ()
                            .body ( instructorServiceImpl.save ( instructorPartyDto ) );
        } catch (UserAlreadyExistsException e) {

           return ResponseEntity.badRequest ().body ( e.getMessage () );
        }
    }
    @GetMapping("/instructor/{id}")
    public ResponseEntity<?> findAnInstructor(@PathVariable Long id){
        Instructor instructor;
        instructor = instructorServiceImpl.findById ( id );
        return ResponseEntity.ok ().body ( instructor );
    }

    @PostMapping("/student")
    public ResponseEntity<?> registerAsStudent(@RequestBody StudentPartyDto studentPartyDto){
        log.info ( "Student Object --> {}", studentPartyDto );
        return
                ResponseEntity.ok ()
                        .body ( studentServiceImpl.save ( studentPartyDto ) );
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> getAStudent(@PathVariable Long id){
        Student student;
       student = studentServiceImpl.findById ( id );
        return ResponseEntity.ok ().body ( student );
    }
}
