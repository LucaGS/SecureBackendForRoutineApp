package com.goaps.activity;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/Activity")
public class ActivityController {
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
