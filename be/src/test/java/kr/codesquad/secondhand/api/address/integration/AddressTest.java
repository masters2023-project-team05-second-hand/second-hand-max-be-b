package kr.codesquad.secondhand.api.address.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.codesquad.secondhand.util.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class AddressTest extends IntegrationTest {

    @Test
    @DisplayName("다음 페이지가 있는 동네 목록 조회 요청 시 200 코드와 동네 데이터를 응답한다.")
    void read_addresses_has_next_true() throws Exception {
        mockMvc.perform(get("/api/addresses?page=0&size=2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("addresses[0].id").value(1111051500)) // 임시로 하드 코딩함, 서비스/레포지토리 로직 불러올 수 있도록 변경 필요
                .andExpect(jsonPath("addresses[0].name").value("서울특별시 종로구 청운효자동"))
                .andExpect(jsonPath("addresses[1].id").value(1111053000))
                .andExpect(jsonPath("addresses[1].name").value("서울특별시 종로구 사직동"))
                .andExpect(jsonPath("hasNext").value(true));
    }

    @Test
    @DisplayName("다음 페이지가 없는 동네 목록 조회 요청 시 200 코드와 동네 데이터를 응답한다.")
    void read_addresses_has_next_false() throws Exception {
        mockMvc.perform(get("/api/addresses?page=4&size=2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("addresses[0].id").value(1111061500)) // 임시로 하드 코딩함, 서비스/레포지토리 로직 불러올 수 있도록 변경 필요
                .andExpect(jsonPath("addresses[0].name").value("서울특별시 종로구 종로1.2.3.4가동"))
                .andExpect(jsonPath("addresses[1].id").value(1111063000))
                .andExpect(jsonPath("addresses[1].name").value("서울특별시 종로구 종로5.6가동"))
                .andExpect(jsonPath("hasNext").value(false));
    }
}
