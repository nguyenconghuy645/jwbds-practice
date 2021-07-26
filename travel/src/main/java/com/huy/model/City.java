package com.huy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @ManyToOne
    @JoinColumn(name = "countries_id", referencedColumnName = "id", nullable = false)
    private Country country;

    @Column(nullable = false)
    @NotEmpty
    @Min(value = 0)
    private String acreage;

    @Column(nullable = false)
    @NotEmpty
    @Min(value = 0)
    private String population;

    @Column(nullable = false)
    @NotEmpty
    @Min(value = 0)
    private String gdp;

    @Column(nullable = false)
    @NotEmpty
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
