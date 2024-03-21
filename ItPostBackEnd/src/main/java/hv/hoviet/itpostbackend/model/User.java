package hv.hoviet.itpostbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "phone"),
                @UniqueConstraint(columnNames = "email")
        })
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(columnDefinition = "NVARCHAR(50)")
    private String name;

    private String nick_name;

    @NotBlank
    @JsonIgnore
    private LocalDate dob;

    @JsonIgnore
    @Column(name = "subscribed_on")
    private LocalDateTime subscribedOn;

    @JsonIgnore
    private String phone;

    @JsonIgnore
    @NotBlank
    private String email;

    @JsonIgnore
    private String pass_word;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Img> ava_imgs = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    /* dùng để lưu các vai trò của người dùng
    HashSer<>() để xác định các vai trò chỉ lưu 1 lần*/

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities =
                roles.stream()
                        .map(role ->
                                new SimpleGrantedAuthority(role.getRole_name()
                                        .name())).collect(Collectors.toList()
                        );
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return pass_word;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
