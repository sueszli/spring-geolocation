package geolocation;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * This component is only created, if the profile {@code datagen} is active. <br>
 * You can activate this profile by adding {@code -Dspring.profiles.active=datagen} to your maven command.
 */

@Slf4j
@Component
@Profile("datagen")
@RequiredArgsConstructor
public class DataGeneratorBean {

    private final DataSource dataSource;

    @PostConstruct
    public void generateData() throws SQLException {
        log.info("Generating data…");
        try (var connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("insertTestData.sql"));
            log.info("Finished generating data without error.");
        }
    }
}
