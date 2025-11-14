package model;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import enums.ModeloMotocicleta;

@SuppressWarnings("serial")
public class Motocicleta extends Veiculo {
	private ModeloMotocicleta modelo;

	public Motocicleta(Long id, Estado estado, Marca marca, Categoria categoria, ModeloMotocicleta modelo, String placa, int ano, double valorDeCompra) {
		super(id, estado, marca, categoria, placa, ano, valorDeCompra);
		this.modelo = modelo;
	}

	@Override
	public double getValorDiariaLocacao() {
		double valor = 0;
		switch(this.getCategoria()) {
		case POPULAR:
			valor = 70;
			break;
		case INTERMEDIARIO:
			valor = 200;
			break;
		case LUXO:
			valor = 350;
			break;
		default:
			valor = 70;
			break;
		}
		return valor;
	}

	public ModeloMotocicleta getModelo() {
		return modelo;
	}
}
