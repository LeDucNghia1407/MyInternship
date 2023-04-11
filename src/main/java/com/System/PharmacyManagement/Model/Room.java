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
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_sequence")
    @SequenceGenerator(name = "room_sequence", sequenceName = "room_sequence", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "departmentid", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Department department;

    private String room;

    private String status;

    public Room() {
    }

    public Room(long id, Department department, String room, String status) {
        this.id = id;
        this.department = department;
        this.room = room;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return String.format("Room ID: %d, DepartmentID: %d, Room: %s, Status: %s",
                id, department.getId(), room, status);
    }
}
