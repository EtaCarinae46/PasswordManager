package model.connection;

import model.model.PwdEntity;

class Queries {
    static final String GET_MAX_ID = "SELECT MAX(id) as MAX FROM PWDS ;";

    static final String CREATE_MAIN_TABLE = "CREATE TABLE IF NOT EXISTS PWDS(ID INT PRIMARY KEY, TITLE VARCHAR(255), USERNAME VARCHAR(255), PASSWORD VARCHAR(255), NOTE VARCHAR(255));";

    static final String GET_ALL_PASSWORD = "SELECT * FROM PWDS";

    static final String REMOVE_ALL_ROWS = "DELETE FROM PWDS;";

    private static final String GET_PASSWORD = "SELECT * FROM PWDS WHERE id=%d";

    private static final String ADD_NEW_ROW = "INSERT INTO PWDS VALUES(%d, '%s', '%s', '%s', '%s');";

    private static final String UPDATE_ROW = "UPDATE PWDS SET TITLE='%s', USERNAME='%s', PASSWORD='%s', NOTE='%s' WHERE ID=%d;";

    private static final String REMOVE_ROW = "DELETE FROM PWDS WHERE ID=%d;";

    static String getPwd(int id) {
        return String.format("SELECT * FROM PWDS WHERE id=%d", id);
    }

    static String addNewRow(PwdEntity r) {
        return String.format("INSERT INTO PWDS VALUES(%d, '%s', '%s', '%s', '%s');", r.getId(), r.getTitle(), r.getUsername(), r.getPassword(), r.getNote());
    }

    static String updateRow(PwdEntity r) {
        return String.format("UPDATE PWDS SET TITLE='%s', USERNAME='%s', PASSWORD='%s', NOTE='%s' WHERE ID=%d;", r.getTitle(), r.getUsername(), r.getPassword(), r.getNote(), r.getId());
    }

    static String removeRow(int id) {
        return String.format("DELETE FROM PWDS WHERE ID=%d;", id);
    }
}
