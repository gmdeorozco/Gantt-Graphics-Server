package com.gantt.gantt.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.gantt.gantt.controller.ActivityController;
import com.gantt.gantt.entities.Activity;
import com.gantt.gantt.entities.UpdateActivityPayload;
import com.gantt.gantt.models.ActivityModel;
import com.gantt.gantt.services.ActivityService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collections;
import java.util.List;

@Component
public class ActivityModelAssembler extends 
    RepresentationModelAssemblerSupport< Activity, ActivityModel > {

    public ActivityModelAssembler(){
        super( ActivityController.class, ActivityModel.class );
    }

    @Autowired
    ActivityService activityService;

    @Override
    public ActivityModel toModel( Activity activity ) {
       
        ActivityModel activityModel = instantiateModel(activity);

       activityModel.add(
            linkTo( 
                methodOn( ActivityController.class)
                    .getById( activity.getId())
                    
            ).withSelfRel()
        );

         

        activityModel.add(
            linkTo( 
                methodOn( ActivityController.class )
                    .deleteById( activity.getId())
            ).withRel("delete")
        );
        
        /**/

       
        activityModel.add(
            linkTo( 
                methodOn(ActivityController.class)
                    .getAll()
            ).withRel("list")
        );

        activityModel.add(
            linkTo(
                methodOn(ActivityController.class)
                    .updateActivity(new UpdateActivityPayload())
            ).withRel("update")
        ); /**/

        activityModel.setActualEnd( activity.getActualEnd() );
        activityModel.setActualInitial( activity.getActualInitial() );
        activityModel.setDaysRequired( activity.getDaysRequired() );
        activityModel.setId( activity.getId() );
        activityModel.setName( activity.getName() );
        activityModel.setSoonestEnd( activity.getSoonestEnd() );
        activityModel.setSoonestInitial( activity.getSoonestInitial() );

        activityModel.setPrerequisites( toActivtyModel(activity.getId(), activity.getPrerequisites()) );

        return activityModel;
    }

    @Override
    public CollectionModel < ActivityModel > 
        toCollectionModel( Iterable<? extends Activity> activities){
            CollectionModel<ActivityModel> activityModels = super.toCollectionModel(activities);
            activityModels.add( linkTo( methodOn( ActivityController.class).getAll()).withSelfRel());
            return activityModels;
        }

    private List<ActivityModel> toActivtyModel(Long actId, List<Activity> prerequisites) {

        if ( prerequisites == null || prerequisites.isEmpty() ){
            return Collections.emptyList();
        }

        return prerequisites.stream()
            .map(
                act -> ActivityModel.builder()
                    .actualEnd( act.getActualEnd())   
                    .actualInitial( act.getActualInitial())
                    .daysRequired( act.getDaysRequired() )
                    .id( act.getId())
                    .name( act.getName())
                    .soonestEnd( act.getSoonestEnd())
                    .soonestInitial( act.getSoonestInitial())
                    .build()
                        .add(
                            linkTo(
                                methodOn(ActivityController.class)
                                    .getById( act.getId())
                            ).withSelfRel()
                        )
                        .add(
                            linkTo(
                                methodOn( ActivityController.class )
                                    .dettachPrerequisite(actId, act.getId())       
                            ).withRel("dettach")
                        )
            ).toList();
    }
    
}
