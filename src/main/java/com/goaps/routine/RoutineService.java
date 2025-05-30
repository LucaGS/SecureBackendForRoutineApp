// Paketdefinition: Diese Klasse gehört zum "routine"-Modul der Anwendung
package com.goaps.routine;

// Importiert benötigte Klassen aus anderen Modulen und Bibliotheken
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
    public Routine create(RoutineRequest request) {
        // Ermittelt den aktuell authentifizierten Benutzer aus dem Security Context
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Erstellt ein neues Routine-Objekt mit Name, Beschreibung und zugehörigem Benutzer
        var routine = Routine.builder()
                .name(request.getName())               // Name der Routine
                .description(request.getDescription()) // Beschreibung der Routine
                .user(user)                            // Verknüpfung zur User-Entität
                .build();

        // Speichert die Routine in der Datenbank und gibt das gespeicherte Objekt zurück
        return repository.save(routine);
    }

    /**
     * Gibt eine Liste aller Routinen zurück, die dem aktuell angemeldeten Benutzer gehören.
     *
     * @return Liste von Routine-Objekten, die vom eingeloggten Benutzer erstellt wurden
     */
    public List<Routine> listAllUserRoutines() {
        // Holt den aktuell eingeloggten Benutzer aus dem Spring Security Context
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Ruft alle Routinen aus der Datenbank ab und filtert nach Routinen, die dem Benutzer gehören
        return repository.findAll()
                .stream()
                .filter(routine -> Objects.equals(routine.getUser().getId(), user.getId())) // Vergleicht User-IDs
                .toList(); // Wandelt den Stream in eine Liste um und gibt sie zurück
    }
}
