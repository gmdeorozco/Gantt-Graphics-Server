package com.gantt.gantt.repository;

import org.springframework.data.repository.CrudRepository;

import com.gantt.gantt.entities.Activity;

public interface ActivityRepository extends CrudRepository<Activity, Long>{
    
}
