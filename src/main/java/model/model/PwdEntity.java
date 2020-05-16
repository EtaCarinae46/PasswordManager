package model.model;

public class PwdEntity {
    private int id;

    private String title;

    private String username;

    private String password;

    private String note;

    public PwdEntity() {}

    public PwdEntity(int id, String title, String username, String password, String note) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.password = password;
        this.note = note;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
