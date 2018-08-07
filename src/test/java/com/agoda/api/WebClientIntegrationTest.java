package com.agoda.api;

import com.agoda.api.controller.HotelController;
import com.agoda.api.model.Hotel;
import com.agoda.api.service.impl.HotelServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


/**
 * @author <a href="mailto:nazar.bukhari12@gmail.com">Nazar-E-Bukhari</a>
 * @since 8/7/18.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = HotelController.class,secure = false)
public class WebClientIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private HotelServiceImpl hotelService;

  @Test
  public void getHotelsByCity() throws Exception{

    List<Hotel> hotelList = new ArrayList<>();

    Hotel hotel1 = new Hotel("Bangkok", "1", "Deluxe", 1000);
    Hotel hotel2 = new Hotel("Bangkok", "2", "Deluxe", 300);
    Hotel hotel3 = new Hotel("Bangkok", "3", "Sweet Suite", 12000);

    hotelList.add(hotel1);
    hotelList.add(hotel2);
    hotelList.add(hotel3);

    when(hotelService.getHotelByCity("bangkok",null)).thenReturn(hotelList);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/city/bangkok").accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    String expectedJson = this.mapToJson(hotelList);
    String outputInJson = result.getResponse().getContentAsString();
    assertThat(outputInJson).isEqualTo(expectedJson);

  }

  private String mapToJson(Object object) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(object);
  }
}
