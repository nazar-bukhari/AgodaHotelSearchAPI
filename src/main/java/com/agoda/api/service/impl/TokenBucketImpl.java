package com.agoda.api.service.impl;

import com.agoda.api.helper.CommonHelper;
import com.agoda.api.service.TokenBucket;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Properties;

/**
 * @author <a href="mailto:nazar.bukhari12@gmail.com">Nazar-E-Bukhari</a>
 * @since 8/4/18.
 */
public class TokenBucketImpl implements TokenBucket {

  private int cityAPIAvailableTokens = 1;
  private int roomAPIAvailableTokens = 1;
  private Properties properties;

  private LocalTime startTime = LocalTime.now();
  private static TokenBucketImpl tokenBucket = null;

  private TokenBucketImpl() {
    properties = CommonHelper.getProperties();
  }

  public static TokenBucketImpl getInstance(){

    if(tokenBucket == null){
      tokenBucket = new TokenBucketImpl();
    }
    return tokenBucket;
  }

  @Override
  public boolean tryConsume(String requestType) {

    resetTokenAfterInterval();
    int defaultAPITokenCapacity = Integer.parseInt(properties.getProperty("defaultAPITokenCapacity"));

    if(requestType.equals("city")){

      int cityAPITokenCapacity = (properties.containsKey("cityAPITokenCapacity") &&
                                (!properties.getProperty("cityAPITokenCapacity").isEmpty())) ?
                                Integer.parseInt(properties.getProperty("cityAPITokenCapacity")) : defaultAPITokenCapacity;

      if(cityAPITokenCapacity > cityAPIAvailableTokens){
        cityAPIAvailableTokens++;
        return true;
      }
    }
    else if(requestType.equals("room")){

      int roomAPITokenCapacity = (properties.containsKey("roomAPITokenCapacity") &&
              (!properties.getProperty("roomAPITokenCapacity").isEmpty())) ?
              Integer.parseInt(properties.getProperty("roomAPITokenCapacity")) : defaultAPITokenCapacity;

      if(roomAPITokenCapacity > roomAPIAvailableTokens){
        roomAPIAvailableTokens++;
        return true;
      }
    }
    return false;
  }

  @Override
  public void resetTokenAfterInterval() {

    int defaultTime = 10;
    int cityAPITimeRange = (properties.containsKey("cityAPITimeRange") &&
            (!properties.getProperty("cityAPITimeRange").isEmpty())) ?
            Integer.parseInt(properties.getProperty("cityAPITimeRange")) : defaultTime;

    int roomAPITimeRange = (properties.containsKey("roomAPITimeRange") &&
            (!properties.getProperty("roomAPITimeRange").isEmpty())) ?
            Integer.parseInt(properties.getProperty("roomAPITimeRange")) : defaultTime;

    LocalTime currentTime = LocalTime.now();
    long elapsedTimeInSeconds = Duration.between(startTime,currentTime).getSeconds();

    if(elapsedTimeInSeconds >= cityAPITimeRange){
      cityAPIAvailableTokens = 0;
      startTime = currentTime;
    }

    if(elapsedTimeInSeconds >= roomAPITimeRange){
      roomAPIAvailableTokens = 0;
      startTime = currentTime;
    }
  }


}
