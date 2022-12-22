package com.gantt.gantt.controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gantt.gantt.assemblers.ActivityModelAssembler;
import com.gantt.gantt.entities.Activity;
import com.gantt.gantt.entities.NewActivityPayload;
import com.gantt.gantt.entities.UpdateActivityPayload;
import com.gantt.gantt.models.ActivityModel;
import com.gantt.gantt.services.ActivityService;

@RestController
@RequestMapping("/api")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @Autowired
    ActivityModelAssembler activityModelAssembler;
    
    @PostMapping("/activity/create")
    public ResponseEntity<ActivityModel> create( @RequestBody NewActivityPayload payload ){
     
        return 
            ResponseEntity.ok(activityModelAssembler.toModel(activityService.save( payload )));

    }

    @GetMapping("/activity")
    public ResponseEntity<CollectionModel<ActivityModel>> getAll(){

        List<Activity> activities = activityService.findAll();

        return new ResponseEntity<>( activityModelAssembler.toCollectionModel(activities),
            HttpStatus.OK);
    }

    @GetMapping("/activity/{id}")
    public ResponseEntity<ActivityModel> getById( @PathVariable(value = "id") Long id){
        
        return activityService.findById( id )
            .map( activityModelAssembler::toModel)
            .map( ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }



    @PutMapping("/activity/update")
    public ResponseEntity<ActivityModel> updateActivity( @RequestBody UpdateActivityPayload payload){
        
        return ResponseEntity.ok(activityModelAssembler.toModel(activityService.update( payload )));
    }

    @DeleteMapping("/activity/{id}/delete")
    public Activity deleteById( @PathVariable(value = "id") Long id ){
        Activity activity = activityService.findById( id ).get();
        
        activityService.deleteById( id );

        return activity;

    }

    @PutMapping("activity/{id}/dettach/{pid}")
    public Activity dettachPrerequisite( @PathVariable(value = "id") Long id,
        @PathVariable(value = "pid") Long pid ){

            Activity activity = activityService.findById( id ).get();

             activityService.dettachPrerequisite(id, pid);

             return activity;
        }

}
