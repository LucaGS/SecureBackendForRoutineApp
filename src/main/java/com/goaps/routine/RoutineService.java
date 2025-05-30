package com.goaps.routine;

import com.goaps.auth.AuthenticationResponse;
import com.goaps.user.User;
import com.goaps.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RoutineService {
    private final RoutineRepository repository;
    private final UserRepository userRepository;
    
    public Routine create(RoutineRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var routine = Routine.builder()
                .name(request.getName())
                .description(request.getDescription())
                .user(user)
                .build();
       return repository.save(routine);
    }

    public List<Routine> listAllUserRoutines() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return repository.findAll()
                .stream()
                .filter(routine -> Objects.equals(routine.getUser().getId() , user.getId()))
                .toList();
    }
}
