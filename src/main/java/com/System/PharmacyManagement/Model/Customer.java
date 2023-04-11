package com.System.PharmacyManagement.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity
@Table
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence")
    @SequenceGenerator(name = "customer_sequence", sequenceName = "customer_sequence", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name =  "healthcareServiceId", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private HealthcareService healthcareServiceId;

    private int age;

    private int tel;

    private String gender;

    private String name;

    private String symptom;

    @Column(name = "contact_type")
    private String contactType;

    public Customer() {
    }

    public Customer(long id, HealthcareService healthcareServiceId, int age, int tel, String gender, String name, String symptom, String contactType) {
        this.id = id;
        this.healthcareServiceId = healthcareServiceId;
        this.age = age;
        this.tel = tel;
        this.gender = gender;
        this.name = name;
        this.symptom = symptom;
        this.contactType = contactType;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String toString() {
        return String.format("Customer ID: %d, Healthcare Service ID: %d, Name: %s, Age: %d, Telephone: %d, Gender: %s, Symptom: %s, Contact Type: %s",
                id, healthcareServiceId.getId(), name, age, tel, gender, symptom, contactType);
    }
    }