package br.com.alura.school.course;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCode(String code);
}
