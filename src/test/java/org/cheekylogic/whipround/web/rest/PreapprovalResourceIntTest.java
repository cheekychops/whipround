package org.cheekylogic.whipround.web.rest;

import org.cheekylogic.whipround.WhiproundApp;

import org.cheekylogic.whipround.domain.Preapproval;
import org.cheekylogic.whipround.repository.PreapprovalRepository;
import org.cheekylogic.whipround.service.PreapprovalService;
import org.cheekylogic.whipround.service.dto.PreapprovalDTO;
import org.cheekylogic.whipround.service.mapper.PreapprovalMapper;
import org.cheekylogic.whipround.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static org.cheekylogic.whipround.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PreapprovalResource REST controller.
 *
 * @see PreapprovalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WhiproundApp.class)
public class PreapprovalResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PreapprovalRepository preapprovalRepository;

    @Autowired
    private PreapprovalMapper preapprovalMapper;
    
    @Autowired
    private PreapprovalService preapprovalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPreapprovalMockMvc;

    private Preapproval preapproval;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PreapprovalResource preapprovalResource = new PreapprovalResource(preapprovalService);
        this.restPreapprovalMockMvc = MockMvcBuilders.standaloneSetup(preapprovalResource)
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
    public static Preapproval createEntity(EntityManager em) {
        Preapproval preapproval = new Preapproval()
            .name(DEFAULT_NAME);
        return preapproval;
    }

    @Before
    public void initTest() {
        preapproval = createEntity(em);
    }

    @Test
    @Transactional
    public void createPreapproval() throws Exception {
        int databaseSizeBeforeCreate = preapprovalRepository.findAll().size();

        // Create the Preapproval
        PreapprovalDTO preapprovalDTO = preapprovalMapper.toDto(preapproval);
        restPreapprovalMockMvc.perform(post("/api/preapprovals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preapprovalDTO)))
            .andExpect(status().isCreated());

        // Validate the Preapproval in the database
        List<Preapproval> preapprovalList = preapprovalRepository.findAll();
        assertThat(preapprovalList).hasSize(databaseSizeBeforeCreate + 1);
        Preapproval testPreapproval = preapprovalList.get(preapprovalList.size() - 1);
        assertThat(testPreapproval.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPreapprovalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = preapprovalRepository.findAll().size();

        // Create the Preapproval with an existing ID
        preapproval.setId(1L);
        PreapprovalDTO preapprovalDTO = preapprovalMapper.toDto(preapproval);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreapprovalMockMvc.perform(post("/api/preapprovals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preapprovalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Preapproval in the database
        List<Preapproval> preapprovalList = preapprovalRepository.findAll();
        assertThat(preapprovalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPreapprovals() throws Exception {
        // Initialize the database
        preapprovalRepository.saveAndFlush(preapproval);

        // Get all the preapprovalList
        restPreapprovalMockMvc.perform(get("/api/preapprovals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preapproval.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getPreapproval() throws Exception {
        // Initialize the database
        preapprovalRepository.saveAndFlush(preapproval);

        // Get the preapproval
        restPreapprovalMockMvc.perform(get("/api/preapprovals/{id}", preapproval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(preapproval.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPreapproval() throws Exception {
        // Get the preapproval
        restPreapprovalMockMvc.perform(get("/api/preapprovals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreapproval() throws Exception {
        // Initialize the database
        preapprovalRepository.saveAndFlush(preapproval);

        int databaseSizeBeforeUpdate = preapprovalRepository.findAll().size();

        // Update the preapproval
        Preapproval updatedPreapproval = preapprovalRepository.findById(preapproval.getId()).get();
        // Disconnect from session so that the updates on updatedPreapproval are not directly saved in db
        em.detach(updatedPreapproval);
        updatedPreapproval
            .name(UPDATED_NAME);
        PreapprovalDTO preapprovalDTO = preapprovalMapper.toDto(updatedPreapproval);

        restPreapprovalMockMvc.perform(put("/api/preapprovals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preapprovalDTO)))
            .andExpect(status().isOk());

        // Validate the Preapproval in the database
        List<Preapproval> preapprovalList = preapprovalRepository.findAll();
        assertThat(preapprovalList).hasSize(databaseSizeBeforeUpdate);
        Preapproval testPreapproval = preapprovalList.get(preapprovalList.size() - 1);
        assertThat(testPreapproval.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPreapproval() throws Exception {
        int databaseSizeBeforeUpdate = preapprovalRepository.findAll().size();

        // Create the Preapproval
        PreapprovalDTO preapprovalDTO = preapprovalMapper.toDto(preapproval);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreapprovalMockMvc.perform(put("/api/preapprovals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preapprovalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Preapproval in the database
        List<Preapproval> preapprovalList = preapprovalRepository.findAll();
        assertThat(preapprovalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePreapproval() throws Exception {
        // Initialize the database
        preapprovalRepository.saveAndFlush(preapproval);

        int databaseSizeBeforeDelete = preapprovalRepository.findAll().size();

        // Get the preapproval
        restPreapprovalMockMvc.perform(delete("/api/preapprovals/{id}", preapproval.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Preapproval> preapprovalList = preapprovalRepository.findAll();
        assertThat(preapprovalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Preapproval.class);
        Preapproval preapproval1 = new Preapproval();
        preapproval1.setId(1L);
        Preapproval preapproval2 = new Preapproval();
        preapproval2.setId(preapproval1.getId());
        assertThat(preapproval1).isEqualTo(preapproval2);
        preapproval2.setId(2L);
        assertThat(preapproval1).isNotEqualTo(preapproval2);
        preapproval1.setId(null);
        assertThat(preapproval1).isNotEqualTo(preapproval2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreapprovalDTO.class);
        PreapprovalDTO preapprovalDTO1 = new PreapprovalDTO();
        preapprovalDTO1.setId(1L);
        PreapprovalDTO preapprovalDTO2 = new PreapprovalDTO();
        assertThat(preapprovalDTO1).isNotEqualTo(preapprovalDTO2);
        preapprovalDTO2.setId(preapprovalDTO1.getId());
        assertThat(preapprovalDTO1).isEqualTo(preapprovalDTO2);
        preapprovalDTO2.setId(2L);
        assertThat(preapprovalDTO1).isNotEqualTo(preapprovalDTO2);
        preapprovalDTO1.setId(null);
        assertThat(preapprovalDTO1).isNotEqualTo(preapprovalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(preapprovalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(preapprovalMapper.fromId(null)).isNull();
    }
}
