package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.EvaluationCriteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EvaluationCriteriaMapper extends AbstractDataMapper<EvaluationCriteria> {

    public EvaluationCriteriaMapper(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "CRITERES_EVALUATION";
    }

    @Override
    public void create(EvaluationCriteria criteria) throws SQLException {
        String sql = "INSERT INTO " + getTableName() + " (NOM, DESCRIPTION) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, criteria.getName());
            stmt.setString(2, criteria.getDescription());
            stmt.executeUpdate();
            System.out.println("Critère d'évaluation créé avec succès");
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la création du critère d'évaluation", e);
        }
    }

    @Override
    public EvaluationCriteria read(int id) throws SQLException {
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
    public void update(int id, EvaluationCriteria criteria) throws SQLException {
        String sql = "UPDATE " + getTableName() + " SET NOM = ?, DESCRIPTION = ? WHERE NUMERO = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, criteria.getName());
            stmt.setString(2, criteria.getDescription());
            stmt.setInt(3, id);
            int rowCount = stmt.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Critère d'évaluation mis à jour avec succès");
            } else {
                System.err.println("La mise à jour du critère d'évaluation a échoué. Aucun critère correspondant trouvé.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la mise à jour du critère d'évaluation", e);
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE NUMERO = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowCount = stmt.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Critère d'évaluation supprimé avec succès");
            } else {
                System.err.println("La suppression du critère d'évaluation a échoué. Aucun critère correspondant trouvé.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la suppression du critère d'évaluation", e);
        }
    }

    public List<EvaluationCriteria> getAll() throws SQLException {
        return getAll(getTableName());
    }

    @Override
    protected EvaluationCriteria mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("NUMERO");
        String name = rs.getString("NOM");
        String description = rs.getString("DESCRIPTION");

        return new EvaluationCriteria(id, name, description);
    }
}
