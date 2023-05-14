package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
import aiss.gitminer.service.CommentService;
import aiss.gitminer.service.IssueService;
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

@WebMvcTest(IssueController.class)
@Import(IssueController.class)
class IssueControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private IssueRepository issueRepository;
    @MockBean private IssueService issueService;
    @MockBean private CommentService commentService;

    private Issue dummyIssue;
    private Comment dummyComment;

    @BeforeEach
    void setUp() {
        dummyIssue = new Issue();
        dummyIssue.setId(UUID.randomUUID().toString());

        dummyComment = new Comment();
    }

    @Test
    public void whenCallingFindAll_returnsIssues() throws Exception {
        when(issueService.findAll(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dummyIssue)));

        MvcResult result = mockMvc.perform(get("/issues"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Issue>>() {}))
                .isEqualTo(List.of(dummyIssue));
        verify(issueService).findAll(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), any(Pageable.class));
    }

    @Test
    public void givenValidId_whenCallingFindById_returnsIssue() throws Exception {
        when(issueService.findById("0")).thenReturn(Optional.of(dummyIssue));

        MvcResult result = mockMvc.perform(get("/issues/{id}", "0"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(objectMapper.readValue(result.getResponse().getContentAsString(), Issue.class))
                .isEqualTo(dummyIssue);
    }

    @Test
    public void givenInvalidId_whenCallingFindById_throwsEntityNotFoundException() throws Exception {
        when(issueService.findById(not(eq("0")))).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/issues/{id}", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenValidId_whenCallingFindComments_returnsComments() throws Exception {
        when(issueRepository.existsById("0")).thenReturn(true);
        when(commentService.findAll(eq("0"), isNull(), isNull(), isNull(), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dummyComment)));

        MvcResult result = mockMvc.perform(get("/issues/{id}/comments", "0"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Comment>>() {}))
                .isEqualTo(List.of(dummyComment));
    }

    @Test
    public void givenInvalidId_whenCallingFindComments_throwsEntityNotFoundException() throws Exception {
        when(issueRepository.existsById(not(eq("0")))).thenReturn(false);

        mockMvc.perform(get("/issues/{id}/comments", "1"))
                .andExpect(status().isNotFound());
    }
}
