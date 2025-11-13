package model;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import enums.ModeloAutomovel;

@SuppressWarnings("serial")
public class Automovel extends Veiculo{
	private ModeloAutomovel modelo;
	public Automovel(Estado estado, Marca marca, Categoria categoria, ModeloAutomovel modelo, String placa, int ano, double valorDeCompra) {
		super(estado, marca, categoria, placa, ano, valorDeCompra);
		this.modelo = modelo;
	}

	@Override
	public double getValorDiariaLocacao() {
		double valor = 0;
		switch(this.getCategoria()) {
		case POPULAR:
			valor = 100;
			break;
		case INTERMEDIARIO:
			valor = 300;
			break;
		case LUXO:
			valor = 450;
			break;
		default:
			valor = 100;
			break;
		}
		return valor;
	}

	public ModeloAutomovel getModelo() {
		return modelo;
	}
}