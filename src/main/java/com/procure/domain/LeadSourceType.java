package com.procure.domain;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "LEAD_SOURCE_TYPE")
public class LeadSourceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @Column(name = "SOURCE_TYPE")
    private String sourceType;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
    private boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
