package es.pastorg.mpgstrava.background;

import org.apache.commons.lang.SerializationUtils;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.io.Serializable;
import java.util.Map;

public class ClientCommentsSerializer<ClientComments extends Serializable> implements Serializer<ClientComments> {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String s, ClientComments ClientComments) {
        return SerializationUtils.serialize(ClientComments);
        //return new byte[0];
    }

    @Override
    public byte[] serialize(String topic, Headers headers, ClientComments data) {
        return new byte[0];
    }

    @Override
    public void close() {

    }
}
