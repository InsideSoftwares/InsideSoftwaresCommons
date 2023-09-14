package br.com.insidesoftwares.commons.utils.filter;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.lgpd.DataMaskingService;
import br.com.insidesoftwares.commons.utils.GsonUtils;
import br.com.insidesoftwares.commons.utils.dto.TestDTO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FormatBodyUtilTest {

    @Mock
    private DataMaskingService dataMaskingService;

    @InjectMocks
    private FormatBodyUtil formatBodyUtil;

    private final Gson gson = GsonUtils.getInstance();

    @Test
    void test() {
        TestDTO testDTO = TestDTO.builder().build();
        String body = gson.toJson(createInsideSoftwaresResponse(testDTO));

        Mockito.when(dataMaskingService.applyDataMaskValueBody(any(), any(JsonElement.class))).thenReturn(gson.toJsonTree(null));
        Mockito.when(dataMaskingService.applyDataMaskValueBody("enable", gson.toJsonTree(false))).thenReturn(gson.toJsonTree(false));
        Mockito.when(dataMaskingService.applyDataMaskValueBody("age", gson.toJsonTree(0))).thenReturn(gson.toJsonTree(0));

        String bodyResponse = formatBodyUtil.formatBody(body);

        assertEquals(body, bodyResponse);
    }

    private InsideSoftwaresResponseDTO createInsideSoftwaresResponse(final TestDTO testDTO) {

        return InsideSoftwaresResponseDTO.builder()
                .data(testDTO)
                .build();
    }
}
