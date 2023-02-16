package geolocation.model;

import geolocation.model.dto.CreateLocationDto;
import geolocation.model.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class LocationMapper {
    public abstract Location dtoToEntity(CreateLocationDto dto);
}
