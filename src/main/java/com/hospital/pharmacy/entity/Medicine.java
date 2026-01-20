package com.hospital.pharmacy.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "medicine",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
    }
)
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicineId;

    @Column(nullable = false, unique = true)
    private String name;

    private String manufacturer;

    @Column(nullable = false)
    private Double price;

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim(); // avoid " Paracetamol "
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
