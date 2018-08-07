package com.agoda.api.service;

import com.agoda.api.model.Hotel;

import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:nazar.bukhari12@gmail.com">Nazar-E-Bukhari</a>
 * @since 8/2/18.
 */
public interface HotelService {

  public List<Hotel> getHotelByCity(String city, Optional<String> searchOrder);
  public List<Hotel> getHotelByRoomCategory(String roomCategory, Optional<String> searchOrder);
}
