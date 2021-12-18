package br.com.alura.school.builder;

import br.com.alura.school.course.Course;

public class CourseBuilder {

    private final String CODE_DEFAULT = "java-1";
    private final String NAME_DEFAULT = "Java OO";
    private final String DESCRIPTION_DEFAULT = "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism.";

    private String code = CODE_DEFAULT;
    private String name = NAME_DEFAULT;
    private String description = DESCRIPTION_DEFAULT;


    public static CourseBuilder oneCourse() {
        return new CourseBuilder();
    }

    public CourseBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public CourseBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CourseBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public Course build() {
        return new Course(this.code, this.name, this.description);
    }

}
