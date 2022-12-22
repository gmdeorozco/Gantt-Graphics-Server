package com.gantt.gantt.models;

import java.time.LocalDate;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(Include.NON_NULL)

public class ActivityModel extends RepresentationModel< ActivityModel> {


    private Long id;
    private String name;
    private LocalDate soonestInitial; 
    private LocalDate actualInitial;
    private LocalDate soonestEnd;
    private LocalDate actualEnd;
    private int daysRequired;
    private List<ActivityModel> prerequisites;

}
