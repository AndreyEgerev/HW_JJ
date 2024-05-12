package org.jj.seminar4;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "groupstudent")
public class GroupStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToMany
    @JoinColumn(name = "groupid")
    private List<Student> studentGroup;
    @Column(name = "name")
    private String name;

    public GroupStudent() {
    }

    public GroupStudent(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id= " + id +
                ", name= '" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(List<Student> studentGroup) {
        this.studentGroup = studentGroup;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        GroupStudent group = (GroupStudent) object;
        return Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
