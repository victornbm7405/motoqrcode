package br.com.fiap.mottu.motoqrcode;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class PasswordTestRunner implements CommandLineRunner {

    private final PasswordEncoder encoder;

    public PasswordTestRunner(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        String rawPassword = "123";
        String hashFromDb = "$2a$10$Dow1JJ6Y9oFf.0h2yT6OaOXhLQ0bFHRg6yQEBZ7cOBmPt5PgQm7.e";

        boolean matches = encoder.matches(rawPassword, hashFromDb);
        System.out.println("👉 Testando senha '" + rawPassword + "': " + matches);
    }
}
