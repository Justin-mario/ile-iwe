package com.ileiwe.service.instructor;

import com.ileiwe.data.dto.InstructorPartyDto;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.service.exception.UserAlreadyExistsException;

public interface InstructorService {
    Instructor save(InstructorPartyDto instructor) throws UserAlreadyExistsException;
    Instructor findById(Long instructorId);

}
