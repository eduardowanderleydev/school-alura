package br.com.alura.school.support.validation;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class UniquenessValidator implements ConstraintValidator<Unique, Object> {

    private Class<?> entity;
    private String field;

    private final EntityManager entityManager;

    UniquenessValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(Unique unique) {
        entity = unique.entity();
        field = unique.field();
    }

    @Override
    public boolean isValid(Object valueToBeValidated, ConstraintValidatorContext context) {
        Assert.notNull(entityManager, "The entity manager should not be null");
        String jpql = String.format("select count(1) > 0 from %s where %s = :value", entity.getSimpleName(), field);

        boolean existsInDB = entityManager
                .createQuery(jpql, Boolean.class)
                .setParameter("value", valueToBeValidated)
                .getSingleResult();

        return !existsInDB;
    }

}
