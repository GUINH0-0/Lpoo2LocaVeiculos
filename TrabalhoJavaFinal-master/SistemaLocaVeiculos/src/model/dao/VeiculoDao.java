package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import model.Veiculo;


    public interface VeiculoDao extends Dao<Veiculo>{
   
        public Veiculo buscarPorPlaca(String placa) throws SQLException;
        public Veiculo criarVeiculo(ResultSet rs) throws SQLException;
        public void deleteByPLaca(String placa) throws SQLException;
    }
