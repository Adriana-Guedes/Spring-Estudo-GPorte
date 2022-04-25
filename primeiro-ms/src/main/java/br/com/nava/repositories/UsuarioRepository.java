package br.com.nava.repositories;
import org.springframework.data.jpa.repository.JpaRepository;// HERANÇA TRAZENDO TODOS OS METODOS JÁ EXISTENTES NO JPA (CRUD)
import org.springframework.stereotype.Repository;

import br.com.nava.entities.UsuarioEntity; // IMPORTAR A ENTIDADE 




//NECESSÁRIO NOTAÇÃO
@Repository
public interface UsuarioRepository extends  JpaRepository <UsuarioEntity,Integer> {

	//ORM - OBJECT RELATIONAL MAPPING - MAPEAMENTO RELACIONAL DE OBJETOS

}
