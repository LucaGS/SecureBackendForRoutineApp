package com.goaps.activity;

import com.goaps.routine.Routine;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@Builder
public class Activity {
    private Integer id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "routine_id")
    private Routine routine;
}
