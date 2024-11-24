package com.wesp.infra.anotaçãoDeValidação;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final EntityManager entityManager;

    public UniqueEmailValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true; // Não valida se for null, pois a validação @NotNull é quem vai cuidar disso
        }

        // Consulta para verificar se o email já está no banco de dados
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(s) FROM Seller s WHERE s.email = :email", Long.class);
        query.setParameter("email", email);

        // Se o resultado for maior que 0, o email já existe
        return query.getSingleResult() == 0;
    }
}
