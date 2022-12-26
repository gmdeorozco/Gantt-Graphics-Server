package com.gantt.gantt.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gantt.gantt.entities.Activity;

public interface ActivityRepository extends CrudRepository<Activity, Long>{

    @Query("SELECT u FROM Activity u ORDER BY name")
    Collection<Activity> findAllActivitiesAlphabetical();

    @Query("SELECT u FROM Activity u WHERE u.id = ?1 and u.name = ?2")
    Optional <Activity> findActivityByIdAndName(Long id, String name);

    
    
}
