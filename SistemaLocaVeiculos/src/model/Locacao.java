package model;

import java.io.Serializable;
import java.util.Calendar;

public class Locacao implements Serializable {
    private static final long serialVersionUID = 1L;
	private Long id;
	private int dias;
	private double valor;
	private Calendar data;
	private Cliente cliente;
	private Veiculo veiculo;
	
	public Locacao(){}
	
	
	public int getDias() {
		return dias;
	}
	public double getValor() {
		return valor;
	}
	public Calendar getData() {
		return data;
	}
	public Cliente getCliente() {
		return cliente;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public Long getId() {
		return id;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void setCliente(Long clienteid) {
		for(Cliente c : Main.clientes) {
			if(c.getId() == clienteid)
				this.cliente = c;
		}
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	
	public void setVeiculo(Long veiculoid) {
		for(Veiculo v : Main.veiculos) {
			if(v.getId() == veiculoid) {
			this.veiculo = v;
			v.setLocacao(this);
			}
		}
	}
}
