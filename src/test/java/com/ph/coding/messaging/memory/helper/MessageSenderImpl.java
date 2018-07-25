package com.ph.coding.messaging.memory.helper;

import com.ph.coding.messaging.MessageSender;
import com.ph.coding.messaging.MessagingException;

public class MessageSenderImpl implements MessageSender {

    private final Topic topic;

    public MessageSenderImpl(Topic topic) {
        this.topic = topic;
    }

    @Override
    public String getTopic() {
        return topic.getName();
    }

    @Override
    public void sendMessage(byte[] message) throws MessagingException {
        topic.publish(new MessageImpl(message));
    }
}
