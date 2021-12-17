package br.com.alura.school.enrollment;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.support.validation.EnrollmentValidator;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EnrollmentController {

    private final CourseRepository courseRepository;

    private final EnrollmentRepository enrollmentRepository;

    private final UserRepository userRepository;

    EnrollmentController(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/courses/{courseCode}/enroll")
    ResponseEntity newEnrollment(@RequestBody @Valid NewEnrollmentRequest newEnrollmentRequest, @PathVariable("courseCode") String courseCode) {
        User user = userRepository.findByUsername(newEnrollmentRequest.getUsername()).orElseThrow();
        Course course = courseRepository.findByCode(courseCode).orElseThrow();
        Enrollment enrollment = new Enrollment(course, user);

        if (EnrollmentValidator.isAlreadyEnrolled(user, course)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        enrollmentRepository.save(enrollment);

        return ResponseEntity.created(null).build();
    }

    @GetMapping("/courses/enroll/report")
    ResponseEntity enrollmentReport() {
        List<EnrollmentReportResponse> enrollmentReportResponseList = enrollmentRepository.generateReport();

        if (enrollmentReportResponseList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok().body(enrollmentReportResponseList);
    }

}
