package br.com.insidesoftwares.commons.utils;

import br.com.insidesoftwares.commons.adapter.LocalDateAdapter;
import br.com.insidesoftwares.commons.adapter.LocalDateTimeAdapter;
import br.com.insidesoftwares.commons.adapter.LocalTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GsonUtils {

    private static Gson gson = null;

    public static Gson getInstance() {
        if(Objects.isNull(gson)) {
            gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                    .create();
        }
        return gson;
    }



}
