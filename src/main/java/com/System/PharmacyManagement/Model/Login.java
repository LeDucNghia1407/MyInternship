package com.System.PharmacyManagement.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString

@Entity
@Table
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "login_sequence")
    @SequenceGenerator(name = "login_sequence", sequenceName = "login_sequence", allocationSize = 1)
    private long id;



    private String username;

    private String password;

    public Login() {
    }

    public Login(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return String.format("Login ID: %d,  Username: %s, Password: %s",
                id,  username, password);
    }
}
