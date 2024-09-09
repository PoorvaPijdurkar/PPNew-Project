package com.procure.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "department")
@Data
public class Department {

    @Id
    @Column(name = "department_id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @Column(name = "department_code")
    @NotNull
    private String departmentCode;

    @Column(name = "department_name")
    @NotNull
    private String departmentName;

    public Department() {}

    public Department(String departmentCode, String departmentName) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
    }
}
