package br.com.insidesoftwares.commons.lgpd;

import br.com.insidesoftwares.commons.utils.GsonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class SanitizationBodyComponent {

    private final DataMaskingService dataMaskingService;
    private static final String BODY_IS_EMPTY = "Does not have Body";
    private final Gson gson = GsonUtils.getInstance();

    public String sanitizeBody(final InputStream bodyInputStream){
        log.info("Starting sanitize body");
        String bodyValue = new BufferedReader(
                new InputStreamReader(bodyInputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        return sanitizeBody(bodyValue);
    }

    public String sanitizeBody(final String bodyValue){
        log.info("Starting sanitize body");
        if(!bodyValue.isEmpty()) {
            try {
                JsonObject bodyJson = gson.fromJson(bodyValue, JsonObject.class);
                if (Objects.nonNull(bodyJson)) {
                    bodyJson.keySet()
                            .forEach(key -> {
                                        JsonElement jsonElementResponse = getValueResponse(bodyJson.get(key));
                                        bodyJson.add(key, jsonElementResponse);
                                    }
                            );

                    return gson.toJson(bodyJson);
                }
            } catch (Exception exception) {
                log.error("Erro format Body", exception);
                return bodyValue;
            }
        }
        return BODY_IS_EMPTY;
    }

    private JsonElement getValueResponse(final JsonElement jsonElement) {
        if(jsonElement instanceof JsonObject jsonObject) {
            jsonObject.keySet()
                    .forEach( key -> {
                            JsonElement jsonElementResponse = getValueResponse(jsonObject.get(key));
                            jsonObject.add(key, dataMaskingService.applyDataMaskValueBody(key, jsonElementResponse));
                        }
                    );
            return jsonObject;
        } else {
            if(jsonElement instanceof JsonArray jsonArray && !jsonArray.asList().isEmpty()) {
                if(jsonArray.asList().get(0) instanceof JsonObject jsonObjectList){
                    jsonObjectList.keySet()
                            .forEach( key -> {
                                    JsonElement jsonElementResponse = getValueResponse(jsonObjectList.get(key));
                                    jsonObjectList.add(key, dataMaskingService.applyDataMaskValueBody(key, jsonElementResponse));
                                }
                            );
                    return jsonObjectList;
                }
            }
            return jsonElement;
        }
    }



}
