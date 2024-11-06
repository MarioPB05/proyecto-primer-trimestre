package safa.safepaws.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity

@Table(name = "client" , schema = "public", catalog = "safe_paws")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name" , nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "birthdate",nullable = false)
    private LocalDate birthdate;

    @Column(name = "dni", nullable = false)
    private String dni;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registration_date;

    @Column(name = "location", nullable = false)
    private String location;



}
