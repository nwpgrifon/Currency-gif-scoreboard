package ru.alfabank;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.alfabank.dto.Rates;

import java.io.IOException;

public class RatesSerializer extends StdSerializer<Rates> {

    public RatesSerializer() {
        this(null);
    }

    public RatesSerializer(Class<Rates> t) {
        super(t);
    }

    @Override
    public void serialize(Rates value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("RUB", value.getExchangeRate());
        gen.writeEndObject();
    }
}
