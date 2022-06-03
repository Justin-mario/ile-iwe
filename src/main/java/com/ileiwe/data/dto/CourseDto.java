package com.ileiwe.data.dto;


import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CourseDto {
    private Long id;
    private Long instructorId;
    private String title;
    private String description;
    private String language;
//    private LocalDateTime updatedAt;
    private boolean isPublished;
    private InstructorPartyDto instructor;
}
