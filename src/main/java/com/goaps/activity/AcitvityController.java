package com.goaps.activity;

import com.goaps.routine.RoutineRequest;
import com.goaps.routine.RoutineResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/Activity")
public class AcitvityController {
    private final ActivityService activityService;
    @PostMapping()
    public ResponseEntity<ActivityResponse> Create(
            @RequestBody ActivityRequest request
    ){
        return ResponseEntity.ok(activityService.create(request));
    }
    @GetMapping("/routine/{routineId}")
    public List<ActivityResponse> listAllUserRoutines(@PathVariable int routineId) {
        return activityService.getAllForRoutine(routineId);
    }
}
