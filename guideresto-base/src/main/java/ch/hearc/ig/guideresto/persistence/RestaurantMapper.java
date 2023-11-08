package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.Restaurant;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantMapper extends AbstractDataMapper<Restaurant> {

    public RestaurantMapper(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "RESTAURANTS";
    }

    @Override
    public void create(Restaurant restaurant) throws SQLException {
        // Recherche l'ID de la ville
        int cityId = cityMapper.getIdByCriteria(restaurant.getAddress().getCity());
        if (cityId == -1) {
            // La ville n'existe pas, gérer l'erreur ou insérer la ville ici
            throw new SQLException("La ville spécifiée n'existe pas.");
        }

        // Recherche l'ID du type de restaurant
        int restaurantTypeId = restaurantTypeMapper.getIdByLabel(restaurant.getType().getLabel());
        if (restaurantTypeId == -1) {
            // Le type de restaurant n'existe pas, gérer l'erreur ou insérer le type de restaurant ici
            throw new SQLException("Le type de restaurant spécifié n'existe pas.");
        }

        // Maintenant, vous pouvez insérer le restaurant en utilisant les IDs récupérés
        String sql = "INSERT INTO " + getTableName() + " (NOM, ADRESSE, DESCRIPTION, SITE_WEB, FK_TYPE, FK_VILL) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, restaurant.getName());
            stmt.setString(2, restaurant.getAddress().getStreet());
            stmt.setString(3, restaurant.getDescription());
            stmt.setString(4, restaurant.getWebsite());
            stmt.setInt(5, restaurantTypeId);
            stmt.setInt(6, cityId);
            stmt.executeUpdate();
            System.out.println("Restaurant créé avec succès");
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la création du restaurant", e);
        }
    }



    @Override
    public Restaurant read(int id) throws SQLException {
        // Insérez ici la logique pour lire un restaurant avec les détails des clés étrangères
    }

    @Override
    public void update(int id, Restaurant restaurant) throws SQLException {
        // Insérez ici la logique pour mettre à jour un restaurant avec les clés étrangères
    }

    @Override
    public void delete(int id) throws SQLException {
        // Insérez ici la logique pour supprimer un restaurant
    }

    public List<Restaurant> getAll() throws SQLException {
        // Insérez ici la logique pour récupérer tous les restaurants avec les détails des clés étrangères
    }

    @Override
    protected Restaurant mapResultSetToEntity(ResultSet rs) throws SQLException {
        // Insérez ici la logique pour mapper un ResultSet à un objet Restaurant avec les détails des clés étrangères
    }
}
