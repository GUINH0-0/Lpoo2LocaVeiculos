package model;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import java.io.Serializable;
import java.time.Year;
import java.util.Calendar;

public abstract class Veiculo implements VeiculoI, Serializable {
    private static final long serialVersionUID = 1L;
	private Long id;
	private Estado estado;
	private Marca marca;
	private Categoria categoria;
	private Locacao locacao;
	private String placa;
	private int ano;
	private double valorDeCompra;
	
	public Veiculo(Estado estado, Marca marca, Categoria categoria, String placa, int ano, double valorDeCompra) {
		this.estado = estado; this.marca = marca; this.categoria = categoria;
		this.placa = placa; this.ano = ano; this.valorDeCompra = valorDeCompra;
	}
	
	public void locar(int dias, Calendar data, Cliente cliente) {
		this.estado = Estado.LOCADO;
		Locacao locacao = new Locacao();
		locacao.setDias(dias);
		locacao.setData(data);
		locacao.setCliente(cliente);
		locacao.setValor(this.getValorDiariaLocacao() * dias);
	}
	public void vender() {
		this.estado = Estado.VENDIDO;
	}
	public void devolver() {
		this.estado = Estado.DISPONIVEL;
		this.locacao = null;
	}
	public Estado getEstado() {
		return this.estado;
	}
	public Marca getMarca() {
		return this.marca;
	}
	public Categoria getCategoria() {
		return this.categoria;
	}
	public Locacao getLocacao() {
		return this.locacao;
	}
	public String getPlaca() {
		return this.placa;
	}
	public int getAno() {
		return this.ano;
	}
	public double getValorDeCompra() {
		return this.valorDeCompra;
	}
	
	public double getValorParaVenda() {
		double valor = this.valorDeCompra - (Year.now().getValue() - this.ano) * 0.15 * this.valorDeCompra;
		if(valor < this.valorDeCompra * 0.1) valor = this.valorDeCompra*0.1;
		return valor;
	}
	public abstract double getValorDiariaLocacao();

    public void setAno(int ano) {
        this.ano = ano;
    }

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public void setLocacao(Locacao locacao) {
		this.locacao = locacao;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public void setValorDeCompra(double valorDeCompra) {
		this.valorDeCompra = valorDeCompra;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}