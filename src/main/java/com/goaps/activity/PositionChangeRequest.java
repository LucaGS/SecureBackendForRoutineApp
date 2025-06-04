package com.goaps.activity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PositionChangeRequest {
    private int activityId;
    private int position;
}
