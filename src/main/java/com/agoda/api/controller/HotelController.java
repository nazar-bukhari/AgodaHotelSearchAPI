package com.agoda.api.controller;

import com.agoda.api.model.Hotel;
import com.agoda.api.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:nazar.bukhari12@gmail.com">Nazar-E-Bukhari</a>
 * @since 8/2/18.
 */

@RestController
public class HotelController {

  @Autowired
  private HotelService hotelService;

  @RequestMapping(value = {"/city/{cityName}/{searchOrder}", "/city/{cityName}"},
      method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<Hotel> getHotelByCityName(final @PathVariable("cityName") String cityName,
                                        final @PathVariable("searchOrder") Optional<String> searchOrder) {
      return hotelService.getHotelByCity(cityName, searchOrder);
  }

  @RequestMapping(value = {"/room/{roomCategory}/{searchOrder}", "/room/{roomCategory}"},
      method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<Hotel> getHotelByRoomCategory(final @PathVariable("roomCategory") String roomCategory,
                                            final @PathVariable("searchOrder") Optional<String> searchOrder) {
      return hotelService.getHotelByRoomCategory(roomCategory, searchOrder);
  }
}
