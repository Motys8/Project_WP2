package com.foundite.foundite_app.model;
 
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
 
@Entity
@Table(name = "users")
public class User {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(nullable = false, unique = true, length = 50)
    private String username;
 
    @Column(nullable = false, unique = true, length = 100)
    private String email;
 
    @Column(nullable = false)
    private String password;
 
    @Column(name = "full_name", length = 100)
    private String fullName;
 
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
 
    @Column(nullable = false)
    private boolean enabled = true;
 
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();
 
    public User() {
        this.createdAt = LocalDateTime.now();
    }
 
    public User(String username, String email, String password, String fullName) {
        this();
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.roles.add("ROLE_USER");
    }
 
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
 
    public boolean isAdmin() {
        return roles.contains("ROLE_ADMIN");
    }
}

