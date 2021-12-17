package br.com.alura.school.enrollment;

import br.com.alura.school.course.Course;
import br.com.alura.school.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query("SELECT e FROM Enrollment e where e.user = ?1 AND e.course = ?2")
    Optional<Enrollment> findEnrollmentByUserAndCourse(User user, Course course);

    @Query("SELECT new br.com.alura.school.enrollment.EnrollmentReportResponse(u.email, COUNT(e.course)) FROM Enrollment e INNER JOIN e.user u GROUP BY u.email ORDER BY COUNT(e.course) DESC")
    List<EnrollmentReportResponse> generateReport();

}
