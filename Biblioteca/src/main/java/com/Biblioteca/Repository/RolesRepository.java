package com.Biblioteca.Repository;

import com.Biblioteca.Models.Roles.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {
}
