package com.goaps.activity;

import com.goaps.routine.Routine;
import com.goaps.routine.RoutineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final RoutineRepository routineRepository;
    public ActivityResponse create(ActivityRequest request){
        Routine routine = routineRepository.findById(request.getRoutineId())
                .orElseThrow(() -> new RuntimeException("Routine nicht gefunden"));

        Activity activity = Activity.builder()
                .routine(routine)
                .position(request.getPosition())
                .name(request.getName())
                .description(request.getDescription())
                .duration(request.getDuration())
                .build();

       activity =  activityRepository.save(activity);

       return ActivityResponse.builder()
               .position(activity.getPosition())
               .id(activity.getId())
               .name(activity.getName())
               .description(activity.getDescription())
               .duration(activity.getDuration())
               .build();
    }
    public List<ActivityResponse> getAllForRoutine(int routineId) {
        List<Activity> activities = activityRepository.findAllByRoutine_Id(routineId);
        if (activities == null || activities.isEmpty()) {
            return new ArrayList<>();
        }

        return activities.stream()
                .map(activity -> ActivityResponse.builder()
                        .position(activity.getPosition())
                        .id(activity.getId())
                        .name(activity.getName())
                        .description(activity.getDescription())
                        .duration(activity.getDuration())
                        .build())
                .collect(Collectors.toList());
    }

}
