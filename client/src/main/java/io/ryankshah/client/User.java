package io.ryankshah.client;

import java.util.UUID;

public class User
{
    private UUID uuid;
    private String firstName, lastName, email;
    private int rank;

    public User(UUID uuid, String firstName, String lastName, String email, int rank) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.rank = rank;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }
}