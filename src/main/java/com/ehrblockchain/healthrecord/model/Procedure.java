package com.ehrblockchain.healthrecord.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "procedures")
public class Procedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String procedureName;
    private LocalDate procedureDate;

    @Column(name = "procedure_notes")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_record_id", nullable = false)
    private HealthRecord healthRecord;

    public Procedure() {
    }

    public Procedure(String procedureName, LocalDate procedureDate, String notes) {
        this.procedureName = procedureName;
        this.procedureDate = procedureDate;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public LocalDate getProcedureDate() {
        return procedureDate;
    }

    public void setProcedureDate(LocalDate procedureDate) {
        this.procedureDate = procedureDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public HealthRecord getHealthRecord() {
        return healthRecord;
    }

    public void setHealthRecord(HealthRecord healthRecord) {
        this.healthRecord = healthRecord;
    }

    @Override
    public String toString() {
        return "Procedure{" +
                "id=" + id +
                ", procedureName='" + procedureName + '\'' +
                ", procedureDate=" + procedureDate +
                ", notes='" + notes + '\'' +
                '}';
    }
}
