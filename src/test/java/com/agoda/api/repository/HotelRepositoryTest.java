package com.agoda.api.repository;

import com.agoda.api.dao.HotelRepository;
import com.agoda.api.model.Hotel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:nazar.bukhari12@gmail.com">Nazar-E-Bukhari</a>
 * @since 8/3/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelRepositoryTest {

  private HotelRepository hotelRepository;

  @Before
  public void setUp() {

    List<Hotel> hotelList = generateMockHotels();

    hotelRepository = HotelRepository.getInstance();
    hotelRepository.process(hotelList);

  }

  @Test
  public void testFindAllHotel() {
    int totalHotelNumber = hotelRepository.getAll().size();
    assertThat(totalHotelNumber).isEqualTo(4);
  }

  @Test
  public void testFindHotelByPriceAscOrder() {
    List<Hotel> hotelList = hotelRepository.getAllByPriceAsc();
    assertThat(hotelList.get(0).getPrice()).isEqualTo(300);
    assertThat(hotelList.get(3).getPrice()).isEqualTo(15000);
  }

  @Test
  public void testFindHotelByPriceDescOrder() {
    List<Hotel> hotelList = hotelRepository.getAllByPriceDesc();
    assertThat(hotelList.get(0).getPrice()).isEqualTo(15000);
    assertThat(hotelList.get(3).getPrice()).isEqualTo(300);
  }

  private List<Hotel> generateMockHotels(){

    List<Hotel> hotelList = new ArrayList<>();

    Hotel hotel1 = new Hotel("Bangkok", 1, "Deluxe", 1000);
    Hotel hotel2 = new Hotel("Bangkok", 2, "Deluxe", 300);
    Hotel hotel3 = new Hotel("Bangkok", 3, "Sweet Suite", 12000);
    Hotel hotel4 = new Hotel("Amsterdam", 4, "Sweet Suite", 15000);

    hotelList.add(hotel1);
    hotelList.add(hotel2);
    hotelList.add(hotel3);
    hotelList.add(hotel4);

    return hotelList;
  }
}
