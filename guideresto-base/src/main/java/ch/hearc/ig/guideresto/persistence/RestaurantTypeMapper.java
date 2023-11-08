package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.RestaurantType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantTypeMapper extends AbstractDataMapper<RestaurantType> {

    public RestaurantTypeMapper(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "TYPES_GASTRONOMIQUES";
    }

    @Override
    public void create(RestaurantType restaurantType) throws SQLException {
        String sql = "INSERT INTO " + getTableName() + " (LIBELLE, DESCRIPTION) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, restaurantType.getLabel());
            stmt.setString(2, restaurantType.getDescription());
            stmt.executeUpdate();
            System.out.println("Type de restaurant créé avec succès");
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la création du type de restaurant", e);
        }
    }

    @Override
    public RestaurantType read(int id) throws SQLException {
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
    public void update(int id, RestaurantType restaurantType) throws SQLException {
        String sql = "UPDATE " + getTableName() + " SET LIBELLE = ?, DESCRIPTION = ? WHERE NUMERO = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, restaurantType.getLabel());
            stmt.setString(2, restaurantType.getDescription());
            stmt.setInt(3, id);
            int rowCount = stmt.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Type de restaurant mis à jour avec succès");
            } else {
                System.err.println("La mise à jour du type de restaurant a échoué. Aucun type correspondant trouvé.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la mise à jour du type de restaurant", e);
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE NUMERO = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowCount = stmt.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Type de restaurant supprimé avec succès");
            } else {
                System.err.println("La suppression du type de restaurant a échoué. Aucun type correspondant trouvé.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la suppression du type de restaurant", e);
        }
    }

    public List<RestaurantType> getAll() throws SQLException {
        return getAll(getTableName());
    }

    @Override
    protected RestaurantType mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("NUMERO");
        String label = rs.getString("LIBELLE");
        String description = rs.getString("DESCRIPTION");

        return new RestaurantType(id, label, description);
    }
}