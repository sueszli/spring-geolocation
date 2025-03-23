package geolocation.model;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class LocationMapper {
    public abstract Location dtoToEntity(CreateLocationDto dto);
}
