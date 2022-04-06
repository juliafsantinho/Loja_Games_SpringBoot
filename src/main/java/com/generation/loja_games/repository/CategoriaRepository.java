package com.generation.loja_games.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.loja_games.model.Categorias;



public interface CategoriaRepository extends JpaRepository<Categorias, Long> {
	
	public List<Categorias> findAllByTipoContainingIgnoreCase(String tipo);

}
