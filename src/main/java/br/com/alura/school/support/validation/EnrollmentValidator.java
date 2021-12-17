package br.com.alura.school.support.validation;

import br.com.alura.school.course.Course;
import br.com.alura.school.enrollment.Enrollment;
import br.com.alura.school.enrollment.EnrollmentRepository;
import br.com.alura.school.user.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EnrollmentValidator {

    private static EnrollmentRepository enrollmentRepository;

    public EnrollmentValidator(EnrollmentRepository enrollmentRepository){
        this.enrollmentRepository = enrollmentRepository;
    }

    public static boolean isAlreadyEnrolled(User user, Course course){
        Optional<Enrollment> enrollment = enrollmentRepository.findEnrollmentByUserAndCourse(user, course);

        if (enrollment.isPresent()){
            return true;
        }

        return false;
    }



}
