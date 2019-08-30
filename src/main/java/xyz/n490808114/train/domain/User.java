package xyz.n490808114.train.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails,Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String name;
    private String password;
    private Integer status;
    private Date createDate;
    private String email;
    @JsonIgnore
    List<GrantedAuthority> grantedAuthorities;
    public User(){
        createDate = new Date();
    }
    public User(int id,String username,String name,String password,Integer status,Date createDate,String email){
        this.id=id;
        this.username = username;
        this.name = name;
        this.password=password;
        this.status=status;
        this.createDate=createDate;
        this.email=email;
    }
    public Integer getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the grantedAuthorities
     */
    public List<GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }
    /**
     * @param grantedAuthorities the grantedAuthorities to set
     */
    public void setGrantedAuthorities(List<GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }



    public void setPassword(String password) {
        this.password = password;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "User [id="+id+
                ",username="+username+
                ",lname="+name+
                ",password="+password+
                ",status="+status+
                ",createDate=" + createDate + ",email=" + email + "" + "]";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public String getPassword() {
        return password;
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
		return false;
	}
}
