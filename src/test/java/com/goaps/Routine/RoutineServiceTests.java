package com.goaps.Routine;

import com.goaps.routine.*;
import com.goaps.user.Role;
import com.goaps.user.User;
import com.goaps.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoutineServiceTests {
    @Mock
    private RoutineRepository routineRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private RoutineService routineService;

    private User testUser;
    private Routine testRoutine;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1)
                .email("test@example.com")
                .password("password")
                .role(Role.USER)
                .build();

        testRoutine = Routine.builder()
                .id(1)
                .name("Test Routine")
                .description("Test Description")
                .user(testUser)
                .build();

        // Setup security context
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(testUser);
    }

    @Test
    void createRoutine_ShouldCreateAndReturnRoutine() {
        // Arrange
        RoutineRequest request = RoutineRequest.builder()
                .name("New Routine")
                .description("New Description")
                .build();
        when(routineRepository.save(any(Routine.class))).thenReturn(testRoutine);

        // Act
        RoutineResponse response = routineService.create(request);

        // Assert
        assertNotNull(response);
        assertEquals(testRoutine.getId(), response.getId());
        assertEquals(testRoutine.getName(), response.getName());
        assertEquals(testRoutine.getDescription(), response.getDescription());
        verify(routineRepository).save(any(Routine.class));
    }

    @Test
    void listAllUserRoutines_ShouldReturnUserRoutines() {
        // Arrange
        List<Routine> routines = Arrays.asList(testRoutine);
        when(routineRepository.findAll()).thenReturn(routines);

        // Act
        List<RoutineResponse> response = routineService.listAllUserRoutines();

        // Assert
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(testRoutine.getId(), response.get(0).getId());
        assertEquals(testRoutine.getName(), response.get(0).getName());
        assertEquals(testRoutine.getDescription(), response.get(0).getDescription());
    }

}
