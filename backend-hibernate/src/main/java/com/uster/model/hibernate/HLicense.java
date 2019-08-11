package com.uster.model.hibernate;

import com.uster.model.License;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "licenses")
public class HLicense implements ConvertTo<License>, HasId<String> {
    @Id
    private String id;

    @Column(length = 255, unique = true)
    private String name;

    public static HLicense from(License x) {
        return HLicense
                .builder()
                .id(x.getId())
                .name(x.getName())
                .build();
    }

    public License to() {
        return License
                .builder()
                .id(id)
                .name(name)
                .build();
    }
}
