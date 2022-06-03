package com.ileiwe.service.course;

import com.ileiwe.data.dto.CourseDto;
import com.ileiwe.data.model.Course;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.repository.CourseRepository;
import com.ileiwe.exception.CourseNotFoundException;
import com.ileiwe.service.instructor.InstructorService;
import com.ileiwe.utilities.ModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService{
    CourseRepository courseRepository;

    @Autowired
    InstructorService instructorServiceImpl;

    @Autowired
    ModelMapper modelMapper;

    Course course;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    @Override
    public Course save(CourseDto newCourse) {
        if (newCourse == null){
            throw new NullPointerException ("No course to Create");
        }
        log.info("new course --> {}", newCourse);
        Course course =  modelMapper.map ( newCourse );
        return courseRepository.save ( course );

    }

    @Override
    public Course update(CourseDto course) {
        Optional<Course> result = courseRepository.findById ( course.getId () );
        log.info("Course DTO before updating --> {}",course);
        if (result.isEmpty ()) throw new CourseNotFoundException ( "Course could not be found" );
        Course updatedCourse = result.get ();
        log.info("Update course before updating --> {}",updatedCourse);
        modelMapper.mapUpdate ( course, updatedCourse );
        log.info("Update course after updating --> {}",updatedCourse);

        return courseRepository.save ( updatedCourse );
    }



    @Override
    public Course findById(Long id) {
        Optional<Course>  result = courseRepository.findById ( id);
        if (result.isPresent ()){
            if (result.get ().isPublished ()){
                return courseRepository.findById ( id ).orElse ( null );}
            else{
                throw new CourseNotFoundException ( "course can not be found" );
        }
        }
        throw new CourseNotFoundException ( "course can not be found" );
    }


    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById ( id );

    }

    @Override
    public List<Course> findAllCourse() {
        List<Course> courses = courseRepository.findAll ();
        List<Course> foundCourse = new ArrayList<> ();
        for (Course allCourse: courses) {
            if (allCourse.isPublished ()){
                foundCourse.add ( allCourse );
            }

        }
        return foundCourse ;

    }
}
