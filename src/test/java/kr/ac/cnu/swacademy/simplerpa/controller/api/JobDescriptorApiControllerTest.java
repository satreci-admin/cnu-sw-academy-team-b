package kr.ac.cnu.swacademy.simplerpa.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.ContentType;
import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorSaveRequestDto;
import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import kr.ac.cnu.swacademy.simplerpa.service.JobDescriptorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JobDescriptorApiController.class)
class JobDescriptorApiControllerTest {

    @MockBean
    JobDescriptorService jobDescriptorService;
    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_URL = "/api/v1";

    @Test
    void findAllDesc() {
    }

    @Test
    @DisplayName("[API][GET] 작업명세서 상세 조회")
    void findById() throws Exception {
        // Given
        Long id = 1L;
        String name = "작업명세서1";
        boolean isRepeat = false;
        given(jobDescriptorService.findById(id)).willReturn(
                new JobDescriptorResponseDto(
                        new JobDescriptorEntity(name, isRepeat, null, null)));

        // When, Then
        mockMvc.perform(
                get(BASE_URL + "/jobdescriptor/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.isRepeat").value(isRepeat))
                .andDo(print());

        verify(jobDescriptorService).findById(id);
    }

    @Test
    @DisplayName("[API][POST] 작업명세서 등록")
    void save() throws Exception {
        // Given
        Long id = 1L;
        given(jobDescriptorService.save(any()))
                .willReturn(id);

        // When, Then
        String name = "작업명세서1";
        boolean isRepeat = false;
        JobDescriptorSaveRequestDto jobDescriptorSaveRequestDto = JobDescriptorSaveRequestDto.builder()
                .name(name)
                .isRepeat(isRepeat)
                .build();
        String json = new ObjectMapper().writeValueAsString(jobDescriptorSaveRequestDto);

        mockMvc.perform(
                post(BASE_URL +  "/jobdescriptor")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(id.toString()))
                .andDo(print());

        verify(jobDescriptorService).save(any());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void execute() {
    }
}