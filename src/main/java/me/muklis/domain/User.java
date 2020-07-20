package me.muklis.domain;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Entity(name = "AU_USER")
@Table(name = "AU_USER")
public class User implements OAuth2User, UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String name;
    private String username;
    private String password;
    private Boolean enable;
    @Transient
    private Map<String, Object> attribute;
    @Transient
    private Set<GrantedAuthority> grantedAuthorities;
    private String role = "ROLE_USER";

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attribute;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    public void setAttribute(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    public User() {
    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
        this.grantedAuthorities.add(new SimpleGrantedAuthority(this.role));
    }

    public User(String email, String name, Map<String, Object> attribute) {
        this.email = email;
        this.name = name;
        this.attribute = attribute;
        this.grantedAuthorities.add(new SimpleGrantedAuthority(this.role));

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
