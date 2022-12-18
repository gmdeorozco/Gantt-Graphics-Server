package com.gantt.gantt.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gantt.gantt.entities.Activity;
import com.gantt.gantt.entities.NewActivityPayload;
import com.gantt.gantt.entities.UpdateActivityPayload;
import com.gantt.gantt.repository.ActivityRepository;

@Service
public class ActivityService {
    @Autowired
    ActivityRepository activityRepository;

    public Optional<Activity> findById( Long id ){
        return activityRepository.findById(id);
    }



    public Activity save( NewActivityPayload payload ){

        List<Activity> preActivities = new ArrayList<>();
        LocalDate initialDate = payload.getInitialDate(); 
        

        if( payload.getPrerequisites() != null  ){

            preActivities = payload.getPrerequisites().stream()
            .map( id -> findById(id) )
            .filter( opt -> opt.isPresent())
            .map( opt -> opt.get())
            .toList();

            initialDate = findAll().stream()
                .map( act -> act.getActualEnd())
                .max( LocalDate::compareTo)
                .get()
                .plusDays(1) ;
            
        } 

        LocalDate endDate = initialDate.plusDays( payload.getDaysRequired());
            
        return activityRepository.save( Activity.builder()
            .daysRequired( payload.getDaysRequired())
            .name( payload.getName())
            .prerequisites( preActivities )
            .actualEnd(endDate)
            .soonestEnd(endDate)
            .actualInitial(initialDate)
            .soonestInitial(initialDate)
            .build()

            );
    }


    public Activity update( UpdateActivityPayload payload){
        
        List<Activity> pre = payload.getPrerequisites().stream()
            .map( id -> findById(id).get())
            .toList();
        
        LocalDate soonestInitial = getSoonestInitial( payload );
        LocalDate soonestEnd = soonestInitial.plusDays(payload.getDaysRequired());
        LocalDate actualInitial = payload.getActualInitial().isAfter(soonestInitial) 
            ? payload.getActualInitial() : soonestInitial;


        return activityRepository.save(
            Activity.builder()
            .actualEnd( actualInitial.plusDays( payload.getDaysRequired() ) )
            .actualInitial( actualInitial )
            .daysRequired( payload.getDaysRequired())
            .id( payload.getId())
            .name( payload.getName() )
            .prerequisites( pre )
            .soonestEnd( soonestEnd )
            .soonestInitial( soonestInitial )
            .build()
            );
    }

    public List<Activity> findAll(){
        return (List<Activity>) activityRepository.findAll();
    }

    public boolean deleteById( Long id ){

        try{
            Activity activityToBeDeleted = findById(id).get();

        findAll().stream()
            .forEach( act -> { 

                if ( act .getPrerequisites().contains( activityToBeDeleted )){
                    act.getPrerequisites().remove( activityToBeDeleted );
                    activityRepository.save(act);
                }

            });
            
        activityRepository.deleteById(id);
        return true;

        }
        catch( Exception e){
            return false;
        }

      
            

        }
    
        private LocalDate getSoonestInitial( UpdateActivityPayload payload){
            return  payload.getPrerequisites().stream()
                .map( id -> findById(id).get().getActualEnd())
                .max( LocalDate::compareTo)
                .get()
                .plusDays(1);
        } 
}
