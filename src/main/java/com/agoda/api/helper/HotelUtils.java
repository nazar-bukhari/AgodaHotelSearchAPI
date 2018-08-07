package com.agoda.api.helper;

import com.agoda.api.model.Hotel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author <a href="mailto:nazar.bukhari12@gmail.com">Nazar-E-Bukhari</a>
 * @since 8/4/18.
 */
public class HotelUtils {

  public List<Hotel> sortByPrice(List<Hotel> hotelLists, String order){
    List<Hotel> sortedHotelLists = new ArrayList<>(hotelLists);

    if (order.equals("asc")) {
      sortedHotelLists.sort(Comparator.comparing(Hotel::getPrice));
    }
    else {
      sortedHotelLists.sort(Comparator.comparing(Hotel::getPrice).reversed());
    }

    return sortedHotelLists;
  }

}
