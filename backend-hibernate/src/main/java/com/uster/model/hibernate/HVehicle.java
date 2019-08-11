package com.uster.model.hibernate;

import com.uster.model.Vehicle;
import lombok.*;

import javax.persistence.*;

import static java.util.Optional.ofNullable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "vehicles")
public class HVehicle implements ConvertTo<Vehicle>, HasId<Long> {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 255, unique = true)
    private String name;

    @Column(length = 20, unique = true)
    private String numberPlate;

    @ManyToOne
    @JoinColumn(name = "HLicense_id")
    private HLicense minimumLicenseRequired;

    public static HVehicle from(Vehicle x) {
        return HVehicle
                .builder()
                .id(ofNullable(x.getId()).map(Long::valueOf).orElse(null))
                .name(x.getName())
                .numberPlate(x.getNumberPlate())
                .minimumLicenseRequired(HLicense.from(x.getMinimumLicenseRequired()))
                .build();
    }

    public Vehicle to() {
        return Vehicle
                .builder()
                .id(ofNullable(id).map(Object::toString).orElse(null))
                .name(name)
                .numberPlate(numberPlate)
                .minimumLicenseRequired(minimumLicenseRequired.to())
                .build();
    }
}
