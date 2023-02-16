package geolocation;

import com.fasterxml.jackson.databind.ObjectMapper;
import geolocation.model.CreateLocationDto;
import geolocation.model.LocationTypeEnum;
import geolocation.model.SearchLocationDto;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test", "datagen"})
class LocationControllerUnitTests {

    private static final String BASE_URI = "/api/locations";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    public void createLocation_shouldSucceed() throws Exception {
        var dto = createRandomLocationDto();
        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createLocation_shouldHaveSideEffect() throws Exception {
        var dto = createRandomLocationDto();
        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        var response = mockMvc.perform(get(BASE_URI)).andExpect(status().isOk()).andReturn().getResponse();
        var str = response.getContentAsString();
        var locationsJson = new JSONArray(str);

        for (int i = 0; i < locationsJson.length(); i++) {
            var locationJson = locationsJson.getJSONObject(i);
            var name = locationJson.getString("name");
            if (name.equals(dto.getName())) {
                assertThat(locationJson.getDouble("lat")).isEqualTo(dto.getLat());
                assertThat(locationJson.getDouble("lng")).isEqualTo(dto.getLng());
                assertThat(locationJson.getString("type")).isEqualTo(dto.getType().toString());
                return;
            }
        }
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

    @Test
    public void getAllLocations_shouldSucceed() throws Exception {
        var query = SearchLocationDto.builder().build();
        mockMvc.perform(get(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(query)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllLocations_shouldReturnAllDemoData() throws Exception {
        var response = mockMvc.perform(get(BASE_URI)).andExpect(status().isOk()).andReturn().getResponse();
        var str = response.getContentAsString();

        var expectedNames = new ArrayList<String>() {{
            add("demo_data1");
            add("demo_data2");
            add("demo_data3");
            add("demo_data4");
            add("demo_data5");
        }};

        expectedNames.forEach(name -> assertThat(str).contains(name));
    }

    @Test
    public void getAllLocations_shouldPrioritizePremium() throws Exception {
        var response = mockMvc.perform(get(BASE_URI)).andExpect(status().isOk()).andReturn().getResponse();
        var str = response.getContentAsString();
        var locationsJson = new JSONArray(str);

        var idToIndex = new HashMap<String, Integer>();
        for (int i = 0; i < locationsJson.length(); i++) {
            var locationJson = locationsJson.getJSONObject(i);
            var id = locationJson.getString("id");
            idToIndex.put(id, i);
        }

        var premiumIndices = new ArrayList<Integer>();
        var standardIndices = new ArrayList<Integer>();
        idToIndex.forEach((id, index) -> {
            if (id.equals("premium")) {
                premiumIndices.add(index);
            } else {
                standardIndices.add(index);
            }
        });

        // "premium" indices must be before "standard" indices
        for (var premiumIndex : premiumIndices) {
            for (var standardIndex : standardIndices) {
                assertThat(premiumIndex).isLessThan(standardIndex);
            }
        }
    }

    @Test
    public void searchLocationsByType_shouldOnlyReturnTheSameType() throws Exception {
        var premiumQuery = SearchLocationDto.builder().type(LocationTypeEnum.premium).build();
        var response = mockMvc.perform(get(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(premiumQuery)))
                .andExpect(status().isOk()).andReturn().getResponse();
        var str = response.getContentAsString();
        var locationsJson = new JSONArray(str);

        for (int i = 0; i < locationsJson.length(); i++) {
            var locationJson = locationsJson.getJSONObject(i);
            var type = locationJson.getString("type");
            assertThat(type).isEqualTo("premium");
        }
    }

    @Test
    public void searchLocationsWithLimit_shouldReturnLimitedResults() throws Exception {
        var lim3Query = SearchLocationDto.builder().limit(3).build();
        var response = mockMvc.perform(get(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(lim3Query)))
                .andExpect(status().isOk()).andReturn().getResponse();
        var str = response.getContentAsString();
        var locationsJson = new JSONArray(str);
        assertThat(locationsJson.length()).isEqualTo(3);
    }
}

