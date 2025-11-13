package view;

import controller.VeiculoController;
import enums.Categoria;
import enums.Estado;
import enums.Marca;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Automovel;
import model.Motocicleta;
import model.Van;
import model.Veiculo;

@SuppressWarnings("serial")
public class TelaVenderVeiculos extends JPanel {

    private VeiculoController controller;

    private JComboBox<String> tipoCombo;
    private JComboBox<Marca> marcaCombo;
    private JComboBox<Categoria> categoriaCombo;
    private JTable tabelaVeiculos;
    private DefaultTableModel tabelaModel;
    private JButton botaoVender;

    public TelaVenderVeiculos(VeiculoController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("VENDA DE VEÍCULOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.add(criarPainelFiltros(), BorderLayout.NORTH);
        painelPrincipal.add(criarPainelTabela(), BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout(10, 10));
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        botaoVender = new JButton("Vender Veículo");
        botaoVender.setPreferredSize(new Dimension(120, 30));
        botaoVender.setEnabled(false);
        painelBotoes.add(botaoVender);

        painelInferior.add(painelBotoes, BorderLayout.SOUTH);
        painelPrincipal.add(painelInferior, BorderLayout.SOUTH);

        add(painelPrincipal, BorderLayout.CENTER);

        configurarEventos();
        atualizarTabela();
    }

    private JPanel criarPainelFiltros() {
        JPanel painel = new JPanel(new GridLayout(1, 6, 5, 5));
        tipoCombo = new JComboBox<>(new String[]{"Todos", "Automóvel", "Motocicleta", "Van"});
        marcaCombo = new JComboBox<>(Marca.values());
        marcaCombo.insertItemAt(null, 0);
        marcaCombo.setSelectedIndex(0);
        categoriaCombo = new JComboBox<>(Categoria.values());
        categoriaCombo.insertItemAt(null, 0);
        categoriaCombo.setSelectedIndex(0);

        painel.add(new JLabel("Tipo:"));
        painel.add(tipoCombo);
        painel.add(new JLabel("Marca:"));
        painel.add(marcaCombo);
        painel.add(new JLabel("Categoria:"));
        painel.add(categoriaCombo);

        return painel;
    }

    private JScrollPane criarPainelTabela() {
        String[] colunas = {"Placa", "Marca", "Modelo", "Ano", "Categoria", "Preço"};
        tabelaModel = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaVeiculos = new JTable(tabelaModel);
        tabelaVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return new JScrollPane(tabelaVeiculos);
    }

    private void configurarEventos() {
        tipoCombo.addActionListener(e -> atualizarTabela());
        marcaCombo.addActionListener(e -> atualizarTabela());
        categoriaCombo.addActionListener(e -> atualizarTabela());

        tabelaVeiculos.getSelectionModel().addListSelectionListener(e -> {
            botaoVender.setEnabled(tabelaVeiculos.getSelectedRow() != -1);
        });

        botaoVender.addActionListener(e -> realizarVenda());
    }

    private void realizarVenda() {
        int linhaSelecionada = tabelaVeiculos.getSelectedRow();
        if (linhaSelecionada == -1) return;

        String placa = (String) tabelaModel.getValueAt(linhaSelecionada, 0);

        try {
            controller.venderVeiculo(placa);
            JOptionPane.showMessageDialog(this, "Venda realizada com sucesso!");
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar venda: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTabela() {
        tabelaModel.setRowCount(0);

        String tipoSelecionado = (String) tipoCombo.getSelectedItem();
        Marca marcaSelecionada = (Marca) marcaCombo.getSelectedItem();
        Categoria categoriaSelecionada = (Categoria) categoriaCombo.getSelectedItem();

        try {
            List<Veiculo> veiculos = controller.listarTodos();

            for (Veiculo v : veiculos) {
                if (v.getEstado() != Estado.DISPONIVEL) continue;

                if (tipoSelecionado != null && !tipoSelecionado.equals("Todos")) {
                    if (tipoSelecionado.equals("Automóvel") && !(v instanceof Automovel)) continue;
                    if (tipoSelecionado.equals("Motocicleta") && !(v instanceof Motocicleta)) continue;
                    if (tipoSelecionado.equals("Van") && !(v instanceof Van)) continue;
                }

                if (marcaSelecionada != null && v.getMarca() != marcaSelecionada) continue;
                if (categoriaSelecionada != null && v.getCategoria() != categoriaSelecionada) continue;

                String modelo = "";
                if (v instanceof Automovel) modelo = ((Automovel) v).getModelo().name();
                else if (v instanceof Motocicleta) modelo = ((Motocicleta) v).getModelo().name();
                else if (v instanceof Van) modelo = ((Van) v).getModelo().name();

                tabelaModel.addRow(new Object[]{
                        v.getPlaca(), v.getMarca(), modelo, v.getAno(), v.getCategoria(),
                        String.format("R$ %.2f", v.getValorParaVenda())
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar veículos: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
