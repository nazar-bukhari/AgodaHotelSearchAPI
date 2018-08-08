package com.agoda.api.service.impl;

import com.agoda.api.helper.CommonHelper;
import com.agoda.api.service.RequestLimiter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Properties;

/**
 * @author <a href="mailto:nazar.bukhari12@gmail.com">Nazar-E-Bukhari</a>
 * @since 8/4/18.
 */
public class RequestLimiterImpl implements RequestLimiter {

  private int cityAPIConsumedTokens = 0;
  private int roomAPIConsumedTokens = 0;
  private Properties properties;

  private LocalTime startTime = LocalTime.now();
  private static RequestLimiterImpl tokenBucket = null;
  private static Logger logger = LogManager.getLogger("RequestLimiterImpl");


  private RequestLimiterImpl() {
    properties = CommonHelper.getProperties();
  }

  public static synchronized RequestLimiterImpl getInstance(){

    if(tokenBucket == null){
      tokenBucket = new RequestLimiterImpl();
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

      synchronized (this) {
        if (cityAPITokenCapacity > cityAPIConsumedTokens) {

          cityAPIConsumedTokens++;
          logger.info("Accepting Request: "+ cityAPIConsumedTokens);
          System.out.println("Accepting Request(Testing Purpose): "+ cityAPIConsumedTokens);
          return true;
        }
      }
    }
    else if(requestType.equals("room")){

      int roomAPITokenCapacity = (properties.containsKey("roomAPITokenCapacity") &&
              (!properties.getProperty("roomAPITokenCapacity").isEmpty())) ?
              Integer.parseInt(properties.getProperty("roomAPITokenCapacity")) : defaultAPITokenCapacity;

      synchronized (this) {
        if (roomAPITokenCapacity > roomAPIConsumedTokens) {
          roomAPIConsumedTokens++;
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void resetTokenAfterInterval() {

    int defaultTime = Integer.parseInt(properties.getProperty("defaultAPITimeLimit"));
    int cityAPITimeRange = (properties.containsKey("cityAPITimeLimit") &&
            (!properties.getProperty("cityAPITimeLimit").isEmpty())) ?
            Integer.parseInt(properties.getProperty("cityAPITimeLimit")) : defaultTime;

    int roomAPITimeRange = (properties.containsKey("roomAPITimeLimit") &&
            (!properties.getProperty("roomAPITimeLimit").isEmpty())) ?
            Integer.parseInt(properties.getProperty("roomAPITimeLimit")) : defaultTime;

    LocalTime currentTime = LocalTime.now();
    long elapsedTimeInSeconds = Duration.between(startTime,currentTime).getSeconds();

    if(elapsedTimeInSeconds >= cityAPITimeRange){
      cityAPIConsumedTokens = 0;
      startTime = currentTime;
    }

    if(elapsedTimeInSeconds >= roomAPITimeRange){
      roomAPIConsumedTokens = 0;
      startTime = currentTime;
    }
  }

}
