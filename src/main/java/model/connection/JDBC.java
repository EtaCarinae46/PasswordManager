package model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.model.PwdEntity;

public class JDBC {

    private Connection conn = null;
    private Statement createStatement = null;
    private boolean ready = false;
    private String error = null;

    public JDBC(String url, String username, String pwd) {
        try {
            conn = DriverManager.getConnection("jdbc:h2:" + url, username, pwd);
            createStatement = conn.createStatement();
            createStatement.execute(Queries.CREATE_MAIN_TABLE);
            System.out.println("Connection ok");
            ready = true;
        } catch (SQLException ex) {
            error = ex.getMessage();
            System.out.println(String.format("Can not connect to database [ url: jdbc:h2:%s, username: %s ]", url, username));
        }
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isReady() {
        return ready;
    }

    public PwdEntity getPwd(int id) {
        try {
            ResultSet rs = createStatement.executeQuery(Queries.getPwd(id));
            rs.next();
            return entityFromRs(rs);
        } catch (SQLException e) {
            System.out.println(String.format("Error during 'getPwd' by id: %d", id));
            return null;
        }
    }

    public ArrayList<PwdEntity> getAllPwd() {
        try {
            ArrayList<PwdEntity> entities = new ArrayList<>();
            ResultSet rs = createStatement.executeQuery(Queries.GET_ALL_PASSWORD);
            while (rs.next())
                entities.add(entityFromRs(rs));
            return entities;
        } catch (SQLException e) {
            System.out.println("Error during 'getAllPwd'");
            return null;
        }
    }

    public ArrayList<PwdEntity> getFilteredPwd(String filter) {
        try {
            ArrayList<PwdEntity> entities = new ArrayList<>();
            ResultSet rs = createStatement.executeQuery(Queries.filteredPwds(filter));
            while (rs.next())
                entities.add(entityFromRs(rs));
            return entities;
        } catch (SQLException e) {
            System.out.println("Error during 'getAllPwd'");
            return null;
        }
    }

    public int getNextId() {
        try {
            ResultSet rs = createStatement.executeQuery(Queries.GET_MAX_ID);
            rs.next();
            return rs.getInt("MAX") + 1;
        } catch (SQLException e) {
            System.out.println("Error during 'getNextId'");
            return -1;
        }
    }

    private PwdEntity entityFromRs(ResultSet rs) throws SQLException {
        return new PwdEntity(rs
                .getInt("ID"), rs
                .getString("TITLE"), rs
                .getString("USERNAME"), rs
                .getString("PASSWORD"), rs
                .getString("NOTE"));
    }

    public void addPwd(PwdEntity entity) {
        try {
            entity.setId(getNextId());
            createStatement.execute(Queries.addNewRow(entity));
        } catch (SQLException e) {
            System.out.println("Error during adding entity");
        }
    }

    public void updateRow(PwdEntity pwd) {
        try {
            createStatement.execute(Queries.updateRow(pwd));
        } catch (SQLException e) {
            System.out.println("Error during updating entity");
        }
    }

    public void removeRow(PwdEntity pwd) {
        try {
            createStatement.execute(Queries.removeRow(pwd.getId()));
        } catch (SQLException e) {
            System.out.println("Error during deleting row");
        }
    }

    public void removeAll() {
        try {
            createStatement.execute(Queries.REMOVE_ALL_ROWS);
        } catch (SQLException e) {
            System.out.println("Error during deleting all row");
        }
    }

    public String getError() {
        return error;
    }
}
