package br.com.alura.school.support.validation;

import br.com.alura.school.builder.CourseBuilder;
import br.com.alura.school.builder.UserBuilder;
import br.com.alura.school.course.Course;
import br.com.alura.school.enrollment.Enrollment;
import br.com.alura.school.enrollment.EnrollmentRepository;
import br.com.alura.school.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class EnrollmentValidatorTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentValidator validator;

    @Test
    void should_return_that_user_is_already_enrolled() {
        User user = UserBuilder.oneUser().build();
        Course course = CourseBuilder.oneCourse().build();

        Mockito.when(enrollmentRepository.findEnrollmentByUserAndCourse(user, course)).thenReturn(Optional.of(new Enrollment(course, user)));

        Assertions.assertEquals(true, validator.isAlreadyEnrolled(user, course));
    }

    @Test
    void should_return_that_user_is_not_already_enrolled() {
        User user = UserBuilder.oneUser().build();
        Course course = CourseBuilder.oneCourse().build();

        Mockito.when(enrollmentRepository.findEnrollmentByUserAndCourse(user, course)).thenReturn(Optional.empty());

        Assertions.assertEquals(false, validator.isAlreadyEnrolled(user, course));
    }

}