package org.cheekylogic.whipround.web.rest;

import org.cheekylogic.whipround.WhiproundApp;

import org.cheekylogic.whipround.domain.Whipround;
import org.cheekylogic.whipround.repository.WhiproundRepository;
import org.cheekylogic.whipround.service.WhiproundService;
import org.cheekylogic.whipround.service.dto.WhiproundDTO;
import org.cheekylogic.whipround.service.mapper.WhiproundMapper;
import org.cheekylogic.whipround.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


import static org.cheekylogic.whipround.web.rest.TestUtil.sameInstant;
import static org.cheekylogic.whipround.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.cheekylogic.whipround.domain.enumeration.WhiproundStatus;
/**
 * Test class for the WhiproundResource REST controller.
 *
 * @see WhiproundResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WhiproundApp.class)
public class WhiproundResourceIntTest {

    private static final String DEFAULT_INVITATION = "AAAAAAAAAA";
    private static final String UPDATED_INVITATION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final WhiproundStatus DEFAULT_STATUS = WhiproundStatus.PENDING;
    private static final WhiproundStatus UPDATED_STATUS = WhiproundStatus.ACTIVE;

    @Autowired
    private WhiproundRepository whiproundRepository;

    @Mock
    private WhiproundRepository whiproundRepositoryMock;

    @Autowired
    private WhiproundMapper whiproundMapper;
    

    @Mock
    private WhiproundService whiproundServiceMock;

    @Autowired
    private WhiproundService whiproundService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWhiproundMockMvc;

    private Whipround whipround;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WhiproundResource whiproundResource = new WhiproundResource(whiproundService);
        this.restWhiproundMockMvc = MockMvcBuilders.standaloneSetup(whiproundResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Whipround createEntity(EntityManager em) {
        Whipround whipround = new Whipround()
            .invitation(DEFAULT_INVITATION)
            .imageUrl(DEFAULT_IMAGE_URL)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .status(DEFAULT_STATUS);
        return whipround;
    }

    @Before
    public void initTest() {
        whipround = createEntity(em);
    }

    @Test
    @Transactional
    public void createWhipround() throws Exception {
        int databaseSizeBeforeCreate = whiproundRepository.findAll().size();

        // Create the Whipround
        WhiproundDTO whiproundDTO = whiproundMapper.toDto(whipround);
        restWhiproundMockMvc.perform(post("/api/whiprounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(whiproundDTO)))
            .andExpect(status().isCreated());

        // Validate the Whipround in the database
        List<Whipround> whiproundList = whiproundRepository.findAll();
        assertThat(whiproundList).hasSize(databaseSizeBeforeCreate + 1);
        Whipround testWhipround = whiproundList.get(whiproundList.size() - 1);
        assertThat(testWhipround.getInvitation()).isEqualTo(DEFAULT_INVITATION);
        assertThat(testWhipround.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testWhipround.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testWhipround.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testWhipround.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createWhiproundWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = whiproundRepository.findAll().size();

        // Create the Whipround with an existing ID
        whipround.setId(1L);
        WhiproundDTO whiproundDTO = whiproundMapper.toDto(whipround);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWhiproundMockMvc.perform(post("/api/whiprounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(whiproundDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Whipround in the database
        List<Whipround> whiproundList = whiproundRepository.findAll();
        assertThat(whiproundList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWhiprounds() throws Exception {
        // Initialize the database
        whiproundRepository.saveAndFlush(whipround);

        // Get all the whiproundList
        restWhiproundMockMvc.perform(get("/api/whiprounds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(whipround.getId().intValue())))
            .andExpect(jsonPath("$.[*].invitation").value(hasItem(DEFAULT_INVITATION.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    public void getAllWhiproundsWithEagerRelationshipsIsEnabled() throws Exception {
        WhiproundResource whiproundResource = new WhiproundResource(whiproundServiceMock);
        when(whiproundServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restWhiproundMockMvc = MockMvcBuilders.standaloneSetup(whiproundResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restWhiproundMockMvc.perform(get("/api/whiprounds?eagerload=true"))
        .andExpect(status().isOk());

        verify(whiproundServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllWhiproundsWithEagerRelationshipsIsNotEnabled() throws Exception {
        WhiproundResource whiproundResource = new WhiproundResource(whiproundServiceMock);
            when(whiproundServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restWhiproundMockMvc = MockMvcBuilders.standaloneSetup(whiproundResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restWhiproundMockMvc.perform(get("/api/whiprounds?eagerload=true"))
        .andExpect(status().isOk());

            verify(whiproundServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getWhipround() throws Exception {
        // Initialize the database
        whiproundRepository.saveAndFlush(whipround);

        // Get the whipround
        restWhiproundMockMvc.perform(get("/api/whiprounds/{id}", whipround.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(whipround.getId().intValue()))
            .andExpect(jsonPath("$.invitation").value(DEFAULT_INVITATION.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWhipround() throws Exception {
        // Get the whipround
        restWhiproundMockMvc.perform(get("/api/whiprounds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWhipround() throws Exception {
        // Initialize the database
        whiproundRepository.saveAndFlush(whipround);

        int databaseSizeBeforeUpdate = whiproundRepository.findAll().size();

        // Update the whipround
        Whipround updatedWhipround = whiproundRepository.findById(whipround.getId()).get();
        // Disconnect from session so that the updates on updatedWhipround are not directly saved in db
        em.detach(updatedWhipround);
        updatedWhipround
            .invitation(UPDATED_INVITATION)
            .imageUrl(UPDATED_IMAGE_URL)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .status(UPDATED_STATUS);
        WhiproundDTO whiproundDTO = whiproundMapper.toDto(updatedWhipround);

        restWhiproundMockMvc.perform(put("/api/whiprounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(whiproundDTO)))
            .andExpect(status().isOk());

        // Validate the Whipround in the database
        List<Whipround> whiproundList = whiproundRepository.findAll();
        assertThat(whiproundList).hasSize(databaseSizeBeforeUpdate);
        Whipround testWhipround = whiproundList.get(whiproundList.size() - 1);
        assertThat(testWhipround.getInvitation()).isEqualTo(UPDATED_INVITATION);
        assertThat(testWhipround.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testWhipround.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWhipround.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testWhipround.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingWhipround() throws Exception {
        int databaseSizeBeforeUpdate = whiproundRepository.findAll().size();

        // Create the Whipround
        WhiproundDTO whiproundDTO = whiproundMapper.toDto(whipround);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWhiproundMockMvc.perform(put("/api/whiprounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(whiproundDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Whipround in the database
        List<Whipround> whiproundList = whiproundRepository.findAll();
        assertThat(whiproundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWhipround() throws Exception {
        // Initialize the database
        whiproundRepository.saveAndFlush(whipround);

        int databaseSizeBeforeDelete = whiproundRepository.findAll().size();

        // Get the whipround
        restWhiproundMockMvc.perform(delete("/api/whiprounds/{id}", whipround.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Whipround> whiproundList = whiproundRepository.findAll();
        assertThat(whiproundList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Whipround.class);
        Whipround whipround1 = new Whipround();
        whipround1.setId(1L);
        Whipround whipround2 = new Whipround();
        whipround2.setId(whipround1.getId());
        assertThat(whipround1).isEqualTo(whipround2);
        whipround2.setId(2L);
        assertThat(whipround1).isNotEqualTo(whipround2);
        whipround1.setId(null);
        assertThat(whipround1).isNotEqualTo(whipround2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WhiproundDTO.class);
        WhiproundDTO whiproundDTO1 = new WhiproundDTO();
        whiproundDTO1.setId(1L);
        WhiproundDTO whiproundDTO2 = new WhiproundDTO();
        assertThat(whiproundDTO1).isNotEqualTo(whiproundDTO2);
        whiproundDTO2.setId(whiproundDTO1.getId());
        assertThat(whiproundDTO1).isEqualTo(whiproundDTO2);
        whiproundDTO2.setId(2L);
        assertThat(whiproundDTO1).isNotEqualTo(whiproundDTO2);
        whiproundDTO1.setId(null);
        assertThat(whiproundDTO1).isNotEqualTo(whiproundDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(whiproundMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(whiproundMapper.fromId(null)).isNull();
    }
}
