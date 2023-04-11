package com.System.PharmacyManagement.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Data
@ToString
@Entity
@Table(name = "Calls")
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "call_sequence")
    @SequenceGenerator(name = "call_sequence", sequenceName = "call_sequence", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "healthcare_service_id", referencedColumnName = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private HealthcareService healthcareServiceId;


    @ManyToOne
    @JoinColumn(name = "health_facility_id", referencedColumnName = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private HealthFacility healthFacilityId;

    private Date date;

    public Call() {
    }


    public Call(long id, HealthcareService healthcareServiceId, HealthFacility healthFacilityId, Date date) {
        this.id = id;
        this.healthcareServiceId = healthcareServiceId;
        this.healthFacilityId = healthFacilityId;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HealthcareService getHealthcareServiceId() {
        return healthcareServiceId;
    }

    public void setHealthcareServiceId(HealthcareService healthcareServiceId) {
        this.healthcareServiceId = healthcareServiceId;
    }

    public HealthFacility getHealthFacilityId() {
        return healthFacilityId;
    }

    public void setHealthFacilityId(HealthFacility healthFacilityId) {
        this.healthFacilityId = healthFacilityId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String toString() {
        return String.format("Call ID: %d, Healthcare Service ID: %d, Health Facility ID: %d, Date: %s", id,
                healthcareServiceId.getId(), healthFacilityId.getId(), date);
    }
}

