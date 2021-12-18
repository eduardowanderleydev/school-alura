package br.com.alura.school.enrollment;

import br.com.alura.school.builder.CourseBuilder;
import br.com.alura.school.builder.UserBuilder;
import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
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

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EnrollmentControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;


    @Test
    void should_save_enrollment() throws Exception {
        userRepository.save(new User("eduardo", "eduardo@email.com"));
        courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));

        NewEnrollmentRequest newEnrollmentRequest = new NewEnrollmentRequest("eduardo");

        mockMvc.perform(post("/courses/java-1/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newEnrollmentRequest)))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @CsvSource({
            "ana, java-50",
            "eduardo, java-1",
            "eduardo, java-50",
            "ana, ",
            "<silvana>, that's not even a code"
    })
    void should_validate_bad_enrollments_requests(String username, String courseCode) throws Exception {
        userRepository.save(new User("ana", "ana@email.com"));
        courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));

        NewEnrollmentRequest newEnrollmentRequest = new NewEnrollmentRequest(username);

        mockMvc.perform(post(String.format("/courses/%s/enroll", courseCode))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newEnrollmentRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_validate_already_enrolled_user_requests() throws Exception {
        User user = userRepository.save(new User("eduardo", "eduardo@email.com"));
        Course course = courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
        enrollmentRepository.save(new Enrollment(course, user));

        NewEnrollmentRequest newEnrollmentRequest = new NewEnrollmentRequest("eduardo");

        mockMvc.perform(post("/courses/java-1/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newEnrollmentRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_status_no_content() throws Exception {
        mockMvc.perform(get("/courses/enroll/report")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_retrieve_enrollments_report_ordered() throws Exception {
        insertEnrollmentsData();

        mockMvc.perform(get("/courses/enroll/report")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$[0].email", is("eduardo@email.com")))
                .andExpect(jsonPath("$[0].quantidade_matriculas", is(4)))
                .andExpect(jsonPath("$[1].email", is("giovana@email.com")))
                .andExpect(jsonPath("$[1].quantidade_matriculas", is(2)))
                .andExpect(jsonPath("$[2].email", is("arthur@email.com")))
                .andExpect(jsonPath("$[2].quantidade_matriculas", is(1)));
    }

    private void insertEnrollmentsData() {
        final User USER_1 = UserBuilder.oneUser().build();
        final User USER_2 = UserBuilder.oneUser().withUsername("giovana").withEmail("giovana@email.com").build();
        final User USER_3 = UserBuilder.oneUser().withUsername("arthur").withEmail("arthur@email.com").build();
        userRepository.saveAll(Arrays.asList(USER_1, USER_2, USER_3));

        final Course COURSE_1 = CourseBuilder.OneCourse().build();
        final Course COURSE_2 = CourseBuilder.OneCourse().withCode("maven-1").withName("maven").withDescription("Maven course").build();
        final Course COURSE_3 = CourseBuilder.OneCourse().withCode("maven-2").withName("maven 2").withDescription("Maven course 2").build();
        final Course COURSE_4 = CourseBuilder.OneCourse().withCode("maven-3").withName("maven 3").withDescription("Maven course 3").build();
        courseRepository.saveAll(Arrays.asList(COURSE_1, COURSE_2, COURSE_3, COURSE_4));

        enrollmentRepository.save(new Enrollment(COURSE_1, USER_1));
        enrollmentRepository.save(new Enrollment(COURSE_2, USER_1));
        enrollmentRepository.save(new Enrollment(COURSE_3, USER_1));
        enrollmentRepository.save(new Enrollment(COURSE_4, USER_1));

        enrollmentRepository.save(new Enrollment(COURSE_4, USER_3));

        enrollmentRepository.save(new Enrollment(COURSE_1, USER_2));
        enrollmentRepository.save(new Enrollment(COURSE_4, USER_2));
    }
}
