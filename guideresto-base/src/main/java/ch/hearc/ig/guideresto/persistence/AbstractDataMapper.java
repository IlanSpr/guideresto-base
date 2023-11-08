package ch.hearc.ig.guideresto.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDataMapper<T> {

    protected abstract String getTableName();
    protected Connection connection;

    public AbstractDataMapper(Connection connection) {
        this.connection = connection;
    }

    public abstract void create(T item) throws SQLException;

    public abstract T read(int id) throws SQLException;

    public abstract void update(int id, T item) throws SQLException;

    public abstract void delete(int id) throws SQLException;

    public List<T> getAll(String getTableName) throws SQLException {
        List<T> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                entities.add(mapResultSetToEntity(rs));
            }
        }
        return entities;
    }

    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;
}
