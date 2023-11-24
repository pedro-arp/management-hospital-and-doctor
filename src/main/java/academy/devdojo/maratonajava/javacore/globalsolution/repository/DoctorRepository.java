package academy.devdojo.maratonajava.javacore.globalsolution.repository;


import academy.devdojo.maratonajava.javacore.globalsolution.conn.ConnectionFactory;
import academy.devdojo.maratonajava.javacore.globalsolution.model.Doctor;
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
public class DoctorRepository {

    public static List<Doctor> findByName(String name) {
        log.info("Finding Doctor By Name '{}'", name);

        List<Doctor> doctors = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = createPreparedStatementFindByName(conn, name); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                final Hospital hospital = Hospital.builder()
                        .name(rs.getString("hospital_name"))
                        .id(rs.getInt("hospital_id"))
                        .build();

                final Doctor doctor = Doctor.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .crm(rs.getInt("crm"))
                        .hospital(hospital)
                        .build();
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            log.error("Error while trying to find all doctors", e);
        }
        return doctors;
    }

    private static PreparedStatement createPreparedStatementFindByName(Connection conn, String name) throws SQLException {
        String sql = """
                select d.id, d.name,d.crm, d.hospital_id, h.name as 'hospital_name' from gndi_health.doctor d inner join
                gndi_health.hospital h on d.hospital_id = h.id
                where d.name like ?;
                """;
        final PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, String.format("%%%s%%", name));
        return ps;
    }


    public static Optional<Doctor> findById(Integer id) {
        log.info("Finding Doctor by id '{}'", id);

        List<Doctor> doctors = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = createPreparedStatementFindById(conn, id); ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) return Optional.empty();
            final Hospital hospital = Hospital.builder()
                    .name(rs.getString("hospital_name"))
                    .id(rs.getInt("hospital_id"))
                    .build();

            final Doctor doctor = Doctor.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .crm(rs.getInt("crm"))
                    .hospital(hospital)
                    .build();

            return Optional.of(doctor);
        } catch (SQLException e) {
            log.error("Error while trying to find all doctors", e);
        }
        return Optional.empty();
    }

    private static PreparedStatement createPreparedStatementFindById(Connection conn, Integer id) throws SQLException {
        String sql = """
                select d.id, d.name,d.crm, d.hospital_id, h.name as 'hospital_name' from gndi_health.doctor d inner join
                gndi_health.hospital h on d.hospital_id = h.id
                where d.id = ?;
                """;
        final PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }


    public static void delete(int id) {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = createPreparedStatementDelete(conn, id)) {
            ps.execute();
            log.info("Deleted doctor '{}' from the database", id);

        } catch (SQLException e) {
            log.error("Error while trying to delete doctor '{}'", id, e);
        }
    }

    private static PreparedStatement createPreparedStatementDelete(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM `gndi_health`.`doctor` WHERE (`id` = ?);";
        final PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }


    public static void save(Doctor doctor) {
        log.info("Saving Doctor '{}'", doctor);

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = createPrepareStatementSave(conn, doctor)) {
            ps.execute();
        } catch (SQLException e) {
            log.error("Error while trying to save doctor '{}'", doctor.getId(), e);
        }
    }

    private static PreparedStatement createPrepareStatementSave(Connection conn, Doctor doctor) throws SQLException {
        String sql = "INSERT INTO `gndi_health`.`doctor` (`name`, `crm`, `hospital_id`) VALUES (?, ?, ?);";
        final PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, doctor.getName());
        ps.setInt(2, doctor.getCrm());
        ps.setInt(3, doctor.getHospital().getId());
        return ps;
    }

    public static void update(Doctor doctor) {
        log.info("Updating doctor '{}'", doctor);
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPrepareStatementUpdate(conn, doctor)) {
             ps.execute();
        } catch (SQLException e) {
            log.error("Error while trying to update doctor '{}'", doctor.getId(), e);
        }
    }
    private static PreparedStatement createPrepareStatementUpdate(Connection conn, Doctor doctor) throws SQLException {
        String sql = "UPDATE `gndi_health`.`doctor` SET `name` = ?, `crm` = ? WHERE (`id` = ?);";
        final PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, doctor.getName());
        ps.setInt(2,  doctor.getCrm());
        ps.setInt(3,  doctor.getId());
        return ps;
    }

}
