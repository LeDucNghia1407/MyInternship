package com.System.PharmacyManagement.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity
@Table
public class HealthFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "health_facility_sequence")
    @SequenceGenerator(name = "health_facility_sequence", sequenceName = "health_facility_sequence", allocationSize = 1)
    private long id;

    private String name;

    private String address;

    private String size;

    public HealthFacility() {
    }

    public HealthFacility(long id, String name, String address, String size) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String toString() {
        return String.format("Health Facility ID: %d, Name: %s, Address: %s, Size: %s",
                id, name, address, size);
    }
}
