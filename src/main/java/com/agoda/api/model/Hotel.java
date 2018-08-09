package com.agoda.api.model;

import com.opencsv.bean.CsvBindByName;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:nazar.bukhari12@gmail.com">Nazar-E-Bukhari</a>
 * @since 8/1/18.
 */
public class Hotel {

  @CsvBindByName(column = "CITY", required = true)
  @NotNull
  private String city;

  @CsvBindByName(column = "HOTELID", required = true)
  @NotNull
  private long hotelId;

  @CsvBindByName(column = "ROOM", required = true)
  @NotNull
  private String room;

  @CsvBindByName(column = "PRICE", required = true)
  @NotNull
  private double price;

  public Hotel() {
  }

  public Hotel(String city, long hotelId, String room, double price) {
    this.city = city;
    this.hotelId = hotelId;
    this.room = room;
    this.price = price;
  }

  public String getCity() {
    return city;
  }

  public void setCity(final String city) {
    this.city = city;
  }

  public long getHotelId() {
    return hotelId;
  }

  public void setHotelId(final long hotelId) {
    this.hotelId = hotelId;
  }

  public String getRoom() {
    return room;
  }

  public void setRoom(final String room) {
    this.room = room;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(final double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Hotel{" +
        "city='" + city + '\'' +
        ", hotelId='" + hotelId + '\'' +
        ", room='" + room + '\'' +
        ", price='" + price + '\'' +
        '}';
  }
}
