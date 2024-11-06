package safa.safepaws.model;


import jakarta.persistence.*;
import lombok.*;
import safa.safepaws.enums.Role;

@Entity

@Table(name = "user" , schema = "public", catalog = "safe_paws")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username" , nullable = false)
    private String username;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "rol",nullable = false)
    @Enumerated(EnumType.STRING)
    private Role rol;

    @Column(name = "ban", nullable = false)
    private boolean ban;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client_id;

}
