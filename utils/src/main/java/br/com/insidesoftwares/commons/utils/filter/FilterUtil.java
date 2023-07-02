package br.com.insidesoftwares.commons.utils.filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FilterUtil {

    private static final List<String> HEADERS_NOT_SHOW_VALUE = List.of("authorization");
    private static final String NOT_VALUE = "*****";
    private static final String BODY_IS_EMPTY = "Does not have Body";

    public String formatHeadersView(final HttpHeaders headers) {
        StringBuilder headerFormatted = new StringBuilder();
        headers.keySet().forEach(headerName -> {
            String headerValue = isHeadersNotShowValue(headerName) ? NOT_VALUE : headers.get(headerName).get(0);
            headerFormatted.append("""
                    Header Name -> %s
                    Header Value -> %s
                    """.formatted(headerName, headerValue));
        });

        return headerFormatted.toString();
    }

    public String formatBody(final InputStream bodyInputStream){
        String bodyValue = new BufferedReader(
                new InputStreamReader(bodyInputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        if(!bodyValue.isEmpty()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            JsonObject bodyJson = gson.fromJson(bodyValue, JsonObject.class);
            if(Objects.nonNull(bodyJson)) {
                bodyJson.keySet()
                        .forEach(key -> {
                                    JsonElement jsonElementResponse = getValueResponse(bodyJson.get(key));
                                    bodyJson.add(key, jsonElementResponse);
                                }
                        );

                return gson.toJson(bodyJson);
            }
        }
        return BODY_IS_EMPTY;
    }

    private JsonElement getValueResponse(final JsonElement jsonElement) {
        if(jsonElement instanceof JsonObject jsonObject) {
            jsonObject.keySet()
                    .forEach( key -> {
                            JsonElement jsonElementResponse = getValueResponse(jsonObject.get(key));
                            jsonObject.add(key, jsonElementResponse);
                        }
                    );
            return jsonObject;
        } else {
            if(jsonElement instanceof JsonArray jsonArray && !jsonArray.asList().isEmpty()) {
                if(jsonArray.asList().get(0) instanceof JsonObject jsonObjectList){
                    jsonObjectList.keySet()
                            .forEach( key -> {
                                    JsonElement jsonElementResponse = getValueResponse(jsonObjectList.get(key));
                                    jsonObjectList.add(key, jsonElementResponse);
                                }
                            );
                    return jsonObjectList;
                }
                return jsonElement;
            }
            return jsonElement;
        }
    }

    private boolean isHeadersNotShowValue(final String header) {
        return HEADERS_NOT_SHOW_VALUE.stream().anyMatch(headerNotShowValue -> headerNotShowValue.equalsIgnoreCase(header));
    }

}
