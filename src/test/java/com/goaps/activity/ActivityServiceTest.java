package com.goaps.activity;

import com.goaps.routine.Routine;
import com.goaps.routine.RoutineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private RoutineRepository routineRepository;

    @InjectMocks
    private ActivityService activityService;

    private Activity activity;
    private Routine routine;

    @BeforeEach
    void setUp() {
        routine = Routine.builder()
                .id(1)
                .name("Test Routine")
                .build();

        activity = Activity.builder()
                .id(1)
                .name("Test Activity")
                .description("Test Description")
                .routine(routine)
                .build();
    }

    @Test
    void createActivity_ShouldReturnSavedActivity() {
        when(activityRepository.save(any(Activity.class))).thenReturn(activity);

        Activity result = activityService.createActivity(activity);

        assertNotNull(result);
        assertEquals(activity.getName(), result.getName());
        assertEquals(activity.getDescription(), result.getDescription());
        verify(activityRepository).save(any(Activity.class));
    }

    @Test
    void getActivityById_ShouldReturnActivity_WhenExists() {
        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));

        Activity result = activityService.getActivityById(1);

        assertNotNull(result);
        assertEquals(activity.getId(), result.getId());
        assertEquals(activity.getName(), result.getName());
    }

    @Test
    void getActivityById_ShouldThrowException_WhenNotExists() {
        when(activityRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> activityService.getActivityById(1));
    }

    @Test
    void getAllActivities_ShouldReturnAllActivities() {
        List<Activity> activities = Arrays.asList(activity);
        when(activityRepository.findAll()).thenReturn(activities);

        List<Activity> result = activityService.getAllActivities();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(activity.getName(), result.get(0).getName());
    }

    @Test
    void updateActivity_ShouldReturnUpdatedActivity() {
        Activity updatedActivity = Activity.builder()
                .id(1)
                .name("Updated Activity")
                .description("Updated Description")
                .routine(routine)
                .build();

        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));
        when(activityRepository.save(any(Activity.class))).thenReturn(updatedActivity);

        Activity result = activityService.updateActivity(1, updatedActivity);

        assertNotNull(result);
        assertEquals(updatedActivity.getName(), result.getName());
        assertEquals(updatedActivity.getDescription(), result.getDescription());
    }

    @Test
    void updateActivity_ShouldThrowException_WhenNotExists() {
        when(activityRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> activityService.updateActivity(1, activity));
    }

    @Test
    void deleteActivity_ShouldDeleteActivity() {
        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));
        doNothing().when(activityRepository).delete(activity);

        activityService.deleteActivity(1);

        verify(activityRepository).delete(activity);
    }

    @Test
    void deleteActivity_ShouldThrowException_WhenNotExists() {
        when(activityRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> activityService.deleteActivity(1));
    }

    @Test
    void getActivitiesByRoutineId_ShouldReturnActivities() {
        List<Activity> activities = Arrays.asList(activity);
        when(activityRepository.findByRoutineId(1)).thenReturn(activities);

        List<Activity> result = activityService.getActivitiesByRoutineId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(activity.getName(), result.get(0).getName());
    }
} 