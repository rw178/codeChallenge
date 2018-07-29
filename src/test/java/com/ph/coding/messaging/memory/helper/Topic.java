package com.ph.coding.messaging.memory.helper;

import com.ph.coding.messaging.MessagingException;

import java.util.LinkedList;
import java.util.List;

public class Topic {
    private final List<MessageReceiverImpl> receivers;
    private final String name;
    private boolean shouldBeShuttingDown = false; /*Making this volatile could be used to eliminate the
    shared lock, but care should be taken to not "miss" a shutdown*/


    public Topic(String name) {
        this.name = name;
        receivers = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public synchronized void addMessageReceiver(MessageReceiverImpl messageReceiver) throws MessagingException {
        if (shouldBeShuttingDown) {
            throw new MessagingException("Topic is marked for shutdown, no more messages accepted");
        }
        receivers.add(messageReceiver);
    }

    public synchronized void publish(MessageImpl message) throws MessagingException {
        if (shouldBeShuttingDown) {
            throw new MessagingException("Topic is marked for shutdown, no more messages accepted");
        }
        for (MessageReceiverImpl receiver : receivers) {
            receiver.getListener().onMessage(message.copy(), receiver.getTopic());
        }

        message.dispose();
    }


    public synchronized void shutdown() {
        shouldBeShuttingDown = true;
    }
}
