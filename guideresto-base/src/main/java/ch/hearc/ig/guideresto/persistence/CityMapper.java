package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityMapper extends AbstractDataMapper<City> {

    public CityMapper(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "VILLES";
    }

    @Override
    public void create(City city) throws SQLException {
        String sql = "INSERT INTO " + getTableName() + " (CODE_POSTAL, NOM_VILLE) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, city.getZipCode());
            stmt.setString(2, city.getCityName());
            stmt.executeUpdate();
            System.out.println("Ville créée avec succès");
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la création de la ville", e);
        }
    }

    @Override
    public City read(int id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE NUMERO = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public void update(int id, City city) throws SQLException {
        String sql = "UPDATE " + getTableName() + " SET NOM_VILLE = ? WHERE CODE_POSTAL = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, city.getCityName());
            stmt.setString(2, city.getZipCode());
            int rowCount = stmt.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Ville mise à jour avec succès");
            } else {
                System.err.println("La mise à jour de la ville a échoué. Aucune ville correspondante trouvée.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la mise à jour de la ville", e);
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE NUMERO = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowCount = stmt.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Ville supprimée avec succès");
            } else {
                System.err.println("La suppression de la ville a échoué. Aucune ville correspondante trouvée.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la suppression de la ville", e);
        }
    }

    public List<City> getAll() throws SQLException {
        return getAll(getTableName());
    }

    @Override
    protected City mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("NUMERO");
        String zipCode = rs.getString("CODE_POSTAL");
        String cityName = rs.getString("NOM_VILLE");

        return new City(id, zipCode, cityName);
    }
    public int getIdByCriteria(City city) throws SQLException {
        String sql = "SELECT NUMERO FROM " + getTableName() + " WHERE CODE_POSTAL = ? AND NOM_VILLE = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, city.getZipCode());
            stmt.setString(2, city.getCityName());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("NUMERO");
                } else {
                    return -1; // Retourne -1 si la ville n'est pas trouvée
                }
            }
        }
    }

}