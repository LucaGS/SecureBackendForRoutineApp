package com.goaps.activity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ActivityRequest {
    private String name;
    private String description;
}
