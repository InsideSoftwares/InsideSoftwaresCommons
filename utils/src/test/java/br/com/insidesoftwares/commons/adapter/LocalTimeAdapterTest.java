package br.com.insidesoftwares.commons.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalTimeAdapterTest {

    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalTime.class, new LocalTimeAdapter()).create();

    @Test
    void shouldSerializeDateSuccessfully() {
        String timeExpected = "\"13:12:32\"";
        LocalTime time = LocalTime.of(13, 12, 32);

        String timeResult = gson.toJson(time);

        assertEquals(timeExpected, timeResult);
    }

    @Test
    void shouldDeserializeDateSuccessfully() {
        LocalTime timeExpected = LocalTime.of(13, 12, 32);
        String time = "\"13:12:32\"";

        LocalTime timeResult = gson.fromJson(time, LocalTime.class);

        assertEquals(timeExpected, timeResult);
    }

}
