package com.example.demo;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
class ApartamentoControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void administradorPuedeConsultarApartamentos() throws Exception {
        mockMvc.perform(get("/api/apartamentos").header("X-User-Role", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].numero").value("101"));
    }

    @Test
    void propietarioSoloConsultaSuApartamento() throws Exception {
        mockMvc.perform(get("/api/apartamentos/mio").header("X-User-Id", "prop-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero").value("101"))
                .andExpect(jsonPath("$.propietarioId").value("prop-001"));

        mockMvc.perform(get("/api/apartamentos/mio"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void administradorPuedeRegistrarApartamento() throws Exception {
        mockMvc.perform(post("/api/apartamentos")
                        .header("X-User-Role", "ADMIN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"numero":"303","torre":"C","area":75.5,"propietarioId":"prop-003"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numero").value("303"))
                .andExpect(jsonPath("$.torre").value("C"));

        mockMvc.perform(post("/api/apartamentos")
                        .header("X-User-Role", "PROPIETARIO")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numero\":\"304\",\"torre\":\"C\",\"area\":60.0,\"propietarioId\":\"prop-004\"}"))
                .andExpect(status().isForbidden());
    }
}
