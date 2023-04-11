package com.System.PharmacyManagement.Model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table
@Getter
@Setter
@ToString
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_sequence")
    @SequenceGenerator(name = "department_sequence", sequenceName = "department_sequence", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "healthfacilityID", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private HealthFacility healthFacility;

    private String name;

    private String doctorName;


    private String status;



    public Department() {
    }

    public Department(long id, HealthFacility healthFacility, String name, String doctorName, String status) {
        this.id = id;
        this.healthFacility = healthFacility;
        this.name = name;
        this.doctorName = doctorName;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HealthFacility getHealthFacility() {
        return healthFacility;
    }

    public void setHealthFacility(HealthFacility healthFacility) {
        this.healthFacility = healthFacility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + id +
                ", healthFacility=" + healthFacility.getName() +
                ", name='" + name + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
