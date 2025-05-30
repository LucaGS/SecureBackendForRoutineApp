package com.goaps.routine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class RoutineChangeRequest {
    private Integer id;
    private String name;
    private String description;
}
