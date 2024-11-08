package safa.safepaws.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address", schema = "public", catalog = "safe_paws")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "coordinate_x")
    private Float coordinateX;

    @Column(name = "coordinate_y")
    private Float coordinateY;

    @Column(name = "road")
    private String road;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "village")
    private String village;

    @Column(name = "province")
    private String province;

    @Column(name = "state")
    private String state;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "country")
    private String country;

    @Column(name = "country_code")
    private String countryCode;
}
