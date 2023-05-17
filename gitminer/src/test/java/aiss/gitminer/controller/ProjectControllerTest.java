package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Issue;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import aiss.gitminer.service.CommitService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
@Import(ProjectController.class)
class ProjectControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private ProjectRepository projectRepository;
    @MockBean private CommitService commitService;
    @MockBean private IssueService issueService;

    private Project dummyProject;
    private Commit dummyCommit;
    private Issue dummyIssue;

    @BeforeEach
    void setUp() {
        dummyProject = new Project(UUID.randomUUID().toString(), "Foo", "https://bar.baz/", Collections.emptyList(), Collections.emptyList());

        dummyCommit = new Commit();
        dummyCommit.setId(UUID.randomUUID().toString());

        dummyIssue = new Issue();
        dummyIssue.setId(UUID.randomUUID().toString());
    }

    @Test
    public void givenValidProject_whenCallingCreate_projectIsSaved() throws Exception {
        when(projectRepository.save(dummyProject)).thenReturn(dummyProject);

        MvcResult result = mockMvc.perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dummyProject)))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(objectMapper.readValue(result.getResponse().getContentAsString(), Project.class)).isEqualTo(dummyProject);
        verify(projectRepository).save(dummyProject);
    }

    @Test
    public void givenInvalidProject_whenCallingCreate_throwsMethodArgumentNotValidException() throws Exception {
        mockMvc.perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new Project())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenCallingFindAll_returnsProjects() throws Exception {
        when(projectRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(dummyProject)));

        MvcResult result = mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Project>>() {})).isEqualTo(List.of(dummyProject));
        verify(projectRepository).findAll(any(Pageable.class));
    }

    @Test
    public void givenValidId_whenCallingFindById_returnsProject() throws Exception {
        when(projectRepository.findById("0")).thenReturn(Optional.of(dummyProject));

        MvcResult result = mockMvc.perform(get("/projects/{id}", "0"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(objectMapper.readValue(result.getResponse().getContentAsString(), Project.class)).isEqualTo(dummyProject);
    }

    @Test
    public void givenInvalidId_whenCallingFindById_throwsEntityNotFoundException() throws Exception {
        when(projectRepository.findById(not(eq("0")))).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/projects/{id}", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenValidId_whenCallingFindCommits_returnsCommits() throws Exception {
        when(projectRepository.existsById("0")).thenReturn(true);
        when(commitService.findAll(eq("0"), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dummyCommit)));

        MvcResult result = mockMvc.perform(get("/projects/{id}/commits", "0"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Commit>>() {}))
                .isEqualTo(List.of(dummyCommit));
    }

    @Test
    public void givenInvalidId_whenCallingFindCommits_throwsEntityNotFoundException() throws Exception {
        when(projectRepository.existsById(not(eq("0")))).thenReturn(false);

        mockMvc.perform(get("/issues/{id}/commits", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenValidId_whenCallingFindIssues_returnsIssues() throws Exception {
        when(projectRepository.existsById("0")).thenReturn(true);
        when(issueService.findAll(eq("0"), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dummyIssue)));

        MvcResult result = mockMvc.perform(get("/projects/{id}/issues", "0"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Issue>>() {}))
                .isEqualTo(List.of(dummyIssue));
    }

    @Test
    public void givenInvalidId_whenCallingFindIssues_throwsEntityNotFoundException() throws Exception {
        when(projectRepository.existsById(not(eq("0")))).thenReturn(false);

        mockMvc.perform(get("/issues/{id}/issues", "1"))
                .andExpect(status().isNotFound());
    }
}
