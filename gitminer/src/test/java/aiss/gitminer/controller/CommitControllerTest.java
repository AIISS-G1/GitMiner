package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.service.CommitService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommitController.class)
@Import(CommitController.class)
class CommitControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private CommitService commitService;

    private Commit dummyCommit;

    @BeforeEach
    void setUp() {
        dummyCommit = new Commit();
        dummyCommit.setId(UUID.randomUUID().toString());

        when(commitService.findById("0")).thenReturn(Optional.of(dummyCommit));
        when(commitService.findById(not(eq("0")))).thenThrow(new EntityNotFoundException());
    }

    @Test
    public void whenCallingFindAll_returnsCommits() throws Exception {
        when(commitService.findAll(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dummyCommit)));

        MvcResult result = mockMvc.perform(get("/commits"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Commit>>(){}))
                .isEqualTo(List.of(dummyCommit));
        verify(commitService).findAll(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), any(Pageable.class));
    }

    @Test
    public void givenValidId_whenCallingFindById_returnsCommit() throws Exception {
        MvcResult result = mockMvc.perform(get("/commits/{id}", "0"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(objectMapper.readValue(result.getResponse().getContentAsString(), Commit.class))
                .isEqualTo(dummyCommit);
    }

    @Test
    public void givenInvalidId_whenCallingFindById_throwsEntityNotFoundException() throws Exception {
        mockMvc.perform(get("/commits/{id}", "1"))
                .andExpect(status().isNotFound());
    }
}
