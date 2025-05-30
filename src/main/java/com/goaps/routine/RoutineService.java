// Paketdefinition: Diese Klasse gehört zum "routine"-Modul der Anwendung
package com.goaps.routine;

// Importiert benötigte Klassen aus anderen Modulen und Bibliotheken
import com.goaps.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// Lombok-Annotation: Generiert automatisch einen Konstruktor mit allen Feldern
@AllArgsConstructor

// Spring-Annotation: Markiert diese Klasse als Service-Komponente für Dependency Injection
@Service
public class RoutineService {

    // Repository zur Datenbankinteraktion für Routinen (CRUD-Operationen)
    private final RoutineRepository repository;

    /**
     * Erstellt eine neue Routine basierend auf den übergebenen Daten im RoutineRequest.
     * Die Routine wird dem aktuell eingeloggten Benutzer zugewiesen.
     *
     * @param request Objekt, das die Eingabedaten für die Routineerstellung enthält (Name, Beschreibung)
     * @return Die erstellte und in der Datenbank gespeicherte Routine
     */
    public RoutineResponse create(RoutineRequest request) {
        // Ermittelt den aktuell authentifizierten Benutzer aus dem Security Context
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Erstellt ein neues Routine-Objekt mit Name, Beschreibung und zugehörigem Benutzer
        var routine = Routine.builder()
                .name(request.getName())               // Name der Routine
                .description(request.getDescription()) // Beschreibung der Routine
                .user(user)                            // Verknüpfung zur User-Entität
                .build();

        // Speichert die Routine in der Datenbank und gibt das gespeicherte Objekt zurück
        routine = repository.save(routine);
        return RoutineResponse.builder()
                .id(routine.getId())
                .name(routine.getName())
                .description(routine.getDescription())
                .build();
    }

    /**
     * Gibt eine Liste aller Routinen zurück, die dem aktuell angemeldeten Benutzer gehören.
     *
     * @return Liste von Routine-Objekten, die vom eingeloggten Benutzer erstellt wurden
     */
    public List<RoutineResponse> listAllUserRoutines() {
        // Holt den aktuell eingeloggten Benutzer
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Filtert Routinen nach Benutzer und mappt sie in RoutineResponse
        return repository.findAll()
                .stream()
                .filter(routine -> Objects.equals(routine.getUser().getId(), user.getId()))
                .map(routine -> new RoutineResponse(
                        routine.getId(),
                        routine.getName(),
                        routine.getDescription()
                ))
                .collect(Collectors.toList());
    }
    public RoutineResponse updateRoutine(RoutineChangeRequest request){
        Routine routine = repository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Routine not found with id: " + request.getId()));
        routine.setName(request.getName());
        routine.setDescription(request.getDescription());

        Routine updatedRoutine = repository.save(routine);

        return RoutineResponse.builder()
                .id(updatedRoutine.getId())
                .name(updatedRoutine.getName())
                .description(updatedRoutine.getDescription())
                .build();
    }


}
