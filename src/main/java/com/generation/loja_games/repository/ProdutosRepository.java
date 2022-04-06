package com.generation.loja_games.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.loja_games.model.Produtos;


public interface ProdutosRepository extends JpaRepository <Produtos, Long>{
	
	public List<Produtos> findAllByNomeContainingIgnoreCase(String nome); 

	public List <Produtos> findByPrecoGreaterThanOrderByPreco(BigDecimal preco); // Buscar todos os Produtos cujo o preço seja maior do que um valor digitado ordenado pelo preço em ordem crescente. Corresponde MySQL: select * from tb_produtos where preco > preco order by preco;
	
	public List <Produtos> findByPrecoLessThanOrderByPrecoDesc(BigDecimal preco);  // Buscar todos os Produtos cujo o preço seja menor do que um valor digitado ordenado pelo preço em ordem decrescente. Corresponde MySQL: select * from tb_produtos where preco < preco order by preco desc;
	
}
