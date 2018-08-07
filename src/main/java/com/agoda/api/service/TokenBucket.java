package com.agoda.api.service;

/**
 * @author <a href="mailto:nazar.bukhari12@gmail.com">Nazar-E-Bukhari</a>
 * @since 8/4/18.
 */
public interface TokenBucket {

  boolean tryConsume(String requestType);
  void resetTokenAfterInterval();


}
