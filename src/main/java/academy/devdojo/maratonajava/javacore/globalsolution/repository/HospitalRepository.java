package academy.devdojo.maratonajava.javacore.globalsolution.repository;


import academy.devdojo.maratonajava.javacore.globalsolution.conn.ConnectionFactory;
import academy.devdojo.maratonajava.javacore.globalsolution.model.Hospital;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class HospitalRepository {

    public static List<Hospital> findByName(String name) {
        log.info("Finding Hospital By Name '{}'", name);

        List<Hospital> hospitals = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = createPreparedStatementFindByName(conn, name); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                final Hospital hospital = Hospital.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
                hospitals.add(hospital);
            }
        } catch (SQLException e) {
            log.error("Error while trying to find all hospitals", e);
        }
        return hospitals;
    }

    private static PreparedStatement createPreparedStatementFindByName(Connection conn, String name) throws SQLException {
        String sql = "SELECT * FROM gndi_health.hospital where name like ?;";
        final PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, String.format("%%%s%%", name));
        return ps;
    }


    public static Optional<Hospital> findById(Integer id) {
        log.info("Finding Hospital by id '{}'", id);

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = createPreparedStatementFindById(conn, id); ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) return Optional.empty();

            return Optional.of(Hospital.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
        } catch (SQLException e) {
            log.error("Error while trying to find all hospitals", e);
        }
        return Optional.empty();
    }

    private static PreparedStatement createPreparedStatementFindById(Connection conn, Integer id) throws SQLException {
        String sql = "SELECT * FROM gndi_health.hospital where id = ?;";
        final PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }


    public static void delete(int id) {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = createPreparedStatementDelete(conn, id)) {
            ps.execute();
            log.info("Deleted hospital '{}' from the database", id);

        } catch (SQLException e) {
            log.error("Error while trying to delete hospital '{}'", id, e);
        }
    }

    private static PreparedStatement createPreparedStatementDelete(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM `gndi_health`.`hospital` WHERE (`id` = ?);";
        final PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }


    public static void save(Hospital hospital) {
        log.info("Saving Hospital '{}'", hospital);

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = createPrepareStatementSave(conn, hospital)) {
            ps.execute();
        } catch (SQLException e) {
            log.error("Error while trying to save hospital '{}'", hospital.getId(), e);
        }
    }

    private static PreparedStatement createPrepareStatementSave(Connection conn, Hospital hospital) throws SQLException {
        String sql = "INSERT INTO `gndi_health`.`hospital` (`name`) VALUES (?);";
        final PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, hospital.getName());
        return ps;
    }

    public static void update(Hospital hospital) {
        log.info("Updating hospital '{}'", hospital);
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPrepareStatementUpdate(conn, hospital)) {
             ps.execute();
        } catch (SQLException e) {
            log.error("Error while trying to update hospital '{}'", hospital.getId(), e);
        }
    }
    private static PreparedStatement createPrepareStatementUpdate(Connection conn, Hospital hospital) throws SQLException {
        String sql = "UPDATE `gndi_health`.`hospital` SET `name` = ? WHERE (`id` = ?);";
        final PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, hospital.getName());
        ps.setInt(2,  hospital.getId());
        return ps;
    }

}
