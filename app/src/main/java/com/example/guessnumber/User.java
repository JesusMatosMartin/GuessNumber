package com.example.guessnumber;

public class User {
    private String name;
    private int attempts;

    public User(String name, int attempts) {
        this.setName(name);
        this.setAttempts(attempts);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}
