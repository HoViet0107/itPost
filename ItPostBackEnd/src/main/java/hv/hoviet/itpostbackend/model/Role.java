package hv.hoviet.itpostbackend.model;

import hv.hoviet.itpostbackend.model.enums.EnumRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private EnumRole role_name;
}
