package br.com.josejuniorjr.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.josejuniorjr.cursomc.domain.Categoria;
import br.com.josejuniorjr.cursomc.domain.Cidade;
import br.com.josejuniorjr.cursomc.domain.Estado;
import br.com.josejuniorjr.cursomc.domain.Produto;
import br.com.josejuniorjr.cursomc.repositories.CategoriaRepository;
import br.com.josejuniorjr.cursomc.repositories.CidadeRepository;
import br.com.josejuniorjr.cursomc.repositories.EstadoRepository;
import br.com.josejuniorjr.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000);
		Produto p2 = new Produto(null, "Impressora", 800);
		Produto p3 = new Produto(null, "Mouse", 80);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().add(p2);
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p2.getCategorias().add(cat1);
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Santa Catarina");
		Estado est2 = new Estado(null, "Rio Grande do Sul");
		
		Cidade cid1 = new Cidade(null, "Criciúma", est1);
		Cidade cid2 = new Cidade(null, "Florianópolis", est1);
		Cidade cid3 = new Cidade(null, "Torres", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1, cid2));
		est2.getCidades().add(cid3);
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
	}

}
