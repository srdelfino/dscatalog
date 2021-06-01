package br.pro.delfino.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.pro.delfino.dscatalog.entities.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}
