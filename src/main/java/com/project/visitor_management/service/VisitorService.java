package com.project.visitor_management.service;

import com.project.visitor_management.dto.VisitorDTO;
import com.project.visitor_management.entity.Visitor;
import com.project.visitor_management.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository repository;

    // Add visitor
    public VisitorDTO addVisitor(Visitor visitor) {
        Visitor saved = repository.save(visitor);
        return toDTO(saved);
    }

    // Get all visitors
    public List<VisitorDTO> getAllVisitors() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Update visitor
    public VisitorDTO updateVisitor(Long id, Visitor visitor) {
        return repository.findById(id).map(v -> {
            v.setName(visitor.getName());
            v.setNumberOfDays(visitor.getNumberOfDays());
            v.setDailyRate(visitor.getDailyRate());
            return toDTO(repository.save(v));
        }).orElseThrow(() -> new RuntimeException("Visitor not found"));
    }

    // Delete visitor
    public void deleteVisitor(Long id) {
        repository.deleteById(id);
    }

    // Stats
    public double getTotalPaid() {
        return repository.findAll().stream()
                .mapToDouble(v -> v.getNumberOfDays() * v.getDailyRate())
                .sum();
    }

    public double getMinPaid() {
        return repository.findAll().stream()
                .mapToDouble(v -> v.getNumberOfDays() * v.getDailyRate())
                .min()
                .orElse(0);
    }

    public double getMaxPaid() {
        return repository.findAll().stream()
                .mapToDouble(v -> v.getNumberOfDays() * v.getDailyRate())
                .max()
                .orElse(0);
    }

    // Mapper
    private VisitorDTO toDTO(Visitor v) {
        return VisitorDTO.builder()
                .visitorId(v.getVisitorId())
                .name(v.getName())
                .numberOfDays(v.getNumberOfDays())
                .dailyRate(v.getDailyRate())
                .totalFee(v.getTotalFee())
                .build();
    }
}
