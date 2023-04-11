package com.System.PharmacyManagement.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
@Data
@ToString
@Entity
@Table
public class HealthcareStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "health_staff_sequence")
    @SequenceGenerator(name = "health_staff_sequence", sequenceName = "health_staff_sequence", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "roomid", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Room room;

    @Column
    private String doctorId;

    private String type;

    public HealthcareStaff() {
    }

    public HealthcareStaff(long id, Room room, String doctorId, String type) {
        this.id = id;
        this.room = room;
        this.doctorId = doctorId;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return String.format("Healthcare Staff ID: %d, Room: %d, Doctor ID: %s, Type: %s",
                id, room.getId(), doctorId, type);
    }
}
