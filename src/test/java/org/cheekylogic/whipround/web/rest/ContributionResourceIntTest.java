package org.cheekylogic.whipround.web.rest;

import org.cheekylogic.whipround.WhiproundApp;

import org.cheekylogic.whipround.domain.Contribution;
import org.cheekylogic.whipround.repository.ContributionRepository;
import org.cheekylogic.whipround.service.ContributionService;
import org.cheekylogic.whipround.service.dto.ContributionDTO;
import org.cheekylogic.whipround.service.mapper.ContributionMapper;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static org.cheekylogic.whipround.web.rest.TestUtil.sameInstant;
import static org.cheekylogic.whipround.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.cheekylogic.whipround.domain.enumeration.Currency;
import org.cheekylogic.whipround.domain.enumeration.ContributionStatus;
/**
 * Test class for the ContributionResource REST controller.
 *
 * @see ContributionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WhiproundApp.class)
public class ContributionResourceIntTest {

    private static final Currency DEFAULT_CURRENCY = Currency.GBP;
    private static final Currency UPDATED_CURRENCY = Currency.GBP;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_FEE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FEE = new BigDecimal(2);

    private static final ContributionStatus DEFAULT_STATUS = ContributionStatus.COLLECTED;
    private static final ContributionStatus UPDATED_STATUS = ContributionStatus.PENDING_APPROVAL;

    private static final String DEFAULT_PAY_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PAY_KEY = "BBBBBBBBBB";

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private ContributionMapper contributionMapper;
    
    @Autowired
    private ContributionService contributionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContributionMockMvc;

    private Contribution contribution;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContributionResource contributionResource = new ContributionResource(contributionService);
        this.restContributionMockMvc = MockMvcBuilders.standaloneSetup(contributionResource)
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
    public static Contribution createEntity(EntityManager em) {
        Contribution contribution = new Contribution()
            .currency(DEFAULT_CURRENCY)
            .amount(DEFAULT_AMOUNT)
            .date(DEFAULT_DATE)
            .fee(DEFAULT_FEE)
            .status(DEFAULT_STATUS)
            .payKey(DEFAULT_PAY_KEY);
        return contribution;
    }

    @Before
    public void initTest() {
        contribution = createEntity(em);
    }

    @Test
    @Transactional
    public void createContribution() throws Exception {
        int databaseSizeBeforeCreate = contributionRepository.findAll().size();

        // Create the Contribution
        ContributionDTO contributionDTO = contributionMapper.toDto(contribution);
        restContributionMockMvc.perform(post("/api/contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributionDTO)))
            .andExpect(status().isCreated());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeCreate + 1);
        Contribution testContribution = contributionList.get(contributionList.size() - 1);
        assertThat(testContribution.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testContribution.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testContribution.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testContribution.getFee()).isEqualTo(DEFAULT_FEE);
        assertThat(testContribution.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testContribution.getPayKey()).isEqualTo(DEFAULT_PAY_KEY);
    }

    @Test
    @Transactional
    public void createContributionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contributionRepository.findAll().size();

        // Create the Contribution with an existing ID
        contribution.setId(1L);
        ContributionDTO contributionDTO = contributionMapper.toDto(contribution);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContributionMockMvc.perform(post("/api/contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContributions() throws Exception {
        // Initialize the database
        contributionRepository.saveAndFlush(contribution);

        // Get all the contributionList
        restContributionMockMvc.perform(get("/api/contributions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contribution.getId().intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].payKey").value(hasItem(DEFAULT_PAY_KEY.toString())));
    }
    
    @Test
    @Transactional
    public void getContribution() throws Exception {
        // Initialize the database
        contributionRepository.saveAndFlush(contribution);

        // Get the contribution
        restContributionMockMvc.perform(get("/api/contributions/{id}", contribution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contribution.getId().intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.fee").value(DEFAULT_FEE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.payKey").value(DEFAULT_PAY_KEY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContribution() throws Exception {
        // Get the contribution
        restContributionMockMvc.perform(get("/api/contributions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContribution() throws Exception {
        // Initialize the database
        contributionRepository.saveAndFlush(contribution);

        int databaseSizeBeforeUpdate = contributionRepository.findAll().size();

        // Update the contribution
        Contribution updatedContribution = contributionRepository.findById(contribution.getId()).get();
        // Disconnect from session so that the updates on updatedContribution are not directly saved in db
        em.detach(updatedContribution);
        updatedContribution
            .currency(UPDATED_CURRENCY)
            .amount(UPDATED_AMOUNT)
            .date(UPDATED_DATE)
            .fee(UPDATED_FEE)
            .status(UPDATED_STATUS)
            .payKey(UPDATED_PAY_KEY);
        ContributionDTO contributionDTO = contributionMapper.toDto(updatedContribution);

        restContributionMockMvc.perform(put("/api/contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributionDTO)))
            .andExpect(status().isOk());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeUpdate);
        Contribution testContribution = contributionList.get(contributionList.size() - 1);
        assertThat(testContribution.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testContribution.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testContribution.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testContribution.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testContribution.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testContribution.getPayKey()).isEqualTo(UPDATED_PAY_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingContribution() throws Exception {
        int databaseSizeBeforeUpdate = contributionRepository.findAll().size();

        // Create the Contribution
        ContributionDTO contributionDTO = contributionMapper.toDto(contribution);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContributionMockMvc.perform(put("/api/contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContribution() throws Exception {
        // Initialize the database
        contributionRepository.saveAndFlush(contribution);

        int databaseSizeBeforeDelete = contributionRepository.findAll().size();

        // Get the contribution
        restContributionMockMvc.perform(delete("/api/contributions/{id}", contribution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contribution.class);
        Contribution contribution1 = new Contribution();
        contribution1.setId(1L);
        Contribution contribution2 = new Contribution();
        contribution2.setId(contribution1.getId());
        assertThat(contribution1).isEqualTo(contribution2);
        contribution2.setId(2L);
        assertThat(contribution1).isNotEqualTo(contribution2);
        contribution1.setId(null);
        assertThat(contribution1).isNotEqualTo(contribution2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContributionDTO.class);
        ContributionDTO contributionDTO1 = new ContributionDTO();
        contributionDTO1.setId(1L);
        ContributionDTO contributionDTO2 = new ContributionDTO();
        assertThat(contributionDTO1).isNotEqualTo(contributionDTO2);
        contributionDTO2.setId(contributionDTO1.getId());
        assertThat(contributionDTO1).isEqualTo(contributionDTO2);
        contributionDTO2.setId(2L);
        assertThat(contributionDTO1).isNotEqualTo(contributionDTO2);
        contributionDTO1.setId(null);
        assertThat(contributionDTO1).isNotEqualTo(contributionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contributionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contributionMapper.fromId(null)).isNull();
    }
}
