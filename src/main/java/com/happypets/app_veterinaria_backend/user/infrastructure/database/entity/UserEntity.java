package com.happypets.app_veterinaria_backend.user.infrastructure.database.entity;

import com.happypets.app_veterinaria_backend.common.infrastructure.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Persistence layer for the user
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UserEntity extends AuditableEntity implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String identification;
    private String name;
    private String firstName;
    private String password;
    private String email;
    private String phone;
    private String username;

    /*Relationship of many users has many roles*/
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();


    /**
     * Map a specific roles and permissions and GrantedAuthority for Spring Security
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        for (RoleEntity role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));

            if (role.getAssignedPermissions() != null) {
                for (PermissionEntity permission : role.getAssignedPermissions()) {
                    authorities.add(new SimpleGrantedAuthority(
                            permission.getModule().toLowerCase() + ":" + permission.getAction().toLowerCase()
                    ));
                }
            }
        }
        return authorities;
    }


    /**
     * Method to distinct the user with all users
     *
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Method to return if the account is not expired
     *
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * Method to return if is an account Not Locked
     *
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * Method to know if the credentias is not expired
     *
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * Principal method to validate if the user is enabled
     *
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    /**
     * Comparation method
     *
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return id != null && id.equals(that.id);
    }

    /**
     * Compare hash method
     *
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
