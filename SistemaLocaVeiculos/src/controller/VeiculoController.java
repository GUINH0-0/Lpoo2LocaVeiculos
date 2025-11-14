package controller;

import java.text.ParseException;
import java.util.List;
import javax.swing.JOptionPane;
import model.*;
import model.dao.DaoFactory;
import model.dao.DaoType;
import model.dao.LocacaoDao;
import model.dao.VeiculoDao;
import view.*;

public class VeiculoController {

    private static VeiculoDao veiculoDao;
    private static LocacaoDao locacaoDao;

    private TelaIncluirVeiculo telaIncluir;
    private TelaLocarVeiculos telaLocar;
    private TelaVenderVeiculos telaVender;
    private TelaDevolverVeiculos telaDevolver;

    // Construtor principal
    public VeiculoController() {
        try {
            veiculoDao = DaoFactory.getDao(VeiculoDao.class, DaoType.SQL);
            locacaoDao = DaoFactory.getDao(LocacaoDao.class, DaoType.SQL);
            carregarVeiculosDoBanco();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inicializar DAOs: " + e.getMessage());
        }
    }

    // === Inicialização das Telas ===
    public TelaIncluirVeiculo getTelaIncluir() throws ParseException {
        if (telaIncluir == null)
            telaIncluir = new TelaIncluirVeiculo(this);
        return telaIncluir;
    }

    public TelaLocarVeiculos getTelaLocar() {
        if (telaLocar == null)
            telaLocar = new TelaLocarVeiculos(this);
        return telaLocar;
    }

    public TelaVenderVeiculos getTelaVender() {
        if (telaVender == null)
            telaVender = new TelaVenderVeiculos(this);
        return telaVender;
    }

    public TelaDevolverVeiculos getTelaDevolver() {
        if (telaDevolver == null)
            telaDevolver = new TelaDevolverVeiculos(this);
        return telaDevolver;
    }

    // === CRUD BÁSICO ===
    public void adicionarVeiculo(Veiculo veiculo) {
        try {
            veiculoDao.add(veiculo);
            Main.veiculos.add(veiculo);
            TelaIncluirVeiculo.atualizarTabela();
            JOptionPane.showMessageDialog(null, "Veículo adicionado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar veículo: " + e.getMessage());
        }
    }

    public void atualizarVeiculo(Veiculo veiculo) {
        try {
            veiculoDao.update(veiculo);
            TelaIncluirVeiculo.atualizarTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar veículo: " + e.getMessage());
        }
    }

    public void removerVeiculo(String placa) {
        try {
            veiculoDao.deleteByPLaca(placa);
            Main.veiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(placa));
            TelaIncluirVeiculo.atualizarTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover veículo: " + e.getMessage());
        }
    }

    public List<Veiculo> listarTodos() {
        try {
            return veiculoDao.getAll();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar veículos: " + e.getMessage());
            return List.of();
        }
    }

    public boolean placaExiste(String placa) {
        try {
            return veiculoDao.buscarPorPlaca(placa) != null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar placa: " + e.getMessage());
            return false;
        }
    }

    // === OPERAÇÕES DE NEGÓCIO ===
    public boolean locarVeiculo(String placa, Cliente cliente, int dias, java.util.Calendar dataInicio) {
        try {
            Veiculo veiculo = veiculoDao.buscarPorPlaca(placa);
            if (veiculo == null) {
                JOptionPane.showMessageDialog(null, "Veículo com placa " + placa + " não encontrado!");
                return false;
            }

            veiculo.locar(dias, dataInicio, cliente);
            veiculoDao.update(veiculo);

            // Salvar locação no banco
            locacaoDao.add(veiculo.getLocacao());

            JOptionPane.showMessageDialog(null, "Veículo locado com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao locar veículo: " + e.getMessage());
            return false;
        }
    }

    public boolean devolverVeiculo(String placa) {
        try {
            Veiculo veiculo = veiculoDao.buscarPorPlaca(placa);
            if (veiculo == null) {
                JOptionPane.showMessageDialog(null, "Veículo com placa " + placa + " não encontrado!");
                return false;
            }

            veiculo.devolver();
            veiculoDao.update(veiculo);

            // Atualizar locação no banco
            locacaoDao.update(veiculo.getLocacao());

            JOptionPane.showMessageDialog(null, "Veículo devolvido com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao devolver veículo: " + e.getMessage());
            return false;
        }
    }

    public void venderVeiculo(String placa) {
        try {
            Veiculo veiculo = veiculoDao.buscarPorPlaca(placa);
            if (veiculo == null) {
                JOptionPane.showMessageDialog(null, "Veículo não encontrado com a placa informada!");
                return;
            }

            veiculo.vender();
            veiculoDao.update(veiculo);
            JOptionPane.showMessageDialog(null, "Veículo vendido com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender veículo: " + e.getMessage());
        }
    }

    // === Métodos de sincronização ===
    public void carregarVeiculosDoBanco() {
        try {
            Main.veiculos = veiculoDao.getAll();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar veículos do banco: " + e.getMessage());
        }
    }

    public void salvarTodos() {
        try {
            for (Veiculo v : Main.veiculos) {
                veiculoDao.update(v);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao sincronizar veículos: " + e.getMessage());
        }
    }
}
