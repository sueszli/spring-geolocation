package geolocation;

import com.fasterxml.jackson.databind.ObjectMapper;
import geolocation.controller.LocationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles({"test", "datagen"}) // use in-memory db & populate some test data
@WebMvcTest(LocationController.class)
class LocationControllerUnitTests {

	private static final String baseUri = "/locations";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper jsonMapper;

}
