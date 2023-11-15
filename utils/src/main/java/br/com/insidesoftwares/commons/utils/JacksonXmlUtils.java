package br.com.insidesoftwares.commons.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JacksonXmlUtils {

    private static XmlMapper xmlMapper = null;

    public static XmlMapper getInstance() {
        if(Objects.isNull(xmlMapper)) {
            xmlMapper = XmlMapper.xmlBuilder()
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
                    .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
                    .build();
        }
        return xmlMapper;
    }

}
