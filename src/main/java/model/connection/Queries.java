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
    private static final String GET_FILTERED_ROWS = "SELECT * FROM PWDS WHERE LOWER(TITLE) LIKE '%%%1$s%%' OR LOWER(USERNAME) LIKE '%%%1$s%%' OR LOWER(NOTE) LIKE '%%%1$s%%'";

    static String getPwd(int id) {
        return String.format(GET_PASSWORD, id);
    }

    static String addNewRow(PwdEntity r) {
        return String.format(ADD_NEW_ROW, r.getId(), r.getTitle(), r.getUsername(), r.getPassword(), r.getNote());
    }

    static String updateRow(PwdEntity r) {
        return String.format(UPDATE_ROW, r.getTitle(), r.getUsername(), r.getPassword(), r.getNote(), r.getId());
    }

    static String removeRow(int id) {
        return String.format(REMOVE_ROW, id);
    }

    static String filteredPwds(String filter) {
        if (filter == null) return GET_ALL_PASSWORD;
        return String.format(GET_FILTERED_ROWS, filter.toLowerCase());
    }
}
