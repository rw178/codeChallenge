package com.ph.coding.messaging.memory.helper;

import com.ph.coding.messaging.Message;

public class MessageImpl implements Message {

    private final byte[] data;

    public MessageImpl(byte[] data) {
        this.data = data;
    }

    @Override
    public byte[] getMsg() {
        return data;
    }

    public MessageImpl copy() {
        return new MessageImpl(this.data);
    }

    @Override
    public void dispose() {
        //Do required cleanup work
    }
}
