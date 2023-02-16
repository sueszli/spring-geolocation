package geolocation.persistence;

import geolocation.model.Location;
import geolocation.model.SearchLocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        var limit = query.getLimit();
        var specification = createFilterSpecification(query);
        var sort = Sort.by(Sort.Direction.DESC, "type");

        if (limit == null) {
            return locationJpaRepository.findAll(specification);
        } else {
            return locationJpaRepository.findAll(specification, Pageable.ofSize(limit)).getContent();
        }
    }

    private Specification<Location> createFilterSpecification(SearchLocationDto query) {
        log.trace("Creating filter specification for query: {}", query);

        Specification<Location> matchesType = null;
        if (query.getType() != null) {
            matchesType = (root, c, builder) -> builder.equal(root.get("type"), query.getType());
        }

        Specification<Location> isInRectangle = null;
        if ((query.getP1() != null) && (query.getP2() != null)) {
            var minQueryLat = Math.min(query.getP1().getLat(), query.getP2().getLat());
            var maxQueryLat = Math.max(query.getP1().getLat(), query.getP2().getLat());
            var minQueryLng = Math.min(query.getP1().getLng(), query.getP2().getLng());
            var maxQueryLng = Math.max(query.getP1().getLng(), query.getP2().getLng());
            isInRectangle = (root, c, builder) -> builder.and(builder.between(root.get("lat"), minQueryLat, maxQueryLat), builder.between(root.get("lng"), minQueryLng, maxQueryLng));
        }

        return Specification.where(matchesType).and(isInRectangle);
    }
}
