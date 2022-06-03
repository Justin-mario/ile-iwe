package com.ileiwe.service.student;

import com.ileiwe.data.dto.CourseDto;
import com.ileiwe.data.dto.InstructorPartyDto;
import com.ileiwe.data.dto.StudentPartyDto;
import com.ileiwe.data.model.Course;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.model.Student;

import java.util.List;

public interface StudentService {
    Student save(StudentPartyDto student);
    Student findById(Long studentId);
}
