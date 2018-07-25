package com.ph.coding.messaging.memory.helper;

import com.ph.coding.messaging.MessageReceiveListener;
import com.ph.coding.messaging.MessageReceiver;
import com.ph.coding.messaging.MessagingException;

public class MessageReceiverImpl implements MessageReceiver {
    private final Topic topic;
    private MessageReceiveListener messageReceiveListener;

    public MessageReceiverImpl(Topic topic)  throws MessagingException {
        this.topic = topic;
        topic.addMessageReceiver(this);
    }

    @Override
    public String getTopic() {
        return topic.getName();
    }

    @Override
    public void setListener(MessageReceiveListener messageReceiveListener) {
        this.messageReceiveListener = messageReceiveListener;
    }

    public MessageReceiveListener getListener() {
        return messageReceiveListener;
    }
}
