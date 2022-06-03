package com.ileiwe.utilities;

import com.ileiwe.data.dto.CourseDto;
import com.ileiwe.data.dto.InstructorPartyDto;
import com.ileiwe.data.model.Course;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.service.course.CourseService;
import com.ileiwe.service.instructor.InstructorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
public class ModelMapper {

    @Autowired
    InstructorService instructorService;

    @Autowired
    CourseService courseServiceImpl;

        public Course map(CourseDto courseDto) {
            Course course = new Course ();
            log.info ( "Course dto --> {}", courseDto );
                Instructor instructor = instructorService.findById ( courseDto.getInstructorId () );
                log.info ( "Instructor --> {}", instructor );
                course.setInstructor ( instructor );
                course.setTitle ( courseDto.getTitle () );
                course.setDateCreated ( LocalDateTime.now () );
                course.setDescription ( courseDto.getDescription () );
                course.setLanguage ( courseDto.getLanguage () );
                return course;
            }


        public  void mapUpdate(CourseDto courseDto, Course course){

            validateUpdateField ( courseDto, course );
        }

        private void validateUpdateField(CourseDto courseDto, Course course){
            if (courseDto.getLanguage () != null || courseDto.getTitle () != null || courseDto.getDescription () != null){
                course.setTitle ( courseDto.getTitle () );
                course.setDescription ( courseDto.getDescription () );
                course.setLanguage ( courseDto.getLanguage () );
                course.setPublished ( courseDto.isPublished () );
                course.setDatePublished ( LocalDateTime.now () );
            }
        }

}
