package com.project.visitor_management.controller;

import com.project.visitor_management.dto.VisitorDTO;
import com.project.visitor_management.entity.Visitor;
import com.project.visitor_management.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/visitors")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService service;

    // Add visitor
    @PostMapping
    public VisitorDTO addVisitor(@RequestBody Visitor visitor) {
        return service.addVisitor(visitor);
    }

    // Get all visitors
    @GetMapping
    public List<VisitorDTO> getVisitors() {
        return service.getAllVisitors();
    }

    // Update visitor
    @PutMapping("/{id}")
    public VisitorDTO updateVisitor(@PathVariable Long id, @RequestBody Visitor visitor) {
        return service.updateVisitor(id, visitor);
    }

    // Delete visitor
    @DeleteMapping("/{id}")
    public void deleteVisitor(@PathVariable Long id) {
        service.deleteVisitor(id);
    }

    // Stats
    @GetMapping("/stats")
    public Map<String, Double> getStats() {
        return Map.of(
                "totalPaid", service.getTotalPaid(),
                "minPaid", service.getMinPaid(),
                "maxPaid", service.getMaxPaid()
        );
    }
}