package br.com.alura.school.course;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

class CourseResponse {

    @JsonProperty
    private final String code;

    @JsonProperty
    private final String name;

    @JsonProperty
    private final String shortDescription;

    CourseResponse(Course course) {
        this.code = course.getCode();
        this.name = course.getName();
        this.shortDescription = Optional.of(course.getDescription()).map(this::abbreviateDescription).orElse("");
    }

    private String abbreviateDescription(String description) {
        if (description.length() <= 13) return description;
        return description.substring(0, 10) + "...";
    }

}
