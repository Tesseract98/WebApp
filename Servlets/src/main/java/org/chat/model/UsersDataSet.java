package org.chat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UsersDataSet implements Serializable {
    private static final long serialVersionUID = 314159265358979323L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login", unique = true, updatable = false, nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @SuppressWarnings("unused")
    public UsersDataSet() {
    }

    public UsersDataSet(String name, String password) {
        setId(-1);
        setName(name);
        setPassword(password);
    }

    @SuppressWarnings("unused")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", id, name, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersDataSet usersDataSet = (UsersDataSet) o;
        return name.equals(usersDataSet.name) &&
                password.equals(usersDataSet.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }

}
