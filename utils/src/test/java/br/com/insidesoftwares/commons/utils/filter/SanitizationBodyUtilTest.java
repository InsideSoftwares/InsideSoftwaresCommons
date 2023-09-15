package br.com.insidesoftwares.commons.utils.filter;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.lgpd.DataMaskingService;
import br.com.insidesoftwares.commons.utils.GsonUtils;
import br.com.insidesoftwares.commons.utils.dto.TestDTO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.netty.util.internal.StringUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SanitizationBodyUtilTest {

    @Mock
    private DataMaskingService dataMaskingService;

    @InjectMocks
    private SanitizationBodyUtil sanitizationBodyUtil;

    private final Gson gson = GsonUtils.getInstance();
    private static final String TAX_IDENTIFIER = "111.222.333-44";
    private static final String BODY_IS_EMPTY = "Does not have Body";

    @Test
    void shouldReturnTheSameBodyWhenEnteringStringThatIsNotJson() {
        String body = "Test.body";

        String bodyResponse = sanitizationBodyUtil.sanitizeBody(body);

        assertEquals(body, bodyResponse);
    }

    @Test
    void shouldReturnMessageOfNotHavingBodyWhenEnteringEmptyString() {
        String bodyResponse = sanitizationBodyUtil.sanitizeBody(StringUtil.EMPTY_STRING);

        assertEquals(BODY_IS_EMPTY, bodyResponse);
    }

    @Test
    void shouldSanitizeTheBodyWithoutAlteringAnyOriginalRecords() {
        TestDTO testDTO = createTestDTO(TAX_IDENTIFIER, false);
        String body = gson.toJson(createInsideSoftwaresResponse(testDTO));

        Mockito.when(dataMaskingService.applyDataMaskValueBody(any(), any(JsonElement.class))).thenReturn(gson.toJsonTree(null));
        Mockito.when(dataMaskingService.applyDataMaskValueBody("taxIdentifier", gson.toJsonTree(TAX_IDENTIFIER))).thenReturn(gson.toJsonTree(TAX_IDENTIFIER));
        Mockito.when(dataMaskingService.applyDataMaskValueBody("enable", gson.toJsonTree(false))).thenReturn(gson.toJsonTree(false));
        Mockito.when(dataMaskingService.applyDataMaskValueBody("age", gson.toJsonTree(0))).thenReturn(gson.toJsonTree(0));

        String bodyResponse = sanitizationBodyUtil.sanitizeBody(body);

        assertEquals(body, bodyResponse);
    }

    @Test
    void shouldSanitizeTheBodyByChangingTheTaxIdentifierRecord() {
        TestDTO testDTO = createTestDTO(TAX_IDENTIFIER, false);
        String body = gson.toJson(createInsideSoftwaresResponse(testDTO));
        TestDTO testDTOExpected = createTestDTO("*******", false);
        String bodyExpected = gson.toJson(createInsideSoftwaresResponse(testDTOExpected));

        Mockito.when(dataMaskingService.applyDataMaskValueBody(any(), any(JsonElement.class))).thenReturn(gson.toJsonTree(null));
        Mockito.when(dataMaskingService.applyDataMaskValueBody("taxIdentifier", gson.toJsonTree(TAX_IDENTIFIER))).thenReturn(gson.toJsonTree("*******"));
        Mockito.when(dataMaskingService.applyDataMaskValueBody("enable", gson.toJsonTree(false))).thenReturn(gson.toJsonTree(false));
        Mockito.when(dataMaskingService.applyDataMaskValueBody("age", gson.toJsonTree(0))).thenReturn(gson.toJsonTree(0));

        String bodyResponse = sanitizationBodyUtil.sanitizeBody(body);

        assertEquals(bodyExpected, bodyResponse);
    }

    @Test
    void shouldSanitizeTheBodyByChangingTheTaxIdentifierRecordOfBodyAndList() {
        TestDTO testDTO = createTestDTO(TAX_IDENTIFIER, true);
        String body = gson.toJson(createInsideSoftwaresResponse(testDTO));
        TestDTO testDTOExpected = createTestDTO("*******", true);
        String bodyExpected = gson.toJson(createInsideSoftwaresResponse(testDTOExpected));

        Mockito.when(dataMaskingService.applyDataMaskValueBody(any(), any(JsonElement.class))).thenReturn(gson.toJsonTree(null));
        Mockito.when(
                    dataMaskingService.applyDataMaskValueBody(
                            "testDTOs",
                            gson.toJsonTree(createTestDTO("*******", false))
                    )
                ).thenReturn(
                        gson.toJsonTree(
                                Set.of(createTestDTO("*******", false))
                        )
                );
        Mockito.when(dataMaskingService.applyDataMaskValueBody("taxIdentifier", gson.toJsonTree(TAX_IDENTIFIER))).thenReturn(gson.toJsonTree("*******"));
        Mockito.when(dataMaskingService.applyDataMaskValueBody("enable", gson.toJsonTree(false))).thenReturn(gson.toJsonTree(false));
        Mockito.when(dataMaskingService.applyDataMaskValueBody("age", gson.toJsonTree(0))).thenReturn(gson.toJsonTree(0));

        String bodyResponse = sanitizationBodyUtil.sanitizeBody(body);

        assertEquals(bodyExpected, bodyResponse);
    }

    @Test
    void shouldSanitizeTheBodyByChangingTheTaxIdentifierRecordOfBodyAndList1() {
        TestDTO testDTO = createTestDTO(TAX_IDENTIFIER, true);
        String body = gson.toJson(createInsideSoftwaresResponse(testDTO));
        InputStream bodyInputStream = new ByteArrayInputStream( body.getBytes() );

        TestDTO testDTOExpected = createTestDTO("*******", true);
        String bodyExpected = gson.toJson(createInsideSoftwaresResponse(testDTOExpected));

        Mockito.when(dataMaskingService.applyDataMaskValueBody(any(), any(JsonElement.class))).thenReturn(gson.toJsonTree(null));
        Mockito.when(
                dataMaskingService.applyDataMaskValueBody(
                        "testDTOs",
                        gson.toJsonTree(createTestDTO("*******", false))
                )
        ).thenReturn(
                gson.toJsonTree(
                        Set.of(createTestDTO("*******", false))
                )
        );
        Mockito.when(dataMaskingService.applyDataMaskValueBody("taxIdentifier", gson.toJsonTree(TAX_IDENTIFIER))).thenReturn(gson.toJsonTree("*******"));
        Mockito.when(dataMaskingService.applyDataMaskValueBody("enable", gson.toJsonTree(false))).thenReturn(gson.toJsonTree(false));
        Mockito.when(dataMaskingService.applyDataMaskValueBody("age", gson.toJsonTree(0))).thenReturn(gson.toJsonTree(0));

        String bodyResponse = sanitizationBodyUtil.sanitizeBody(bodyInputStream);

        assertEquals(bodyExpected, bodyResponse);
    }

    private TestDTO createTestDTO(final String taxIdentifier, final boolean addListTestDTO) {
        Set<TestDTO> testDTOs = null;
        if(addListTestDTO) {
            testDTOs = new HashSet<>();
            testDTOs.add(
                    TestDTO.builder().taxIdentifier(taxIdentifier).build()
            );
        }

        return TestDTO.builder()
                .taxIdentifier(taxIdentifier)
                .testDTOs(testDTOs)
                .build();
    }

    private InsideSoftwaresResponseDTO createInsideSoftwaresResponse(final TestDTO testDTO) {

        return InsideSoftwaresResponseDTO.builder()
                .data(testDTO)
                .build();
    }
}
