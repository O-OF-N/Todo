package com.todo.Todo.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Scope(scopeName = "request", proxyMode = ScopedProxyMode.DEFAULT)
@Document
public class ItemDTO {

    @Id
    private String id;
    private String name;
    private String description;
    private long createdDate;
    private long dueDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", dueDate=" + dueDate +
                '}';
    }
}
