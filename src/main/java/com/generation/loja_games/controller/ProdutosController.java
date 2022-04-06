package com.generation.loja_games.controller;

import java.math.BigDecimal;
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

import com.generation.loja_games.model.Produtos;
import com.generation.loja_games.repository.CategoriaRepository;
import com.generation.loja_games.repository.ProdutosRepository;


@RestController	
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutosController {
	
	/*Injeção de Independencia*/ //transfere a responsabilidade de criar e instanciar postagens para o JPA, no lugar do construtor. Transfere da minha responsabilidade para o Spring.
	@Autowired
	private ProdutosRepository produtoRepository;
	
	/*Injeção de Independencia*/ //transfere a responsabilidade de criar e instanciar postagens para o JPA, no lugar do construtor. Transfere da minha responsabilidade para o Spring.
	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping  //Método que me mostre tudo que há na tabela de postagem, equivalente a SELECT * FROM tb_postagens;
	public ResponseEntity<List<Produtos>> getAll(){
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	@GetMapping("/{id}")  //SELECT * FROM tb_postagens WHERE id = 1 -> FILTRA O OBJETO PELO ID
	public ResponseEntity<Produtos> getById(@PathVariable Long id){
		return produtoRepository.findById(id)
			.map(resposta -> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}") 
	public ResponseEntity<List<Produtos>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
	}	
	
	@PostMapping /*Criar novo produto @PostMapping: Anotação que indica que o método abaixo responderá todas as requisições do tipo POST que forem enviadas no endpoint /produtos*/
	public ResponseEntity<Produtos> postProduto(@Valid @RequestBody Produtos produto){
		return categoriaRepository.findById(produto.getCategorias().getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto)))
				.orElse(ResponseEntity.badRequest().build());
	}
	
	@PutMapping  /* Edita um produto, @PutMapping: Anotação, que indica que o método abaixo responderá todas as requisições do tipo PUT que forem enviadas no endpoint /produtos */ 
	public ResponseEntity<Produtos> putProduto(@Valid @RequestBody Produtos produto) {
					
		if (produtoRepository.existsById(produto.getId())){
			return categoriaRepository.findById(produto.getCategorias().getId())
					.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto)))
					.orElse(ResponseEntity.badRequest().build());
		}		
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}") /* Deletar um produto @DeleteMapping("/{id}"): Annotation (Anotação), que indica que o método abaixo responderá todas as requisições do tipo DELETE que forem enviadas no endpoint /produtos/id */
	public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
		return produtoRepository.findById(id)
				.map(resposta -> {
					produtoRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}

	
	
	@GetMapping("/preco_maior/{preco}") // Consulta pelo preço maior do que o preço digitado emm ordem crescente
	public ResponseEntity<List<Produtos>> getPrecoMaiorQue(@PathVariable BigDecimal preco){ 
		return ResponseEntity.ok(produtoRepository.findByPrecoGreaterThanOrderByPreco(preco));
	}
	

	
	@GetMapping("/preco_menor/{preco}") // Consulta pelo preço menor do que o preço digitado em ordem decrescente
	public ResponseEntity<List<Produtos>> getPrecoMenorQue(@PathVariable BigDecimal preco){ 
		return ResponseEntity.ok(produtoRepository.findByPrecoLessThanOrderByPrecoDesc(preco));
	}
	
}
