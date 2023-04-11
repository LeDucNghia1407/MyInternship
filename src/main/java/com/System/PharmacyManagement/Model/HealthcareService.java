package com.System.PharmacyManagement.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@Data
@ToString
@NoArgsConstructor
@Entity
@Table
public class HealthcareService {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "healthcare_service_sequence")
    @SequenceGenerator(name = "healthcare_service_sequence", sequenceName = "healthcare_service_sequence", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "loginID", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Login login;
    private String name;

    private int age;

    private int tell;

    private String gender;

    public HealthcareService(long id, Login login, String name, int age, int tell, String gender) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.age = age;
        this.tell = tell;
        this.gender = gender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTell() {
        return tell;
    }

    public void setTell(int tell) {
        this.tell = tell;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String toString() {
        return String.format("HealthcareService ID: %d, Name: %s, Age: %d, Tell: %d, Gender: %s, Login: %s",
                id, name, age, tell, gender, login.getId());
    }
}
