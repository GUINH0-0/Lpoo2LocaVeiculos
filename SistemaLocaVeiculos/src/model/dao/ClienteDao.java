package model.dao;

import java.util.List;
import model.Cliente;

    public interface ClienteDao extends Dao<Cliente>{
        public void removerPorCpf(String cpf) throws Exception;
        public Cliente buscarPorCpf(String cpf) throws Exception;
        public void update(Cliente cliente) throws Exception;
        public void add(Cliente cliente) throws Exception;
        public List<Cliente> getAll() throws Exception;
    }
