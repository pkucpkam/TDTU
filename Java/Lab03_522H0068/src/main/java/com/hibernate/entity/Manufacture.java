package com.hibernate.entity;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Manufacture")
public class Manufacture {
    @Id
    private String ID;

    @Column(name = "Name", nullable = false, length = 128)
    private String name;

    @Column(name = "Location", nullable = false)
    private String location;

    @Column(name = "Employee", nullable = false)
    private int employee;

    @OneToMany(mappedBy = "manufacture", cascade = CascadeType.ALL)
    private List<Phone> phones;

    // Constructors, Getters, Setters, hashCode, equals, toString methods

    public Manufacture() {}

    public Manufacture(String ID, String name, String location, int employee) {
        this.ID = ID;
        this.name = name;
        this.location = location;
        this.employee = employee;
    }

    // Getters and Setters...


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEmployee() {
        return employee;
    }

    public void setEmployee(int employee) {
        this.employee = employee;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manufacture)) return false;
        Manufacture that = (Manufacture) o;
        return ID.equals(that.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return "Manufacture{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", employee=" + employee +
                '}';
    }


}
