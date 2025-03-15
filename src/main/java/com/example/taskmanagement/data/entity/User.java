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
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@Entity
@Table(name = "user_table")
@NoArgsConstructor(access= AccessLevel.PROTECTED, force=true)
@RequiredArgsConstructor
@Schema(description = "Пользователь системы, который может быть автором или исполнителем задач.")
public class User implements UserDetails {

    @Schema(description = "ID пользователя", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Email пользователя (используется как логин)", example = "user@example.com")
    @NotNull(message = "email должен быть указан!")
    @Column(name = "email")
    private String username;

    @Schema(description = "Хэшированный пароль пользователя", example = "$2a$10$DOWSDiTxs07...")
    @NotNull(message = "Пароль должен быть указан!")
    private String password;

    @Schema(description = "Список задач, созданных пользователем (автор)")
    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private List<Task> authorTasks;

    @Schema(description = "Список задач, назначенных пользователю (исполнитель)")
    @OneToMany(mappedBy = "executor",cascade = CascadeType.ALL)
    private List<Task> executorTasks;

    @Schema(description = "Роли пользователя", example = "[\"ROLE_ADMIN\", \"ROLE_EXECUTOR\"]")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private final Set<String> roles; // "ROLE_ADMIN", "ROLE_EXECUTOR"


    public User(String username, String password, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    @Schema(description = "Получение списка ролей пользователя", example = "[{\"authority\": \"ROLE_ADMIN\"}, {\"authority\": \"ROLE_EXECUTOR\"}]")
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
