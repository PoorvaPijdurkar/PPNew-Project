package com.procure.domain;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Entitlements")
@Data
public class Entitlements {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "resource_id")
    private String resourceId;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "action_id")
    private String actionId;

    @Column(name = "action_name")
    private String actionName;
}
