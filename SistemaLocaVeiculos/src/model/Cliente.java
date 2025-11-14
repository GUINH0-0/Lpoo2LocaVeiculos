package model;

import java.io.Serializable;
import java.util.Objects;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
	Long id;
	private String nome, sobrenome, RG, CPF, Endereco;
	
	public Cliente(String nome, String sobrenome, String RG, String CPF, String Endereco) {
	    this.nome = nome;
	    this.sobrenome = sobrenome;
	    this.RG = RG;
	    this.CPF = CPF;
	    this.Endereco = Endereco;
	}
	
	public Cliente() {
	    this("", "", "", "", "");
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getRG() {
		return RG;
	}

	public void setRG(String rG) {
		RG = rG;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getEndereco() {
		return Endereco;
	}

	public void setEndereco(String endereco) {
		Endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente outro)) return false;
        return CPF != null && CPF.equals(outro.CPF);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CPF);
    }
}
