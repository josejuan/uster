package com.uster.model.hibernate;

import com.uster.model.Trip;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

import static java.util.Optional.ofNullable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "trips")
public class HTrip implements ConvertTo<Trip>, HasId<Long> {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "HDriver_id", nullable = false)
    private HDriver driver;

    @ManyToOne
    @JoinColumn(name = "HVehicle_id", nullable = false)
    private HVehicle vehicle;

    public static HTrip from(Trip x) {
        return HTrip
                .builder()
                .id(ofNullable(x.getId()).map(Long::valueOf).orElse(null))
                .date(x.getDate())
                .driver(HDriver.from(x.getDriver()))
                .vehicle(HVehicle.from(x.getVehicle()))
                .build();
    }

    public Trip to() {
        return Trip
                .builder()
                .id(ofNullable(id).map(Object::toString).orElse(null))
                .date(date)
                .driver(driver.to())
                .vehicle(vehicle.to())
                .build();
    }
}
