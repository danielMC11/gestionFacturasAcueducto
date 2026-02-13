package com.example.gestionAcueducto.users.repository.projections;

public interface UserProjection {
    Long getId();
    String getDocument();
    String getFirstName();
    String getLastName();
    String getEmail();

}
