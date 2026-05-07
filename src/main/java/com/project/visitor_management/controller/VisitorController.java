package com.project.visitor_management.controller;

import com.project.visitor_management.dto.VisitorDTO;
import com.project.visitor_management.entity.Visitor;
import com.project.visitor_management.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/visitors")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService service;

    // Message
    private ResponseEntity<Map<String, Object>> buildResponse(
            boolean success, String message, Object data, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", message);
        response.put("data", data);
        return ResponseEntity.status(status).body(response);
    }

    // Add visitor
    @PostMapping
    public ResponseEntity<Map<String, Object>> addVisitor(@RequestBody Visitor visitor) {
        try {
            VisitorDTO saved = service.addVisitor(visitor);
            return buildResponse(true, "Visiteur ajouté avec succès", saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return buildResponse(false, "Erreur lors de l'ajout du visiteur : " + e.getMessage(),
                    null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all visitors
    @GetMapping
    public ResponseEntity<Map<String, Object>> getVisitors() {
        try {
            List<VisitorDTO> visitors = service.getAllVisitors();
            String message = visitors.isEmpty()
                    ? "Aucun visiteur enregistré"
                    : visitors.size() + " visiteur(s) récupéré(s) avec succès";
            return buildResponse(true, message, visitors, HttpStatus.OK);
        } catch (Exception e) {
            return buildResponse(false, "Erreur lors de la récupération des visiteurs",
                    null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update visitor
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateVisitor(
            @PathVariable Long id, @RequestBody Visitor visitor) {
        try {
            VisitorDTO updated = service.updateVisitor(id, visitor);
            return buildResponse(true, "Visiteur mis à jour avec succès", updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            // "Visitor not found" levé par le service
            return buildResponse(false, "Visiteur introuvable avec l'ID : " + id,
                    null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return buildResponse(false, "Erreur lors de la mise à jour : " + e.getMessage(),
                    null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete visitor
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteVisitor(@PathVariable Long id) {
        try {
            service.deleteVisitor(id);
            return buildResponse(true, "Visiteur supprimé avec succès", null, HttpStatus.OK);
        } catch (RuntimeException e) {
            return buildResponse(false, "Visiteur introuvable avec l'ID : " + id,
                    null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return buildResponse(false, "Erreur lors de la suppression : " + e.getMessage(),
                    null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Stats
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        try {
            Map<String, Double> stats = Map.of(
                    "totalPaid", service.getTotalPaid(),
                    "minPaid",   service.getMinPaid(),
                    "maxPaid",   service.getMaxPaid()
            );
            return buildResponse(true, "Statistiques calculées avec succès", stats, HttpStatus.OK);
        } catch (Exception e) {
            return buildResponse(false, "Erreur lors du calcul des statistiques",
                    null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}