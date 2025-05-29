package com.goaps.routine;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Routine")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService routineService;

    @PostMapping()
    public ResponseEntity<Routine> Create(
            @RequestBody RoutineRequest request
    ){
        return ResponseEntity.ok(routineService.create(request));
    }
}
