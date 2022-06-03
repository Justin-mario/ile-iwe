package com.ileiwe.controller;

import com.ileiwe.data.dto.CourseDto;
import com.ileiwe.data.model.Course;
import com.ileiwe.exception.CourseNotFoundException;
import com.ileiwe.service.course.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@Slf4j
public class InstructorController {

   CourseService courseServiceImpl;
   @Autowired
   public InstructorController(CourseService courseServiceImpl){
       this.courseServiceImpl = courseServiceImpl;
   }

    @PostMapping("/")
    public Course createCourse(@RequestBody CourseDto course){
       log.info ( "Course in controller --> {}", course );
        return courseServiceImpl.save ( course );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteACourse(@PathVariable Long id){
        courseServiceImpl.deleteById ( id );
        return ResponseEntity.noContent ().build ();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getACourse(@PathVariable Long id){
       Course course;
       try{
           course = courseServiceImpl.findById ( id );

       }catch (CourseNotFoundException exception){
           log.info ( exception.getMessage () );
           return ResponseEntity.badRequest ().body ( exception.getMessage () );
       }
       return ResponseEntity.ok ().body ( course );
    }

    @PatchMapping("/")
    public ResponseEntity<?> updateCourse(@RequestBody CourseDto courseDto){
       Course course;
       try{
           course = courseServiceImpl.update ( courseDto );
           return ResponseEntity.ok ().body ( course );

       }catch (CourseNotFoundException ex){
           log.info ( ex.getMessage () );
           return ResponseEntity.badRequest ().body ( ex.getMessage () );
       }

    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCourse(){
           List<Course> course = courseServiceImpl.findAllCourse ();

           return ResponseEntity.ok ().body ( course );
  }

}
