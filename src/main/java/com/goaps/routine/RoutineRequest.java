package com.goaps.routine;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class RoutineRequest {
    private String name;
    private String description;
}
