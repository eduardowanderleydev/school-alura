package br.com.alura.school.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void should_retrieve_user_by_username() throws Exception {
        userRepository.save(new User("ana", "ana@email.com"));

        mockMvc.perform(get("/users/ana")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username", is("ana")))
                .andExpect(jsonPath("$.email", is("ana@email.com")));
    }

    @Test
    void not_found_when_user_does_not_exist() throws Exception {
        mockMvc.perform(get("/users/non-existent")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_add_new_user() throws Exception {
        NewUserRequest newUser = new NewUserRequest("alex", "alex@email.com");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newUser)))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/users/alex"));
    }

    @ParameterizedTest
    @CsvSource({
            ", maria@email.com",
            "'', maria@email.com",
            "'    ', maria@email.com",
            "maria, ",
            "maria, ''",
            "maria, '    '",
            "maria, not-an-email",
            "an-username-that-is-really-really-big , maria@email.com"
    })
    void should_validate_bad_user_requests(String username, String email) throws Exception {
        NewUserRequest newUser = new NewUserRequest(username, email);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_not_allow_duplication_of_username() throws Exception {
        userRepository.save(new User("maria", "maria@email.com"));

        NewUserRequest newUser = new NewUserRequest("maria", "mary@alura.com.br");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newUser)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_not_allow_duplication_of_email() throws Exception {
        userRepository.save(new User("john", "john@email.com"));

        NewUserRequest newUser = new NewUserRequest("joao", "john@email.com");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newUser)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}