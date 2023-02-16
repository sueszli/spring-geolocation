package geolocation;

import com.fasterxml.jackson.databind.ObjectMapper;
import geolocation.controller.LocationController;
import geolocation.model.CreateLocationDto;
import geolocation.model.LocationTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test", "datagen"})
class LocationControllerUnitTests {

    private static final String BASE_URI = "/locations";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    public void createLocationShouldSucceed() throws Exception {
        var dto = createRandomLocationDto();
        var json = jsonMapper.writeValueAsString(dto);

        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    private CreateLocationDto createRandomLocationDto() {
        var randomName = UUID.randomUUID().toString().substring(0, 5);
        var randomLat = Math.random() * 180 - 90; // lat must be between -90 and 90
        var randomLng = Math.random() * 360 - 180; // lng must be between -180 and 180
        var randomType = LocationTypeEnum.values()[(int) (Math.random() * LocationTypeEnum.values().length)];
        return CreateLocationDto.builder()
                .name(randomName)
                .lat(randomLat)
                .lng(randomLng)
                .type(randomType)
                .build();
    }
}

