package com.project.visitor_management.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitorDTO {
    private Long visitorId;
    private String name;
    private int numberOfDays;
    private double dailyRate;
    private double totalFee;
}
