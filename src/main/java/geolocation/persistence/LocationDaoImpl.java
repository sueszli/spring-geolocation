package geolocation.persistence;

import geolocation.model.dto.SearchLocationDto;
import geolocation.model.entity.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LocationDaoImpl implements LocationDao {

    private final LocationJpaRepository locationJpaRepository;

    @Override
    public List<Location> searchLocations(SearchLocationDto query) {
        log.trace("Searching locations: {}", query);
        if (query == null) {
            return locationJpaRepository.findAll();
        }

        var specification = createFilterSpecification(query);
        return query.getLimit() == null ?
                locationJpaRepository.findAll(specification) :
                locationJpaRepository.findAll(specification, Pageable.ofSize(query.getLimit())).getContent();
    }

    private Specification<Location> createFilterSpecification(SearchLocationDto query) {
        log.trace("Creating filter specification for query: {}", query);

        Specification<Location> matchesType = query.getType() == null ? null :
                (root, q, builder) -> builder.equal(root.get("type"), query.getType());

        var minQueryLat = Math.min(query.getP1().getLat(), query.getP2().getLat());
        var maxQueryLat = Math.max(query.getP1().getLat(), query.getP2().getLat());
        var minQueryLng = Math.min(query.getP1().getLng(), query.getP2().getLng());
        var maxQueryLng = Math.max(query.getP1().getLng(), query.getP2().getLng());
        Specification<Location> isInRectangle = (root, q, builder) ->
            builder.and(
                    builder.between(root.get("lat"), minQueryLat, maxQueryLat),
                    builder.between(root.get("lng"), minQueryLng, maxQueryLng)
            );

        return Specification.where(matchesType).and(isInRectangle);
    }
}
