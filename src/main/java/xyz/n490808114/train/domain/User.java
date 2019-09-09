package xyz.n490808114.train.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class User implements UserDetails{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    @JsonProperty("user")
    private String name;
    private String email;
    private String username;
    private String password;
    private String roles;
    private Date createDate;


    public User(){}
    public User(Integer id, String name,String email,String username,String password,String roles,Date createDate){
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.createDate = createDate;

    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }
    @JsonIgnore
    public Date getCreateDate() {
        return createDate;
    }
    @JsonIgnore
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
    @JsonIgnore
    public String getRoles() {
        return roles;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(roles.split("#"))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}