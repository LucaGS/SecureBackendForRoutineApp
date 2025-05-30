package com.goaps.Routine;

import com.goaps.routine.Routine;
import com.goaps.routine.RoutineRepository;
import com.goaps.user.Role;
import com.goaps.user.User;
import com.goaps.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RoutineRepositoryTests {
    @Autowired
    private RoutineRepository routineRepository;
    @Autowired
    private UserRepository userRepository;
    @Test
    public void RoutineRepository_Save_ReturnSavedRoutine(){
        //Arrange
        User user = User.builder()
                .role(Role.USER)
                .email("testuser@email.de")
                .firstname("test")
                .lastname("User")
                .password("password")
                .build();
        user = userRepository.save(user);
        Routine routine = Routine.builder()
                .user(user)
                .name("testroutine")
                .description("testdescription")
                .build();
        //Act
        Routine savedRoutine = routineRepository.save(routine);
        //Assert
        Assertions.assertThat(savedRoutine).isNotNull();
        Assertions.assertThat(savedRoutine.getId()).isGreaterThan(0);

    }
    @Test
    public void RoutineRepository_GetAll_ReturnMoreThenOneRoutine(){
        User user = User.builder()
                .role(Role.USER)
                .email("testuser@email.de")
                .firstname("test")
                .lastname("User")
                .password("password")
                .build();
       user = userRepository.save(user);
        Routine routine1 = Routine.builder()
                .user(user)
                .name("testroutine")
                .description("testdescription")
                .build();

        Routine routine2 = Routine.builder()
                .user(user)
                .name("testroutine")
                .description("testdescription")
                .build();
        routineRepository.save(routine1);
        routineRepository.save(routine2);
        List<Routine>savedRoutines = routineRepository.findAll();

        Assertions.assertThat(savedRoutines.size()).isEqualTo(2);
    }
}
