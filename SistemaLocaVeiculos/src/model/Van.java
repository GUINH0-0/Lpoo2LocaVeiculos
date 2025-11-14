package model;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import enums.ModeloVan;

@SuppressWarnings("serial")
public class Van extends Veiculo {
	private ModeloVan modelo;
	public Van(Long id, Estado estado, Marca marca, Categoria categoria, ModeloVan modelo , String placa, int ano, double valorDeCompra) {
		super(id, estado, marca, categoria, placa, ano, valorDeCompra);
		this.modelo = modelo;
	}

	@Override
	public double getValorDiariaLocacao() {
		double valor = 0;
		switch(this.getCategoria()) {
		case POPULAR:
			valor = 200;
			break;
		case INTERMEDIARIO:
			valor = 400;
			break;
		case LUXO:
			valor = 600;
			break;
		default:
			valor = 200;
			break;
		}
		return valor;
	}

	public ModeloVan getModelo() {
		return modelo;
	}
}
