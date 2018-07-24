package com.ph.coding.messaging.memory;

import com.ph.coding.messaging.MessageReceiver;
import com.ph.coding.messaging.MessageSender;
import com.ph.coding.messaging.MessagingException;
import com.ph.coding.messaging.MessagingFactory;

public class MemoryMessagingFactory implements MessagingFactory
{
  @Override
  public String getProviderName()
  {
    return "InMemory";
  }

  @Override
  public void shutdown() throws Exception
  {
    //TODO...
  }

  @Override
  public MessageSender createSender(final String topic) throws MessagingException
  {
    return null;//TODO...
  }

  @Override
  public MessageReceiver createReceiver(final String topic) throws MessagingException
  {
    return null;//TODO...
  }

  /**
   * Method which can be called by unit test code to wait for all messages to be delivered and processed. This should cater
   * for message receivers which themselves send further messages, i.e. also waiting for such subsequent messages to be consumed.
   *
   * @param timeoutMillis Overall timeout to wait for the messaging infrastructure to become idle.
   * @throws Exception If something goes wrong whilst waiting, or messages are still waiting to or being handled when the
   *                   timeout expires.
   */
  public void waitForMessages(final long timeoutMillis) throws Exception
  {
    // TODO - a proper wait/notify mechanism is needed really, but this will probably work for now...
    Thread.sleep(Math.min(500, timeoutMillis));
  }
}
