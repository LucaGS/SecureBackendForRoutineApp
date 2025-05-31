package com.goaps.activity;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ActivityResponse {
    private Integer id;
    private String name;
    private String description;
    private Integer duration;
    private Integer position;
}
