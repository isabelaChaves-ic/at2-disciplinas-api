package br.com.isabela.at2_disciplinas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class At2DisciplinasApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveListarTodasAsDisciplinas() throws Exception {
        mockMvc.perform(get("/disciplinas"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].nome").value("Engenharia de Software III"));
    }

    @Test
    void deveBuscarDisciplinaPorIdComSucesso() throws Exception {
        mockMvc.perform(get("/disciplinas/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.sigla").value("ES3"));
    }

    @Test
    void deveRetornarNotFoundAoBuscarIdInexistente() throws Exception {
        mockMvc.perform(get("/disciplinas/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void deveCriarNovaDisciplina() throws Exception {
        Disciplina nova = new Disciplina(null, "Banco de Dados", "BD");
        String jsonBody = objectMapper.writeValueAsString(nova);

        mockMvc.perform(post("/disciplinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBody))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.nome").value("Banco de Dados"));
    }
}