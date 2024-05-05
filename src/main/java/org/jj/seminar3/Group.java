package org.jj.seminar3;

import java.util.UUID;

public class Group {
    private UUID id;
    private String name;

    public Group() {
    }

    public Group(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id= " + id +
                ", name= '" + name + '\'' +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
