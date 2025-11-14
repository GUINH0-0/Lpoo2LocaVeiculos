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

public class LocacaoController {

    private static LocacaoDao locacaoDao;
    
    // Construtor principal
    public LocacaoController() {
        try {
            locacaoDao = DaoFactory.getDao(LocacaoDao.class, DaoType.SQL);
            carregarLocacoesDoBanco();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inicializar DAOs: " + e.getMessage());
        }
    }

    // === CRUD BÁSICO ===
    public List<Locacao> listarTodos() {
        try {
            return locacaoDao.getAll();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar locações: " + e.getMessage());
            return List.of();
        }
    }

    // === Métodos de sincronização ===
    public void carregarLocacoesDoBanco() {
        try {
            Main.locacoes = locacaoDao.getAll();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar locações do banco: " + e.getMessage());
        }
    }

    public void salvarTodos() {
        try {
            for (Locacao l : Main.locacoes) {
                locacaoDao.update(l);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao sincronizar locações: " + e.getMessage());
        }
    }
}
