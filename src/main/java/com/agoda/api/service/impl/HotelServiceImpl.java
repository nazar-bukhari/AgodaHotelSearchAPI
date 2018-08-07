package com.agoda.api.service.impl;

import com.agoda.api.dao.HotelRepository;
import com.agoda.api.exception.TooManyRequestException;
import com.agoda.api.model.Hotel;
import com.agoda.api.service.HotelService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:nazar.bukhari12@gmail.com">Nazar-E-Bukhari</a>
 * @since 8/2/18.
 */
@Service
public class HotelServiceImpl implements HotelService {

  private HotelRepository hotelRepository;
  private RequestLimiterImpl tokenBucket;

  public HotelServiceImpl() {
    hotelRepository = HotelRepository.getInstance();
    tokenBucket = RequestLimiterImpl.getInstance();
  }

  //Using for Test
  public HotelServiceImpl(final HotelRepository hotelRepository, final RequestLimiterImpl tokenBucket) {
    this.hotelRepository = hotelRepository;
    this.tokenBucket = tokenBucket;
  }

  @Override
  public List<Hotel> getHotelByCity(String cityName, Optional<String> searchOrder) {

    if(tokenBucket.tryConsume("city")) {

      List<Hotel> hotelListBySearchResult = new ArrayList<>();
      List<Hotel> hotelList = getHotelRepositoryBySearchOrder(searchOrder);

      for (Hotel hotel : hotelList) {
        if (hotel.getCity().equalsIgnoreCase(cityName)) {
          hotelListBySearchResult.add(hotel);
        }
      }
      return hotelListBySearchResult;
    }
    throw new TooManyRequestException();
  }

  @Override
  public List<Hotel> getHotelByRoomCategory(String roomCategory, Optional<String> searchOrder) {

    if(tokenBucket.tryConsume("room")) {

      List<Hotel> hotelListBySearchResult = new ArrayList<>();
      List<Hotel> hotelList = getHotelRepositoryBySearchOrder(searchOrder);

      for (Hotel hotel : hotelList) {
        if (hotel.getRoom().equalsIgnoreCase(roomCategory)) {
          hotelListBySearchResult.add(hotel);
        }
      }

      return hotelListBySearchResult;
    }
    throw new TooManyRequestException();
  }

  private List<Hotel> getHotelRepositoryBySearchOrder(Optional<String> searchOrder) {

    if (searchOrder!= null && searchOrder.isPresent()) {
      if (searchOrder.get().equals("asc")) {
        return  hotelRepository.getAllByPriceAsc();
      } else if (searchOrder.get().equals("desc")) {
        return  hotelRepository.getAllByPriceDesc();
      }
    }
    return hotelRepository.getAll();
  }
}
