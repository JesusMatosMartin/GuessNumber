package com.example.guessnumber;

import android.net.Uri;

public class User {
    private String name;
    private int attempts;
    private Uri fileUri;

    public User(String name, int attempts, Uri fileUri) {
        this.setName(name);
        this.setAttempts(attempts);
        this.setFileUri(fileUri);
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

    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }
}