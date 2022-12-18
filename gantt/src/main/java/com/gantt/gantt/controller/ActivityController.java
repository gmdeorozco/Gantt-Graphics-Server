package com.gantt.gantt.controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gantt.gantt.entities.Activity;
import com.gantt.gantt.entities.NewActivityPayload;
import com.gantt.gantt.services.ActivityService;

@RestController
public class ActivityController {

    @Autowired
    ActivityService activityService;
    
    @PostMapping("/activity/create")
    public Activity create( @RequestBody NewActivityPayload payload ){
     
        return activityService.save( payload );

    }

    @GetMapping("/activity")
    public List<Activity> getAll(){
        return activityService.findAll();
    }

    @GetMapping("/activity/{id}")
    public Activity getById( @PathVariable(value = "id") Long id){
        return activityService.findById( id ).get();
    }

    @DeleteMapping("/activity/{id}/delete")
    public boolean deleteById( @PathVariable(value = "id") Long id ){
        return activityService.deleteById( id );
    }

}
