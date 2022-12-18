package com.gantt.gantt.entities;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class NewActivityPayload {
    private String name;
    private int daysRequired;
    private List<Long> prerequisites;
    private LocalDate initialDate;
}
