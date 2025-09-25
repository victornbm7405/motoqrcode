package br.com.fiap.mottu.motoqrcode.security;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserAuthRepository {

    private final JdbcTemplate jdbc;

    public UserAuthRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<AuthUser> findByUsername(String username) {
        String sql = """
            SELECT USERNAME, EMAIL, NOME, PASSWORD, ROLE
            FROM USUARIO
            WHERE USERNAME = ?
            """;
        return jdbc.query(sql, rs -> {
            if (rs.next()) {
                return Optional.of(new AuthUser(
                        rs.getString("USERNAME"),
                        rs.getString("EMAIL"),
                        rs.getString("NOME"),
                        rs.getString("PASSWORD"),
                        rs.getString("ROLE")
                ));
            }
            return Optional.empty();
        }, username);
    }
}
