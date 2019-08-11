package com.uster.model;

import lombok.*;
import lombok.experimental.Wither;

@Wither
@Value
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class License {
    String id;
    String name;
}
