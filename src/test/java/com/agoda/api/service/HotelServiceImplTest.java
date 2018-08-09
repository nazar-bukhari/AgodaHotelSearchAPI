package com.agoda.api.service;

import com.agoda.api.dao.HotelRepository;
import com.agoda.api.helper.HotelUtils;
import com.agoda.api.model.Hotel;
import com.agoda.api.service.impl.HotelServiceImpl;
import com.agoda.api.service.impl.RequestLimiterImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelServiceImplTest {

    private HotelServiceImpl hotelService;

    @Before
    public void setUp() throws Exception{

        List<Hotel> hotelList = generateMockHotels();

        HotelRepository hotelRepository = mock(HotelRepository.class);
        when(hotelRepository.getAll()).thenReturn(hotelList);
        when(hotelRepository.getAllByPriceAsc()).thenReturn(new HotelUtils().sortByPrice(hotelList, "asc"));
        when(hotelRepository.getAllByPriceDesc()).thenReturn(new HotelUtils().sortByPrice(hotelList, "desc"));

        RequestLimiterImpl tokenBucket = mock(RequestLimiterImpl.class);
        when(tokenBucket.tryConsume("city")).thenReturn(true);
        when(tokenBucket.tryConsume("room")).thenReturn(true);

        hotelService = new HotelServiceImpl(hotelRepository,tokenBucket);
    }

    @Test
    public void testFindAllHotelByValidRoomCategory() throws Exception{

        List<Hotel> hotelsWithNullOrder = hotelService.getHotelByRoomCategory("Deluxe", Optional.of("asc"));
        List<Hotel> hotelsWithDescOrder = hotelService.getHotelByRoomCategory("Superior", Optional.of("desc"));

        assertThat(hotelsWithNullOrder.size()).isEqualTo(3);
        assertThat(hotelsWithDescOrder.size()).isEqualTo(1);
    }

    @Test
    public void testFindHotelByNullPriceOrder() {
        List<Hotel> hotels = hotelService.getHotelByRoomCategory("Sweet Suite", null);
        assertThat(hotels.size()).isEqualTo(2);
    }

    @Test
    public void testFindHotelByEmptyPriceOrder(){
        List<Hotel> hotels = hotelService.getHotelByRoomCategory("Sweet Suite", Optional.empty());
        assertThat(hotels.size()).isEqualTo(2);
    }

    @Test
    public void testFindHotelByValidRoomCategoryOnPriceOrder() throws Exception{

        long hotelId1 = hotelService.getHotelByRoomCategory("Deluxe",Optional.of("asc")).get(0).getHotelId();
        long hotelId2 = hotelService.getHotelByRoomCategory("Deluxe",Optional.of("asc")).get(2).getHotelId();

        long hotelId3 = hotelService.getHotelByRoomCategory("Deluxe",Optional.of("desc")).get(0).getHotelId();
        long hotelId4 = hotelService.getHotelByRoomCategory("Deluxe",Optional.of("desc")).get(1).getHotelId();

        assertThat(hotelId1).isEqualTo(2);
        assertThat(hotelId2).isEqualTo(1);
        assertThat(hotelId3).isEqualTo(1);
        assertThat(hotelId4).isEqualTo(6);
    }

    @Test
    public void testFindHotelByInValidRoomCategory() throws Exception{
        List<Hotel> hotels = hotelService.getHotelByRoomCategory("Invalid Room Name", Optional.of("asc"));
        assertThat(hotels.size()).isEqualTo(0);
    }

    @Test
    public void testFindAllHotelByValidCountry() throws Exception{

        List<Hotel> hotels = hotelService.getHotelByCity("bangkok", Optional.of("asc"));
        assertThat(hotels.size()).isEqualTo(3);

        hotels = hotelService.getHotelByCity("Amsterdam",Optional.of("desc"));
        assertThat(hotels.size()).isEqualTo(1);
    }

    @Test
    public void testFindHotelByInValidCountry() throws Exception{
        List<Hotel> hotels = hotelService.getHotelByCity("Japan", Optional.of("asc"));
        assertThat(hotels.size()).isEqualTo(0);
    }

    private List<Hotel> generateMockHotels(){

        List<Hotel> hotelList = new ArrayList<>();

        Hotel hotel1 = new Hotel("Bangkok", 1, "Deluxe", 1000);
        Hotel hotel2 = new Hotel("Bangkok", 2, "Deluxe", 300);
        Hotel hotel3 = new Hotel("Bangkok", 3, "Sweet Suite", 12000);
        Hotel hotel4 = new Hotel("Amsterdam", 4, "Sweet Suite", 15000);
        Hotel hotel5 = new Hotel("Ashburn", 5, "Superior", 700);
        Hotel hotel6 = new Hotel("Ashburn", 6, "Deluxe", 500);

        hotelList.add(hotel1);
        hotelList.add(hotel2);
        hotelList.add(hotel3);
        hotelList.add(hotel4);
        hotelList.add(hotel5);
        hotelList.add(hotel6);

        return hotelList;
    }
}
