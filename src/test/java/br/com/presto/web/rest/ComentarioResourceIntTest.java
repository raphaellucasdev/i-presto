package br.com.presto.web.rest;

import br.com.presto.IPrestoApp;

import br.com.presto.domain.Comentario;
import br.com.presto.repository.ComentarioRepository;
import br.com.presto.service.ComentarioService;
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
 * Test class for the ComentarioResource REST controller.
 *
 * @see ComentarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IPrestoApp.class)
public class ComentarioResourceIntTest {

    private static final String DEFAULT_MENSAGEM = "AAAAAAAAAA";
    private static final String UPDATED_MENSAGEM = "BBBBBBBBBB";

    private static final Integer DEFAULT_AVALIACAO = 1;
    private static final Integer UPDATED_AVALIACAO = 2;

    @Autowired
    private ComentarioRepository comentarioRepository;
    
    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComentarioMockMvc;

    private Comentario comentario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComentarioResource comentarioResource = new ComentarioResource(comentarioService);
        this.restComentarioMockMvc = MockMvcBuilders.standaloneSetup(comentarioResource)
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
    public static Comentario createEntity(EntityManager em) {
        Comentario comentario = new Comentario()
            .mensagem(DEFAULT_MENSAGEM)
            .avaliacao(DEFAULT_AVALIACAO);
        return comentario;
    }

    @Before
    public void initTest() {
        comentario = createEntity(em);
    }

    @Test
    @Transactional
    public void createComentario() throws Exception {
        int databaseSizeBeforeCreate = comentarioRepository.findAll().size();

        // Create the Comentario
        restComentarioMockMvc.perform(post("/api/comentarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentario)))
            .andExpect(status().isCreated());

        // Validate the Comentario in the database
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeCreate + 1);
        Comentario testComentario = comentarioList.get(comentarioList.size() - 1);
        assertThat(testComentario.getMensagem()).isEqualTo(DEFAULT_MENSAGEM);
        assertThat(testComentario.getAvaliacao()).isEqualTo(DEFAULT_AVALIACAO);
    }

    @Test
    @Transactional
    public void createComentarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comentarioRepository.findAll().size();

        // Create the Comentario with an existing ID
        comentario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComentarioMockMvc.perform(post("/api/comentarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentario)))
            .andExpect(status().isBadRequest());

        // Validate the Comentario in the database
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllComentarios() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);

        // Get all the comentarioList
        restComentarioMockMvc.perform(get("/api/comentarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentario.getId().intValue())))
            .andExpect(jsonPath("$.[*].mensagem").value(hasItem(DEFAULT_MENSAGEM.toString())))
            .andExpect(jsonPath("$.[*].avaliacao").value(hasItem(DEFAULT_AVALIACAO)));
    }
    
    @Test
    @Transactional
    public void getComentario() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);

        // Get the comentario
        restComentarioMockMvc.perform(get("/api/comentarios/{id}", comentario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comentario.getId().intValue()))
            .andExpect(jsonPath("$.mensagem").value(DEFAULT_MENSAGEM.toString()))
            .andExpect(jsonPath("$.avaliacao").value(DEFAULT_AVALIACAO));
    }

    @Test
    @Transactional
    public void getNonExistingComentario() throws Exception {
        // Get the comentario
        restComentarioMockMvc.perform(get("/api/comentarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComentario() throws Exception {
        // Initialize the database
        comentarioService.save(comentario);

        int databaseSizeBeforeUpdate = comentarioRepository.findAll().size();

        // Update the comentario
        Comentario updatedComentario = comentarioRepository.findById(comentario.getId()).get();
        // Disconnect from session so that the updates on updatedComentario are not directly saved in db
        em.detach(updatedComentario);
        updatedComentario
            .mensagem(UPDATED_MENSAGEM)
            .avaliacao(UPDATED_AVALIACAO);

        restComentarioMockMvc.perform(put("/api/comentarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComentario)))
            .andExpect(status().isOk());

        // Validate the Comentario in the database
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeUpdate);
        Comentario testComentario = comentarioList.get(comentarioList.size() - 1);
        assertThat(testComentario.getMensagem()).isEqualTo(UPDATED_MENSAGEM);
        assertThat(testComentario.getAvaliacao()).isEqualTo(UPDATED_AVALIACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingComentario() throws Exception {
        int databaseSizeBeforeUpdate = comentarioRepository.findAll().size();

        // Create the Comentario

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComentarioMockMvc.perform(put("/api/comentarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentario)))
            .andExpect(status().isBadRequest());

        // Validate the Comentario in the database
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComentario() throws Exception {
        // Initialize the database
        comentarioService.save(comentario);

        int databaseSizeBeforeDelete = comentarioRepository.findAll().size();

        // Get the comentario
        restComentarioMockMvc.perform(delete("/api/comentarios/{id}", comentario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Comentario> comentarioList = comentarioRepository.findAll();
        assertThat(comentarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comentario.class);
        Comentario comentario1 = new Comentario();
        comentario1.setId(1L);
        Comentario comentario2 = new Comentario();
        comentario2.setId(comentario1.getId());
        assertThat(comentario1).isEqualTo(comentario2);
        comentario2.setId(2L);
        assertThat(comentario1).isNotEqualTo(comentario2);
        comentario1.setId(null);
        assertThat(comentario1).isNotEqualTo(comentario2);
    }
}
