package com.goaps.routine;

import com.goaps.user.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping()
    public ResponseEntity<List<Routine>> GetAllUserRoutines(
            @AuthenticationPrincipal User userDetails
    ){
        int userId = userDetails.getId();
        return ResponseEntity.ok(routineService.listAllUserRoutines());
    }
}
