package br.com.presto.web.rest;

import br.com.presto.IPrestoApp;

import br.com.presto.domain.Prestador;
import br.com.presto.repository.PrestadorRepository;
import br.com.presto.service.PrestadorService;
import br.com.presto.web.rest.errors.ExceptionTranslator;

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


import static br.com.presto.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PrestadorResource REST controller.
 *
 * @see PrestadorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IPrestoApp.class)
public class PrestadorResourceIntTest {

    private static final Integer DEFAULT_MEDIA = 1;
    private static final Integer UPDATED_MEDIA = 2;

    private static final Integer DEFAULT_CONTADOR = 1;
    private static final Integer UPDATED_CONTADOR = 2;

    @Autowired
    private PrestadorRepository prestadorRepository;
    
    @Autowired
    private PrestadorService prestadorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrestadorMockMvc;

    private Prestador prestador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrestadorResource prestadorResource = new PrestadorResource(prestadorService);
        this.restPrestadorMockMvc = MockMvcBuilders.standaloneSetup(prestadorResource)
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
    public static Prestador createEntity(EntityManager em) {
        Prestador prestador = new Prestador()
            .media(DEFAULT_MEDIA)
            .contador(DEFAULT_CONTADOR);
        return prestador;
    }

    @Before
    public void initTest() {
        prestador = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrestador() throws Exception {
        int databaseSizeBeforeCreate = prestadorRepository.findAll().size();

        // Create the Prestador
        restPrestadorMockMvc.perform(post("/api/prestadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestador)))
            .andExpect(status().isCreated());

        // Validate the Prestador in the database
        List<Prestador> prestadorList = prestadorRepository.findAll();
        assertThat(prestadorList).hasSize(databaseSizeBeforeCreate + 1);
        Prestador testPrestador = prestadorList.get(prestadorList.size() - 1);
        assertThat(testPrestador.getMedia()).isEqualTo(DEFAULT_MEDIA);
        assertThat(testPrestador.getContador()).isEqualTo(DEFAULT_CONTADOR);
    }

    @Test
    @Transactional
    public void createPrestadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prestadorRepository.findAll().size();

        // Create the Prestador with an existing ID
        prestador.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrestadorMockMvc.perform(post("/api/prestadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestador)))
            .andExpect(status().isBadRequest());

        // Validate the Prestador in the database
        List<Prestador> prestadorList = prestadorRepository.findAll();
        assertThat(prestadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrestadors() throws Exception {
        // Initialize the database
        prestadorRepository.saveAndFlush(prestador);

        // Get all the prestadorList
        restPrestadorMockMvc.perform(get("/api/prestadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestador.getId().intValue())))
            .andExpect(jsonPath("$.[*].media").value(hasItem(DEFAULT_MEDIA)))
            .andExpect(jsonPath("$.[*].contador").value(hasItem(DEFAULT_CONTADOR)));
    }
    
    @Test
    @Transactional
    public void getPrestador() throws Exception {
        // Initialize the database
        prestadorRepository.saveAndFlush(prestador);

        // Get the prestador
        restPrestadorMockMvc.perform(get("/api/prestadors/{id}", prestador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prestador.getId().intValue()))
            .andExpect(jsonPath("$.media").value(DEFAULT_MEDIA))
            .andExpect(jsonPath("$.contador").value(DEFAULT_CONTADOR));
    }

    @Test
    @Transactional
    public void getNonExistingPrestador() throws Exception {
        // Get the prestador
        restPrestadorMockMvc.perform(get("/api/prestadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrestador() throws Exception {
        // Initialize the database
        prestadorService.save(prestador);

        int databaseSizeBeforeUpdate = prestadorRepository.findAll().size();

        // Update the prestador
        Prestador updatedPrestador = prestadorRepository.findById(prestador.getId()).get();
        // Disconnect from session so that the updates on updatedPrestador are not directly saved in db
        em.detach(updatedPrestador);
        updatedPrestador
            .media(UPDATED_MEDIA)
            .contador(UPDATED_CONTADOR);

        restPrestadorMockMvc.perform(put("/api/prestadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrestador)))
            .andExpect(status().isOk());

        // Validate the Prestador in the database
        List<Prestador> prestadorList = prestadorRepository.findAll();
        assertThat(prestadorList).hasSize(databaseSizeBeforeUpdate);
        Prestador testPrestador = prestadorList.get(prestadorList.size() - 1);
        assertThat(testPrestador.getMedia()).isEqualTo(UPDATED_MEDIA);
        assertThat(testPrestador.getContador()).isEqualTo(UPDATED_CONTADOR);
    }

    @Test
    @Transactional
    public void updateNonExistingPrestador() throws Exception {
        int databaseSizeBeforeUpdate = prestadorRepository.findAll().size();

        // Create the Prestador

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrestadorMockMvc.perform(put("/api/prestadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestador)))
            .andExpect(status().isBadRequest());

        // Validate the Prestador in the database
        List<Prestador> prestadorList = prestadorRepository.findAll();
        assertThat(prestadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrestador() throws Exception {
        // Initialize the database
        prestadorService.save(prestador);

        int databaseSizeBeforeDelete = prestadorRepository.findAll().size();

        // Get the prestador
        restPrestadorMockMvc.perform(delete("/api/prestadors/{id}", prestador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Prestador> prestadorList = prestadorRepository.findAll();
        assertThat(prestadorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prestador.class);
        Prestador prestador1 = new Prestador();
        prestador1.setId(1L);
        Prestador prestador2 = new Prestador();
        prestador2.setId(prestador1.getId());
        assertThat(prestador1).isEqualTo(prestador2);
        prestador2.setId(2L);
        assertThat(prestador1).isNotEqualTo(prestador2);
        prestador1.setId(null);
        assertThat(prestador1).isNotEqualTo(prestador2);
    }
}
