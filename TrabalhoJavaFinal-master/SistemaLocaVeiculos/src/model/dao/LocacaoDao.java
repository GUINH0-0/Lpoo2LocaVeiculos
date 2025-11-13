package model.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.Cliente;
import model.Locacao;
import model.Veiculo;

public interface LocacaoDao extends Dao<Locacao>{

    public List<Locacao> listarPorVeiculo(Veiculo veiculo) throws SQLException, IOException;
    public List<Locacao> listarPorCliente(Cliente cliente) throws SQLException, IOException;
    public Locacao montarLocacao(ResultSet rs) throws SQLException;

}

/*getById(long)
delete(Locacao)
getAll()
deleteAll()
update(Locacao)
add(Locacao)*/