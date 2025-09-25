package br.com.fiap.mottu.motoqrcode.security;

public class AuthUser {
    private final String username;
    private final String email;
    private final String nome;
    private final String password;
    private final String role;

    public AuthUser(String username, String email, String nome, String password, String role) {
        this.username = username;
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getEmail()    { return email; }
    public String getNome()     { return nome; }
    public String getPassword() { return password; }
    public String getRole()     { return role; }
}
