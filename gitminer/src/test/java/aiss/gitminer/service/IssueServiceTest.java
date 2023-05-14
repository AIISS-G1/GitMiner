package aiss.gitminer.service;

import aiss.gitminer.repository.IssueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@MockitoSettings
class IssueServiceTest {

    @InjectMocks IssueService commitService;
    @Mock IssueRepository commitRepository;

    private Instant start;

    @BeforeEach
    void setUp() {
        this.start = Instant.now();
    }

    @Test
    void givenSinceCreatedAt_whenCallingFindAll_thenUntilCreatedAtIsNow() {
        ArgumentCaptor<Instant> until = ArgumentCaptor.forClass(Instant.class);
        when(commitRepository.findAll(isNull(), isNull(), isNull(), any(Instant.class), until.capture(), isNull(), isNull(), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(Page.empty());

        commitService.findAll(null, null, null, Instant.EPOCH, null, null, null, null, null, Pageable.unpaged());
        assertThat(until.getValue()).isBetween(this.start, Instant.now());
    }

    @Test
    void givenUntilCreatedAt_whenCallingFindAll_thenSinceCreatedAtIsEpoch() {
        ArgumentCaptor<Instant> since = ArgumentCaptor.forClass(Instant.class);
        when(commitRepository.findAll(isNull(), isNull(), isNull(), since.capture(), any(Instant.class), isNull(), isNull(), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(Page.empty());

        commitService.findAll(null, null, null, null, Instant.now(), null, null, null, null, Pageable.unpaged());
        assertThat(since.getValue()).isEqualTo(Instant.EPOCH);
    }

    @Test
    void givenSinceUpdatedAt_whenCallingFindAll_thenUntilUpdatedAtIsNow() {
        ArgumentCaptor<Instant> until = ArgumentCaptor.forClass(Instant.class);
        when(commitRepository.findAll(isNull(), isNull(), isNull(), isNull(), isNull(), any(Instant.class), until.capture(), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(Page.empty());

        commitService.findAll(null, null, null, null, null, Instant.EPOCH, null, null, null, Pageable.unpaged());
        assertThat(until.getValue()).isBetween(this.start, Instant.now());
    }

    @Test
    void givenUntilUpdatedAt_whenCallingFindAll_thenSinceUpdatedAtIsEpoch() {
        ArgumentCaptor<Instant> since = ArgumentCaptor.forClass(Instant.class);
        when(commitRepository.findAll(isNull(), isNull(), isNull(), isNull(), isNull(), since.capture(), any(Instant.class), isNull(), isNull(), any(Pageable.class)))
                .thenReturn(Page.empty());

        commitService.findAll(null, null, null, null, null, null, Instant.now(), null, null, Pageable.unpaged());
        assertThat(since.getValue()).isEqualTo(Instant.EPOCH);
    }

    @Test
    void givenSinceClosedAt_whenCallingFindAll_thenUntilClosedAtIsNow() {
        ArgumentCaptor<Instant> until = ArgumentCaptor.forClass(Instant.class);
        when(commitRepository.findAll(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), any(Instant.class), until.capture(), any(Pageable.class)))
                .thenReturn(Page.empty());

        commitService.findAll(null, null, null, null, null, null, null, Instant.EPOCH, null, Pageable.unpaged());
        assertThat(until.getValue()).isBetween(this.start, Instant.now());
    }

    @Test
    void givenUntilClosedAt_whenCallingFindAll_thenSinceClosedAtIsEpoch() {
        ArgumentCaptor<Instant> since = ArgumentCaptor.forClass(Instant.class);
        when(commitRepository.findAll(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), since.capture(), any(Instant.class), any(Pageable.class)))
                .thenReturn(Page.empty());

        commitService.findAll(null, null, null, null, null, null, null, null, Instant.now(), Pageable.unpaged());
        assertThat(since.getValue()).isEqualTo(Instant.EPOCH);
    }
}
