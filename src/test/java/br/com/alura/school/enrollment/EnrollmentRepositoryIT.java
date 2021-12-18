package br.com.alura.school.enrollment;

import br.com.alura.school.builder.CourseBuilder;
import br.com.alura.school.builder.UserBuilder;
import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class EnrollmentRepositoryIT {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void should_retrieve_enrollment_by_user_and_course() {
        User user = UserBuilder.oneUser().build();
        Course course = CourseBuilder.oneCourse().build();
        insertEnrollmentData(user, course);

        Optional<Enrollment> enrollmentByUserAndCourse = enrollmentRepository.findEnrollmentByUserAndCourse(user, course);

        Assertions.assertTrue(enrollmentByUserAndCourse.isPresent());
    }

    @Test
    void should_retrieve_enrollment_report_ordered() {
        insertEnrollmentReportData();

        List<EnrollmentReportResponse> enrollmentReportResponses = enrollmentRepository.generateReport();

        Assertions.assertTrue(enrollmentReportResponses.size() == 3);
        Assertions.assertTrue(enrollmentReportResponses.get(0).getEmail().equals("eduardo@email.com"));
        Assertions.assertTrue(enrollmentReportResponses.get(0).getEnrollmentQuantity() == 4l);
        Assertions.assertTrue(enrollmentReportResponses.get(1).getEmail().equals("giovana@email.com"));
        Assertions.assertTrue(enrollmentReportResponses.get(1).getEnrollmentQuantity() == 2l);
        Assertions.assertTrue(enrollmentReportResponses.get(2).getEmail().equals("arthur@email.com"));
        Assertions.assertTrue(enrollmentReportResponses.get(2).getEnrollmentQuantity() == 1l);
    }

    private void insertEnrollmentData(User user, Course course) {
        userRepository.save(user);
        courseRepository.save(course);

        enrollmentRepository.save(new Enrollment(course, user));
    }

    private void insertEnrollmentReportData() {
        final User USER_1 = UserBuilder.oneUser().build();
        final User USER_2 = UserBuilder.oneUser().withUsername("giovana").withEmail("giovana@email.com").build();
        final User USER_3 = UserBuilder.oneUser().withUsername("arthur").withEmail("arthur@email.com").build();
        userRepository.saveAll(Arrays.asList(USER_1, USER_2, USER_3));

        final Course COURSE_1 = CourseBuilder.oneCourse().build();
        final Course COURSE_2 = CourseBuilder.oneCourse().withCode("maven-1").withName("maven").withDescription("Maven course").build();
        final Course COURSE_3 = CourseBuilder.oneCourse().withCode("maven-2").withName("maven 2").withDescription("Maven course 2").build();
        final Course COURSE_4 = CourseBuilder.oneCourse().withCode("maven-3").withName("maven 3").withDescription("Maven course 3").build();
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
