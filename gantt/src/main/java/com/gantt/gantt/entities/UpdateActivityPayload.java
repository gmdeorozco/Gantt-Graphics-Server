package com.gantt.gantt.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor  @Data @AllArgsConstructor
public class UpdateActivityPayload {

    private Long id;

    private String name;

    private LocalDate soonestInitial; 
    private LocalDate actualInitial;
    private LocalDate soonestEnd;
    private LocalDate actualEnd;
    private int daysRequired;
    private List<Long> prerequisites = new ArrayList<>();

}
