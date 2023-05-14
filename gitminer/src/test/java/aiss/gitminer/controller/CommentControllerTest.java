package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.service.CommentService;
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

@WebMvcTest(CommentController.class)
@Import(CommentController.class)
class CommentControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private CommentService commentService;

    private Comment dummyComment;

    @BeforeEach
    void setUp() {
        dummyComment = new Comment();
        dummyComment.setId(UUID.randomUUID().toString());

        when(commentService.findById("0")).thenReturn(Optional.of(dummyComment));
        when(commentService.findById(not(eq("0")))).thenThrow(new EntityNotFoundException());
    }

    @Test
    public void whenCallingFindAll_returnsComments() throws Exception {
        when(commentService.findAll(isNull(), isNull(), isNull(), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dummyComment)));

        MvcResult result = mockMvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Comment>>(){}))
                .isEqualTo(List.of(dummyComment));
        verify(commentService).findAll(isNull(), isNull(), isNull(), isNull(), isNull(), any(Pageable.class));
    }

    @Test
    public void givenValidId_whenCallingFindById_returnsComment() throws Exception {
        MvcResult result = mockMvc.perform(get("/comments/{id}", "0"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(objectMapper.readValue(result.getResponse().getContentAsString(), Comment.class))
                .isEqualTo(dummyComment);
    }

    @Test
    public void givenInvalidId_whenCallingFindById_throwsEntityNotFoundException() throws Exception {
        mockMvc.perform(get("/comments/{id}", "1"))
                .andExpect(status().isNotFound());
    }
}
