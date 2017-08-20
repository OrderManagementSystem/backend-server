package com.vk.oms.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.vk.oms.model.Order;

import java.io.IOException;

public class OrderSerializer extends StdSerializer<Order> {

    public OrderSerializer() {
        this(null);
    }

    private OrderSerializer(Class<Order> t) {
        super(t);
    }

    @Override
    public void serialize(Order order, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("id", order.getId());
        gen.writeObjectField("description", order.getDescription());
        gen.writeObjectField("price", order.getPrice());
        gen.writeObjectField("status", order.getStatus());
        gen.writeObjectField("createdDate", order.getCreatedDate());
        gen.writeObjectField("customerName", order.getCustomer().getUsername());
        gen.writeObjectField("performerName",
                order.getPerformer() != null ? order.getPerformer().getUsername() : null);
        gen.writeEndObject();
    }
}
