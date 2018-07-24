package com.ph.coding.messaging.memory;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;

import com.ph.coding.messaging.Message;
import com.ph.coding.messaging.MessageReceiveListener;
import com.ph.coding.messaging.MessageReceiver;
import com.ph.coding.messaging.MessageSender;
import com.ph.coding.messaging.MessagingException;

import static org.junit.Assert.assertEquals;

public abstract class MemoryMessagingTestBase
{
  protected MemoryMessagingFactory messagingFactory;

  @Before
  public void before() throws Exception
  {
    messagingFactory = new MemoryMessagingFactory();
  }

  @After
  public void after() throws Exception
  {
    messagingFactory.shutdown();
  }

  // -------------------------------------------------------------------------------- //

  protected MessageSender newSender(final String topic) throws MessagingException
  {
    return messagingFactory.createSender(topic);
  }

  protected StoringListener newReceiver(final String topic) throws MessagingException
  {
    final StoringListener listener = new StoringListener();
    newReceiver(topic, listener);
    return listener;
  }

  protected MessageReceiver newReceiver(final String topic, final MessageReceiveListener listener) throws MessagingException
  {
    final MessageReceiver receiver = messagingFactory.createReceiver(topic);
    receiver.setListener(listener);
    return receiver;
  }

  protected void waitForMessages() throws Exception
  {
    messagingFactory.waitForMessages(5000);
  }

  protected static void checkMessages(final StoringListener listener, final String... messages)
  {
    assertEquals(messages.length, listener.messages.size());
    for(int i=0; i<messages.length; ++i)
      assertEquals("Message " + i + " different", messages[i], decode(listener.messages.get(i)));
  }

  protected static void checkTopics(final StoringListener listener, final String... topics)
  {
    assertEquals(topics.length, listener.topics.size());
    for(int i=0; i<topics.length; ++i)
      assertEquals("Topic " + i + " different", topics[i], listener.topics.get(i));
  }

  protected static void checkTopics(final StoringListener listener, final String topic, final int num)
  {
    assertEquals(num, listener.topics.size());
    for(int i=0; i<num; ++i)
      assertEquals("Topic " + i + " different", topic, listener.topics.get(i));
  }

  protected static byte[] encode(final String string)
  {
    return string.getBytes();
  }

  protected static String decode(final Message message)
  {
    return new String(message.getMsg());
  }

  protected static class StoringListener implements MessageReceiveListener
  {
    public final List<Message> messages = new ArrayList<>();
    public final List<String> topics = new ArrayList<>();

    @Override
    public void onMessage(final Message message, final String topic)
    {
      messages.add(message);
      topics.add(topic);
    }
  }
}
