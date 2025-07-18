package com.ehrblockchain.healthrecord.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "lab_results")
public class LabResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String testName;
    private LocalDate testDate;
    private String result;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_record_id", nullable = false)
    private HealthRecord healthRecord;

    public LabResult() {
    }

    public LabResult(String testName, LocalDate testDate, String result) {
        this.testName = testName;
        this.testDate = testDate;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public HealthRecord getHealthRecord() {
        return healthRecord;
    }

    public void setHealthRecord(HealthRecord healthRecord) {
        this.healthRecord = healthRecord;
    }

    @Override
    public String toString() {
        return "LabResult{" +
                "id=" + id +
                ", testName='" + testName + '\'' +
                ", testDate=" + testDate +
                ", result='" + result + '\'' +
                '}';
    }
}
