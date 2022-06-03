package com.ileiwe.data.repository;

import com.ileiwe.data.dto.CourseDto;
import com.ileiwe.data.model.Course;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.service.instructor.InstructorService;
import com.ileiwe.utilities.ModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class CourseRepositoryTest {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    InstructorService instructorServiceImpl;

    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    ModelMapper modelMapper;

    @Test
    @Transactional
    void createCourseTest(){
        CourseDto courseDto = new CourseDto ();
        Course course = new Course ();
        assertThat(course.getId ()).isNull ();
        Instructor instructor = new Instructor ();
        instructor.setId ( 4L );
        instructor.setFirstname ( "ken" );
        instructor.setLastname ( "oga" );
        instructorRepository.save ( instructor );
        assertThat ( instructor.getId () ).isEqualTo ( 4L );
//        instructorServiceImpl.findById(instructor.getId ());
        courseDto.setInstructorId ( instructor.getId () );
        courseDto.setTitle ("spring frame work"   );
        courseDto.setDescription ( "bootstrapping spring with springboot" );
        courseDto.setLanguage ( "spring" );
        log.info ( "Before Creating course --> {}", courseDto );
        course = modelMapper.map ( courseDto );
        courseRepository.save ( course );
        assertThat ( course ).isNotNull ();
        assertThat ( courseDto.getInstructorId () ).isEqualTo ( 4L );
        assertThat ( course.getTitle () ).isEqualTo ( "spring frame work"  );
        assertThat ( course.getDescription () ).isEqualTo ("bootstrapping spring with springboot"  );
        assertThat ( course.getLanguage () ).isEqualTo ("spring"  );
        log.info ( "After Creating course --> {}", courseDto );
    }

    @Test
    void deleteCourseByIdTest(){
        CourseDto courseDto = new CourseDto ();
        Course course = new Course ();
        assertThat(course.getId ()).isNull ();
        Instructor instructor = new Instructor ();
        instructor.setId ( 4L );
        instructor.setFirstname ( "ken" );
        instructor.setLastname ( "oga" );
        instructorRepository.save ( instructor );
        assertThat ( instructor.getId () ).isEqualTo ( 4L );
//        instructorServiceImpl.findById(instructor.getId ());
        courseDto.setInstructorId ( instructor.getId () );
        courseDto.setTitle ("spring frame work"   );
        courseDto.setDescription ( "bootstrapping spring with springboot" );
        courseDto.setLanguage ( "spring" );
        course = modelMapper.map ( courseDto );
        courseRepository.save ( course );
        assertThat ( course ).isNotNull ();
        assertThat ( courseDto.getInstructorId () ).isEqualTo ( 4L );
        assertThat ( course.getTitle () ).isEqualTo ( "spring frame work"  );
        assertThat ( course.getDescription () ).isEqualTo ("bootstrapping spring with springboot"  );
        assertThat ( course.getLanguage () ).isEqualTo ("spring"  );
        log.info ( "After deleting course --> {}", course );
        assertThat ( courseRepository.findAll ().size () ).isEqualTo ( 2 );
        courseRepository.deleteById ( course.getId () );
        log.info ( "After deleting course --> {}", course );
        assertThat ( courseRepository.findAll ().size () ).isEqualTo ( 1 );
        assertThat ( course ).isNotNull ();

    }
}
