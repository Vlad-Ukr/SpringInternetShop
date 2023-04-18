package com.shop.model;

import com.shop.dto.UserDTO;
import com.shop.util.DateConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @Column(name = "userID")
    private String id;
    @NotBlank(message = "Name is required")
    private String name;
    @Size(min = 2, max = 20, message = "Surname should be from 1 to 20 symbols")
    private String surname;
    @Email
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "userID"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "user_role_id"))
    private Set<Role> roles = new java.util.LinkedHashSet<>();

    @Column(name = "ban_date")
    private String banDate;
    @Column(name = "failed_attempts")
    private int failedAttempts;

    public User(String name, String surname, String email,
                String password) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public User(String id, String name, String surname,
                String email, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public User(UserDTO userDTO) {
        this.id = UUID.randomUUID().toString();
        this.name = userDTO.getName();
        this.surname = userDTO.getSurname();
        this.email = userDTO.getEmail();
        this.password = new BCryptPasswordEncoder().encode(userDTO.getPassword());
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1));
        this.banDate = DateConverter.convertLongToTimeStamp(System.currentTimeMillis());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !DateConverter.isUserBanned(this);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return failedAttempts == user.failedAttempts && Objects.equals(id, user.id)
                && Objects.equals(name, user.name) && Objects.equals(surname, user.surname)
                && Objects.equals(email, user.email) && Objects.equals(password, user.password)
                && roles == user.roles && Objects.equals(banDate, user.banDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, password, roles, banDate, failedAttempts);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles.toString() +
                ", banDate='" + banDate + '\'' +
                ", failedAttempts=" + failedAttempts +
                '}';
    }
}
