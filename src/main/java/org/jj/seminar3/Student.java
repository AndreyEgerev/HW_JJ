package org.jj.seminar3;

import java.util.UUID;

public class Student {
    private UUID id;
    private String firstName;
    private String secondName;
    private String groupId;

    public Student() {
    }

    public Student(UUID id, String firstName, String secondName, String groupId) {
        this.id = id;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
