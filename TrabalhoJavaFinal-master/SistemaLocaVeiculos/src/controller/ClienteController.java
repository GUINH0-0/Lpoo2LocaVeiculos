package controller;

import java.util.List;
import javax.swing.JOptionPane;
import model.Cliente;
import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.dao.DaoType;
import view.ClienteTableModel;
import view.TelaManterClientes;

public class ClienteController {

    private static ClienteDao clienteDao;
    private TelaManterClientes telaManterClientes;

    public ClienteController() {
        carregarClientesDoBanco();
        try {
            clienteDao = DaoFactory.getDao(ClienteDao.class, DaoType.SQL);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inicializar ClienteDao: " + e.getMessage());
        }
    }

    // === Inicialização da Tela ===
    public TelaManterClientes getTelaManterClientes() {
        if (telaManterClientes == null)
            telaManterClientes = new TelaManterClientes();
        return telaManterClientes;
    }

    // === CRUD BÁSICO ===

    public void adicionarCliente(Cliente cliente, ClienteTableModel modelo) {
        try {
            clienteDao.add(cliente);
            Main.clientes.add(cliente);
            modelo.atualizarTabela();
            JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar cliente: " + e.getMessage());
        }
    }

    public void atualizarCliente(Cliente cliente, ClienteTableModel modelo) {
        try {
            clienteDao.update(cliente);
            modelo.atualizarTabela();
            JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public void removerCliente(Cliente cliente, ClienteTableModel modelo) {
        try {
            clienteDao.removerPorCpf(cliente.getCPF());
            Main.clientes.remove(cliente);
            modelo.atualizarTabela();
            JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover cliente: " + e.getMessage());
        }
    }

    public List<Cliente> listarTodos() {
        try {
            return clienteDao.getAll();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar clientes: " + e.getMessage());
            return List.of();
        }
    }

    public boolean cpfExiste(String cpf) {
        try {
            return clienteDao.buscarPorCpf(cpf) != null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar CPF: " + e.getMessage());
            return false;
        }
    }

    // === Sincronização com o banco ===
    public void carregarClientesDoBanco() {
        try {
            Main.clientes = clienteDao.getAll();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar clientes do banco: " + e.getMessage());
        }
    }

    public void salvarTodos() {
        try {
            for (Cliente c : Main.clientes) {
                clienteDao.update(c);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao sincronizar clientes: " + e.getMessage());
        }
    }

    public Cliente buscarPorCpf(String cpf) throws Exception {
    return clienteDao.buscarPorCpf(cpf);


}

}