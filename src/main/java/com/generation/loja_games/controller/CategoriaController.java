package com.generation.loja_games.controller;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.loja_games.model.Categorias;
import com.generation.loja_games.repository.CategoriaRepository;



@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {
	
	/*Injeção de Independencia*/ //transfere a responsabilidade de criar e instanciar postagens para o JPA, no lugar do construtor. Transfere da minha responsabilidade para o Spring.
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping  //Método que me mostre tudo que há na tabela de postagem, equivalente a SELECT * FROM tb_postagens;
	private ResponseEntity<List<Categorias>> getAll(){
		
		return ResponseEntity.ok(categoriaRepository.findAll());
	}
	
	@GetMapping("/{id}") //SELECT * FROM tb_postagens WHERE id = 1 -> FILTRA O OBJETO PELO ID
	public ResponseEntity<Categorias> getById(@PathVariable Long id){
		
		return categoriaRepository.findById(id)
			.map(resposta -> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/tipo/{tipo}")  
	public ResponseEntity<List<Categorias>> getByTipo(@PathVariable String tipo){
		
		return ResponseEntity.ok(categoriaRepository.findAllByTipoContainingIgnoreCase(tipo));	
	}
	
	@PostMapping /*Criar nova categoria @PostMapping: Anotação que indica que o método abaixo responderá todas as requisições do tipo POST que forem enviadas no endpoint /categoria*/
	public ResponseEntity<Categorias> postCategoria(@Valid @RequestBody Categorias categorias){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categorias));
	}
	
	@PutMapping  /* Edita uma categoria, @PutMapping: Anotação, que indica que o método abaixo responderá todas as requisições do tipo PUT que forem enviadas no endpoint /categoria */ 
	public ResponseEntity<Categorias> putCategoria(@Valid @RequestBody Categorias categoria) {
					
		return categoriaRepository.findById(categoria.getId())
				.map(resposta -> ResponseEntity.ok().body(categoriaRepository.save(categoria)))
				.orElse(ResponseEntity.notFound().build());

	}

	@DeleteMapping("/{id}")  /* Deleta uma categoria @DeleteMapping("/{id}"): Annotation (Anotação), que indica que o método abaixo responderá todas as requisições do tipo DELETE que forem enviadas no endpoint /produtos/id */
	public ResponseEntity<?> deleteCategoria(@PathVariable Long id) {
		
		return categoriaRepository.findById(id)
				.map(resposta -> {
					categoriaRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}


}
