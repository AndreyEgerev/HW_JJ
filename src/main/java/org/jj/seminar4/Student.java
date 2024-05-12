package org.jj.seminar4;

import jakarta.persistence.*;


@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "secondName")
    private String secondName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupid")
    private GroupStudent groupId;

    public Student() {
    }

    public Student(String firstName, String secondName, GroupStudent groupId) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id= " + id +
                ", firstName= '" + firstName + '\'' +
                ", secondName= '" + secondName + '\'' +
                ", groupId= '" + groupId + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public GroupStudent getGroupId() {
        return groupId;
    }

    public void setGroupId(GroupStudent groupId) {
        this.groupId = groupId;
    }

}
