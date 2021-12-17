package br.com.alura.school.enrollment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnrollmentReportResponse {

    private final String email;

    @JsonProperty("quantidade_matriculas")
    private final Long enrollmentQuantity;

    public EnrollmentReportResponse(String email, Long enrollmentQuantity){
        this.email = email;
        this.enrollmentQuantity = enrollmentQuantity;
    }

    public Long getEnrollmentQuantity() {
        return enrollmentQuantity;
    }

    public String getEmail() {
        return email;
    }
}
