package com.example.taskmanagement.data.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@Entity
@Table(name = "user_table")
@NoArgsConstructor(access= AccessLevel.PROTECTED, force=true)
@RequiredArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotNull(message = "Введите ваше имя!")
//    private String username;

    @NotNull(message = "email должен быть указан!")
    @Column(name = "email")
    private String username;

    @NotNull(message = "Пароль должен быть указан!")
    private String password;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private List<Task> authorTasks;

    @OneToMany(mappedBy = "executor",cascade = CascadeType.ALL)
    private List<Task> executorTasks;

    @ElementCollection(fetch = FetchType.EAGER) // Связь с таблицей ролей
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private final Set<String> roles; // "ROLE_ADMIN", "ROLE_EXECUTOR"

    public User(String username, String password, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
