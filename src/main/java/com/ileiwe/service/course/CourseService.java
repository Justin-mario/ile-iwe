package com.ileiwe.service.course;

import com.ileiwe.data.dto.CourseDto;
import com.ileiwe.data.model.Course;


import java.util.List;

public interface CourseService {
    Course save(CourseDto course);
    Course update(CourseDto course);
    Course findById(Long id);
    void deleteById(Long id);
    List<Course> findAllCourse();


}
