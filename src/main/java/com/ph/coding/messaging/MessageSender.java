package com.ph.coding.messaging;

/**
 * Note that wildcard publications are not supported!
 */
public interface MessageSender
{
  /**
   * @return This sender's topic.
   */
  String getTopic();

  /**
   * Sends a message.
   *
   * @param message The message to send.
   * @throws MessagingException If there is a message transport problem.
   */
  void sendMessage(byte[] message) throws MessagingException;
}
