package br.com.josejuniorjr.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.josejuniorjr.cursomc.domain.Categoria;
import br.com.josejuniorjr.cursomc.domain.Cidade;
import br.com.josejuniorjr.cursomc.domain.Cliente;
import br.com.josejuniorjr.cursomc.domain.Endereco;
import br.com.josejuniorjr.cursomc.domain.Estado;
import br.com.josejuniorjr.cursomc.domain.ItemPedido;
import br.com.josejuniorjr.cursomc.domain.Pagamento;
import br.com.josejuniorjr.cursomc.domain.PagamentoComBoleto;
import br.com.josejuniorjr.cursomc.domain.PagamentoComCartao;
import br.com.josejuniorjr.cursomc.domain.Pedido;
import br.com.josejuniorjr.cursomc.domain.Produto;
import br.com.josejuniorjr.cursomc.domain.enums.EstadoPagamento;
import br.com.josejuniorjr.cursomc.domain.enums.TipoCliente;
import br.com.josejuniorjr.cursomc.repositories.CategoriaRepository;
import br.com.josejuniorjr.cursomc.repositories.CidadeRepository;
import br.com.josejuniorjr.cursomc.repositories.ClienteRepository;
import br.com.josejuniorjr.cursomc.repositories.EnderecoRepository;
import br.com.josejuniorjr.cursomc.repositories.EstadoRepository;
import br.com.josejuniorjr.cursomc.repositories.ItemPedidoRepository;
import br.com.josejuniorjr.cursomc.repositories.PagamentoRepository;
import br.com.josejuniorjr.cursomc.repositories.PedidoRepository;
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
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

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
		
		Cliente cli1 = new Cliente(null, "José Luiz", "joseluiz@gmail.com", "09899898990", TipoCliente.PESSOA_FISICA);
		Cliente cli2 = new Cliente(null, "Betha", "betha@betha.com.br", "08909898000141", TipoCliente.PESSOA_JURIDICA);

		cli1.getTelefones().addAll(Arrays.asList("999794989", "34784017"));
		cli2.getTelefones().addAll(Arrays.asList("99889900", "34335566"));
		
		Endereco end1 = new Endereco(null, "Rua 550", "147", "Mercado", "Ana Maria", "88815345", cli1, cid1);
		Endereco end2 = new Endereco(null, "12 de Agosto", "973", "Loteamento Casa Grande", "Liri", "88820000", cli1, cid1);
		Endereco end3 = new Endereco(null, "6 de Janeiro", "s/n", null, "Centro", "88804000", cli2, cid1);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		cli2.getEnderecos().add(end3);

		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(end1, end2, end3));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("03/06/1993 09:00"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("05/06/1994 09:00"), cli1, end2);

		Pagamento pag1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pag1);
		
		Pagamento pag2 = new PagamentoComBoleto(null, EstadoPagamento.PEDENTE, ped2, sdf.parse("10/12/2019 00:00"), null);
		ped2.setPagamento(pag2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pag1, pag2));
		
		ItemPedido itp1 = new ItemPedido(ped1, p1, 10.1, 5, 70.1);
		ItemPedido itp2 = new ItemPedido(ped1, p2, 0.0, 12, 12.99);
		ItemPedido itp3 = new ItemPedido(ped2, p3, 5.99, 500, 10.99);
		
		ped1.getItens().addAll(Arrays.asList(itp1, itp2));
		ped2.getItens().addAll(Arrays.asList(itp3));
		
		p1.getItens().add(itp1);
		p2.getItens().add(itp2);
		p3.getItens().add(itp3);
		
		itemPedidoRepository.saveAll(Arrays.asList(itp1, itp2, itp3));
	}

}
