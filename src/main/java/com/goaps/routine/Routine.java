package com.goaps.routine;

import com.goaps.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Routine {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
