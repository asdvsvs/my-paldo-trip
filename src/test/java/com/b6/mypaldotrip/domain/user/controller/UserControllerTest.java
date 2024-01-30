package com.b6.mypaldotrip.domain.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.b6.mypaldotrip.domain.user.CommonControllerTest;
import com.b6.mypaldotrip.domain.user.controller.dto.request.UserSignUpReq;
import com.b6.mypaldotrip.domain.user.controller.dto.response.UserSignUpRes;
import com.b6.mypaldotrip.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = {UserController.class})
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest extends CommonControllerTest {

    @MockBean private UserService userService;

    @Nested
    @DisplayName("회원가입 테스트")
    class 회원가입 {
        @Test
        @DisplayName("회원가입 테스트 성공")
        void 회원가입1() throws Exception {
            // given
            UserSignUpReq req =
                    UserSignUpReq.builder()
                            .email(TEST_EMAIL)
                            .username(TEST_USERNAME)
                            .password(TEST_PASSWORD)
                            .build();
            UserSignUpRes res =
                    UserSignUpRes.builder().email(TEST_EMAIL).username(TEST_USERNAME).build();
            given(userService.signup(any())).willReturn(res);

            // when
            ResultActions actions =
                    mockMvc.perform(
                            post("/api/" + versionConfig.getVersion() + "/users/signup")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding("utf-8")
                                    .content(objectMapper.writeValueAsString(req)));

            // then
            actions.andExpect(status().isCreated())
                    .andDo(
                            document(
                                    "user/signup",
                                    preprocessRequest(prettyPrint()),
                                    preprocessResponse(prettyPrint())));
        }

        @Test
        @DisplayName("회원가입 테스트 실패- dto 유효검사")
        void 회원가입2() throws Exception {
            // given
            UserSignUpReq req = UserSignUpReq.builder().build();
            UserSignUpRes res =
                    UserSignUpRes.builder().email(TEST_EMAIL).username(TEST_USERNAME).build();
            given(userService.signup(any())).willReturn(res);

            // when
            ResultActions actions =
                    mockMvc.perform(
                            post("/api/" + versionConfig.getVersion() + "/users/signup")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding("utf-8")
                                    .content(objectMapper.writeValueAsString(req)));

            // then
            actions.andExpect(status().isBadRequest());
        }
    }
}
