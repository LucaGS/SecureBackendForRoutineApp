package com.goaps.routine;

import lombok.Data;

@Data
public class RoutineRequest {
    private String name;
    private String description;
    private Integer userId;
}
