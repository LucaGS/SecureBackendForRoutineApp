package com.goaps.routine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoutineResponse {
    private Integer id;
    private String name;
    private String description;
}
