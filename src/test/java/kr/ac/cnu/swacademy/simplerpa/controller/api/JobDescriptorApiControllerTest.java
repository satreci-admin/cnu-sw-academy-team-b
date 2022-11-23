package kr.ac.cnu.swacademy.simplerpa.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorListResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorSaveRequestDto;
import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;
import kr.ac.cnu.swacademy.simplerpa.service.JobDescriptorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JobDescriptorApiController.class)
class JobDescriptorApiControllerTest {

    private static final String BASE_URL = "/api/v1";
    @MockBean
    JobDescriptorService jobDescriptorService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("[API][GET] 작업명세서 리스트 조회")
    void findAllDesc() throws Exception {
        RobotEntity robotEntity = RobotEntity.builder()
                .address("127.0.0.1:22")
                .user("anonymous")
                .password("1234")
                .build();

        JobDescriptorEntity jobDescriptorEntity1 = JobDescriptorEntity.builder()
                .name("작업명세서1")
                .isRepeat(false)
                .build();

        JobDescriptorEntity jobDescriptorEntity2 = JobDescriptorEntity.builder()
                .name("작업명세서2")
                .isRepeat(false)
                .robotEntity(robotEntity)
                .build();

        // Given
        given(jobDescriptorService.findAllDesc()).willReturn(
                List.of(
                        new JobDescriptorListResponseDto(jobDescriptorEntity1),
                        new JobDescriptorListResponseDto(jobDescriptorEntity2)));

        // When, Then
        mockMvc.perform(get(BASE_URL + "/jobdescriptors"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[0].name").value(jobDescriptorEntity1.getName()))
                .andExpect(jsonPath("$[0].robotId").doesNotExist())
                .andExpect(jsonPath("$[1]").exists())
                .andExpect(jsonPath("$[1].name").value(jobDescriptorEntity2.getName()))
                .andDo(print());

        verify(jobDescriptorService).findAllDesc();
    }

    @Test
    @DisplayName("[API][GET] 작업명세서 상세 조회")
    void findById() throws Exception {
        // Given
        Long id = 1L;
        String name = "작업명세서1";
        boolean isRepeat = false;
        given(jobDescriptorService.findById(id)).willReturn(
                Optional.of(new JobDescriptorResponseDto(
                        new JobDescriptorEntity(name, isRepeat, null, null))));

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
    @DisplayName("[API][GET] 작업명세서 상세 조회 - 존재하지 않을 때 404 리턴")
    void findByIdInvalidIdExceptionThrown() throws Exception {
        // Given
        Long id = 142L;
        given(jobDescriptorService.findById(id)).willReturn(Optional.empty());

        // When, Then
        mockMvc.perform(
                        get(BASE_URL + "/jobdescriptor/" + id))
                .andExpect(status().isNotFound())
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

        String name = "작업명세서1";
        boolean isRepeat = false;
        JobDescriptorSaveRequestDto jobDescriptorSaveRequestDto = JobDescriptorSaveRequestDto.builder()
                .name(name)
                .isRepeat(isRepeat)
                .build();
        String json = new ObjectMapper().writeValueAsString(jobDescriptorSaveRequestDto);

        // When, Then
        mockMvc.perform(
                        post(BASE_URL + "/jobdescriptor")
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