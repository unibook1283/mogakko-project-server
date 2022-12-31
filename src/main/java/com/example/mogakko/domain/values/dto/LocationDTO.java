package com.example.mogakko.domain.values.dto;

import com.example.mogakko.domain.values.domain.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationDTO {

    private Long id;
    private String name;

    public LocationDTO(Location location) {
        this.id = location.getId();
        this.name = location.getName();
    }

    public Location toEntity() {
        Location location = new Location();
        location.setId(id);
        location.setName(name);
        return location;
    }

}
