package com.cursosp.projetosp;

import com.cursosp.projetosp.domain.Categoria;
import com.cursosp.projetosp.domain.Cidade;
import com.cursosp.projetosp.domain.Cliente;
import com.cursosp.projetosp.domain.Endereco;
import com.cursosp.projetosp.domain.Estado;
import com.cursosp.projetosp.domain.ItemPedido;
import com.cursosp.projetosp.domain.Pagamento;
import com.cursosp.projetosp.domain.PagamentoComBoleto;
import com.cursosp.projetosp.domain.PagamentoComCartao;
import com.cursosp.projetosp.domain.Pedido;
import com.cursosp.projetosp.domain.Produto;
import com.cursosp.projetosp.enums.EstadoPagamento;
import com.cursosp.projetosp.enums.TipoCliente;
import com.cursosp.projetosp.repositories.CategoriaRepository;
import com.cursosp.projetosp.repositories.CidadeRepository;
import com.cursosp.projetosp.repositories.ClienteRepository;
import com.cursosp.projetosp.repositories.EnderecoRepository;
import com.cursosp.projetosp.repositories.EstadoRepository;
import com.cursosp.projetosp.repositories.ItemPedidoRepository;
import com.cursosp.projetosp.repositories.PagamentoRepository;
import com.cursosp.projetosp.repositories.PedidoRepository;
import com.cursosp.projetosp.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ProjetospApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjetospApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Inform??tica");
		Categoria cat2 = new Categoria(null, "Escrit??rio");

		Produto p1 = new Produto(null, "Computador", 3000.0);
		Produto p2 = new Produto(null, "Impressora", 800.0);
		Produto p3 = new Produto(null, "Mouse", 80.0);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "S??o Paulo");

		Cidade c1 = new Cidade(null, "Uberl??ndia", est1);
		Cidade c2 = new Cidade(null, "S??o Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est1);

		est1.getCidades().addAll(Arrays.asList(c1, c3));
		est2.getCidades().addAll(Arrays.asList(c2));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "45412136325", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("47965231452", "7932568547"));
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "89025258", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "89014523", cli1, c2);
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf1.parse("30/09/2022 10:32"), cli1, e1);
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pedido ped2 = new Pedido(null, sdf1.parse("10/10/2022 10:32"), cli1, e2);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf1.parse("20/10/2022 00:00"), null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
	    pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.0);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.0);

		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.0);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(List.of(ip3));

		p1.getItens().addAll(List.of(ip1));
		p2.getItens().addAll(List.of(ip3));
		p3.getItens().addAll(List.of(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}