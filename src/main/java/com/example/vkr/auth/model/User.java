package com.example.vkr.auth.model;

import com.example.vkr.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@JsonView(View.UI.class)
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column
    @JsonView(View.REST.class)
    private String password;

    @Column(name = "phone")
    private String phone;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role",
            joinColumns = {
            @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<Role> roles;

    public boolean isAdmin(Role role) {
        return roles.contains(role);
    }

    public void addRole(Role role) {
        roles.add(role);
    }
}
