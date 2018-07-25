package com.ph.coding.messaging.memory;

import com.ph.coding.messaging.*;
import com.ph.coding.messaging.memory.helper.MessageReceiverImpl;
import com.ph.coding.messaging.memory.helper.MessageSenderImpl;
import com.ph.coding.messaging.memory.helper.Topic;

import java.util.concurrent.ConcurrentHashMap;

public class MemoryMessagingFactory implements MessagingFactory {

    private final ConcurrentHashMap<String, Topic> map = new ConcurrentHashMap<>();
    private boolean shouldBeShuttingDown = false;

    @Override
    public String getProviderName() {
        return "InMemory";
    }

    @Override
    public void shutdown() throws Exception {
        shouldBeShuttingDown = true;
        for (Topic topic : map.values()) {
            topic.shutdown();
        }
    }

    @Override
    public MessageSender createSender(final String topic) throws MessagingException {
        if (shouldBeShuttingDown) {
            throw new MessagingException("Messaging service is shutting down, no more senders can be added.");
        }
        map.putIfAbsent(topic, new Topic(topic));
        return new MessageSenderImpl(map.get(topic));
    }

    @Override
    public MessageReceiver createReceiver(final String topic) throws MessagingException {
        if (shouldBeShuttingDown) {
            throw new MessagingException("Messaging service is shutting down, no more receivers can be added.");
        }
        map.putIfAbsent(topic, new Topic(topic));
        return new MessageReceiverImpl(map.get(topic));
    }

    /**
     * Method which can be called by unit test code to wait for all messages to be delivered and processed. This should cater
     * for message receivers which themselves send further messages, i.e. also waiting for such subsequent messages to be consumed.
     *
     * @param timeoutMillis Overall timeout to wait for the messaging infrastructure to become idle.
     * @throws Exception If something goes wrong whilst waiting, or messages are still waiting to or being handled when the
     *                   timeout expires.
     */
    public void waitForMessages(final long timeoutMillis) throws Exception {
        // TODO - a proper wait/notify mechanism is needed really, but this will probably work for now...
        Thread.sleep(Math.min(500, timeoutMillis));
    }
}
