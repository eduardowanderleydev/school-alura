package br.com.alura.school.course;

import br.com.alura.school.support.validation.Unique;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

class NewCourseRequest {

    @Unique(entity = Course.class, field = "code")
    @Size(max=10)
    @NotBlank
    @JsonProperty
    private final String code;

    @Unique(entity = Course.class, field = "name")
    @Size(max=20)
    @NotBlank
    @JsonProperty
    private final String name;

    @JsonProperty
    private final String description;

    public NewCourseRequest(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    Course toEntity() {
        return new Course(code, name, description);
    }
}
