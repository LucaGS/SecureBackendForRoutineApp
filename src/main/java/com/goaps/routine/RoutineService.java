package com.goaps.routine;

import com.goaps.auth.AuthenticationResponse;
import com.goaps.user.User;
import com.goaps.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class RoutineService {
    private final RoutineRepository repository;
    private final UserRepository userRepository;
    public Routine create(RoutineRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var routine = Routine.builder()
                .name(request.getName())
                .description(request.getDescription())
                .user(user)
                .build();
       return repository.save(routine);

    }
}
