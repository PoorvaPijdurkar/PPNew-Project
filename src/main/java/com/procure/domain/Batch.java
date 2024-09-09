package com.procure.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "batch")
@Data
@JsonIgnoreProperties(value = { "Id" }, allowGetters = true)
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BATCH_SIZE")
    private String batchSize;
}
