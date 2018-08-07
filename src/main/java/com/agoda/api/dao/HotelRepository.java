package com.agoda.api.dao;

import com.agoda.api.helper.HotelUtils;
import com.agoda.api.model.Hotel;

import java.util.List;

/**
 * @author <a href="mailto:nazar.bukhari12@gmail.com">Nazar-E-Bukhari</a>
 * @since 8/3/18.
 */
public class HotelRepository {

  private static HotelRepository hotelRepository = null;
  private List<Hotel> hotelList;
  private List<Hotel> hotelListByPriceAsc;
  private List<Hotel> hotelListByPriceDesc;

  private HotelRepository(){}

  public static HotelRepository getInstance(){

    if(hotelRepository == null)
      hotelRepository = new HotelRepository();

    return hotelRepository;
  }

  public void process(List<Hotel> hotelList) {
    HotelUtils hotelUtils = new HotelUtils();
    this.hotelList = hotelList;
    this.hotelListByPriceAsc = hotelUtils.sortByPrice(hotelList, "asc");
    this.hotelListByPriceDesc = hotelUtils.sortByPrice(hotelList, "desc");
  }

  public List<Hotel> getAll(){
    return hotelList;
  }

  public List<Hotel> getAllByPriceAsc(){
    return hotelListByPriceAsc;
  }

  public List<Hotel> getAllByPriceDesc(){
    return hotelListByPriceDesc;
  }

}
