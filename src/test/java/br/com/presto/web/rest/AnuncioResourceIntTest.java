package br.com.presto.web.rest;

import br.com.presto.IPrestoApp;

import br.com.presto.domain.Anuncio;
import br.com.presto.repository.AnuncioRepository;
import br.com.presto.service.AnuncioService;
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
 * Test class for the AnuncioResource REST controller.
 *
 * @see AnuncioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IPrestoApp.class)
public class AnuncioResourceIntTest {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Float DEFAULT_PRECO = 1F;
    private static final Float UPDATED_PRECO = 2F;

    @Autowired
    private AnuncioRepository anuncioRepository;
    
    @Autowired
    private AnuncioService anuncioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnuncioMockMvc;

    private Anuncio anuncio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnuncioResource anuncioResource = new AnuncioResource(anuncioService);
        this.restAnuncioMockMvc = MockMvcBuilders.standaloneSetup(anuncioResource)
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
    public static Anuncio createEntity(EntityManager em) {
        Anuncio anuncio = new Anuncio()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .preco(DEFAULT_PRECO);
        return anuncio;
    }

    @Before
    public void initTest() {
        anuncio = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnuncio() throws Exception {
        int databaseSizeBeforeCreate = anuncioRepository.findAll().size();

        // Create the Anuncio
        restAnuncioMockMvc.perform(post("/api/anuncios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anuncio)))
            .andExpect(status().isCreated());

        // Validate the Anuncio in the database
        List<Anuncio> anuncioList = anuncioRepository.findAll();
        assertThat(anuncioList).hasSize(databaseSizeBeforeCreate + 1);
        Anuncio testAnuncio = anuncioList.get(anuncioList.size() - 1);
        assertThat(testAnuncio.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testAnuncio.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAnuncio.getPreco()).isEqualTo(DEFAULT_PRECO);
    }

    @Test
    @Transactional
    public void createAnuncioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anuncioRepository.findAll().size();

        // Create the Anuncio with an existing ID
        anuncio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnuncioMockMvc.perform(post("/api/anuncios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anuncio)))
            .andExpect(status().isBadRequest());

        // Validate the Anuncio in the database
        List<Anuncio> anuncioList = anuncioRepository.findAll();
        assertThat(anuncioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAnuncios() throws Exception {
        // Initialize the database
        anuncioRepository.saveAndFlush(anuncio);

        // Get all the anuncioList
        restAnuncioMockMvc.perform(get("/api/anuncios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anuncio.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(DEFAULT_PRECO.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getAnuncio() throws Exception {
        // Initialize the database
        anuncioRepository.saveAndFlush(anuncio);

        // Get the anuncio
        restAnuncioMockMvc.perform(get("/api/anuncios/{id}", anuncio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anuncio.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.preco").value(DEFAULT_PRECO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAnuncio() throws Exception {
        // Get the anuncio
        restAnuncioMockMvc.perform(get("/api/anuncios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnuncio() throws Exception {
        // Initialize the database
        anuncioService.save(anuncio);

        int databaseSizeBeforeUpdate = anuncioRepository.findAll().size();

        // Update the anuncio
        Anuncio updatedAnuncio = anuncioRepository.findById(anuncio.getId()).get();
        // Disconnect from session so that the updates on updatedAnuncio are not directly saved in db
        em.detach(updatedAnuncio);
        updatedAnuncio
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .preco(UPDATED_PRECO);

        restAnuncioMockMvc.perform(put("/api/anuncios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnuncio)))
            .andExpect(status().isOk());

        // Validate the Anuncio in the database
        List<Anuncio> anuncioList = anuncioRepository.findAll();
        assertThat(anuncioList).hasSize(databaseSizeBeforeUpdate);
        Anuncio testAnuncio = anuncioList.get(anuncioList.size() - 1);
        assertThat(testAnuncio.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testAnuncio.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAnuncio.getPreco()).isEqualTo(UPDATED_PRECO);
    }

    @Test
    @Transactional
    public void updateNonExistingAnuncio() throws Exception {
        int databaseSizeBeforeUpdate = anuncioRepository.findAll().size();

        // Create the Anuncio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnuncioMockMvc.perform(put("/api/anuncios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anuncio)))
            .andExpect(status().isBadRequest());

        // Validate the Anuncio in the database
        List<Anuncio> anuncioList = anuncioRepository.findAll();
        assertThat(anuncioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnuncio() throws Exception {
        // Initialize the database
        anuncioService.save(anuncio);

        int databaseSizeBeforeDelete = anuncioRepository.findAll().size();

        // Get the anuncio
        restAnuncioMockMvc.perform(delete("/api/anuncios/{id}", anuncio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Anuncio> anuncioList = anuncioRepository.findAll();
        assertThat(anuncioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anuncio.class);
        Anuncio anuncio1 = new Anuncio();
        anuncio1.setId(1L);
        Anuncio anuncio2 = new Anuncio();
        anuncio2.setId(anuncio1.getId());
        assertThat(anuncio1).isEqualTo(anuncio2);
        anuncio2.setId(2L);
        assertThat(anuncio1).isNotEqualTo(anuncio2);
        anuncio1.setId(null);
        assertThat(anuncio1).isNotEqualTo(anuncio2);
    }
}
