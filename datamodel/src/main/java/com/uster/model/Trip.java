package com.uster.model;

import lombok.*;
import lombok.experimental.Wither;

import java.time.LocalDate;

@Wither
@Value
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class Trip {
    String id;
    LocalDate date;
    Driver driver;
    Vehicle vehicle;
}
