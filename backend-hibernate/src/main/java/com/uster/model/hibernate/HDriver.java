package com.uster.model.hibernate;

import com.uster.model.Driver;
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
@Table(name = "drivers")
public class HDriver implements ConvertTo<Driver>, HasId<Long> {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 255, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "HLicense_id")
    private HLicense license;

    public static HDriver from(Driver x) {
        return HDriver
                .builder()
                .id(ofNullable(x.getId()).map(Long::valueOf).orElse(null))
                .name(x.getName())
                .license(HLicense.from(x.getLicense()))
                .build();
    }

    public Driver to() {
        return Driver
                .builder()
                .id(ofNullable(id).map(Object::toString).orElse(null))
                .name(name)
                .license(license.to())
                .build();
    }
}
