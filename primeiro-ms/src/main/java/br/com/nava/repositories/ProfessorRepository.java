package br.com.nava.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;// HERANÇA TRAZENDO TODOS OS METODOS JÁ EXISTENTES NO JPA (CRUD)
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.nava.entities.ProfessorEntity; // IMPORTAR A ENTIDADE 



@Repository // CAMADA DE ACESSO AO BANCO    //  (ENTIDADE DE CLASSE, e o TIPO DA CHAVE PRIMARIAa)					
public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Integer> {
	
	//ORM - OBJECT RELATIONAL MAPPING - MAPEAMENTO RELACIONAL DE OBJETOS
	
	
	
	
	
	//METODOs DE BUSCAR POR NOME like SQL
	
	
	// findBy INDICA QUE O Spring IRÁ MONTAR O SQL 
		// SELECT * FROM PROFESSORES WHERE NOME LIKE '%NOMES%'
		public List<ProfessorEntity> findByNomeContains(String nome);//JPA
		
		// JPQL -> Java Persist Query Language
		// CONTAINS
		@Query(value = "SELECT p FROM ProfessorEntity p WHERE p.nome like %:nome%")
		// STARTS WITH
		//@Query(value = "SELECT p FROM ProfessorEntity p WHERE p.nome like :nome%")
		// ENDS WITH
		//@Query(value = "SELECT p FROM ProfessorEntity p WHERE p.nome like %:nome")
		public List<ProfessorEntity> searchByNome(@Param("nome") String nome);
		
		
		
		//SQL NATIVO, PURO  / ROW QUERY
		@Query(value = "SELECT * FROM PROFESSORES p WHERE p.nome like %?1%", nativeQuery = true)
		public List<ProfessorEntity> searchByNomeNativeSQL(String nome);
				
		
		
		
	}

