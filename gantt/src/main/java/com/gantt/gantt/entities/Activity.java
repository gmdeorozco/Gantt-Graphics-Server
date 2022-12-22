package com.gantt.gantt.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor  @Data @AllArgsConstructor @Builder
public class Activity {

    @Id @GeneratedValue
    private Long id;

    @Column( nullable = false, unique = true )
    private String name;

    private LocalDate soonestInitial; 
    private LocalDate actualInitial;
    private LocalDate soonestEnd;
    private LocalDate actualEnd;
    private int daysRequired;

    @ManyToMany
    private List < Activity > prerequisites;

   
}
