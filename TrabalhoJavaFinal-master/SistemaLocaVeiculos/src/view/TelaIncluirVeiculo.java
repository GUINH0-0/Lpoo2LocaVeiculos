package view;

import controller.VeiculoController;
import enums.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import model.*;

@SuppressWarnings("serial")
public class TelaIncluirVeiculo extends JPanel {

    private final VeiculoController veiculoController;
    private static AbstractTableModel modeloTabela;

    private JComboBox<Marca> comboMarca;
    private JComboBox<Estado> comboEstado;
    private JComboBox<Categoria> comboCategoria;
    private JComboBox<String> comboTipo;
    private JComboBox<Enum<?>> comboModelo;
    private JFormattedTextField tAno;
    private JFormattedTextField tPlaca;
    private JFormattedTextField tValor;

    public TelaIncluirVeiculo(VeiculoController veiculoController) throws ParseException {
        this.veiculoController = veiculoController;
        inicializarComponentes();
        configurarEventos();
        atualizarTabela();
    }

    private void inicializarComponentes() throws ParseException {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("CADASTRO DE VEÍCULOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // ==== CAMPOS DO FORMULÁRIO ====
        comboMarca = new JComboBox<>(Marca.values());
        comboEstado = new JComboBox<>(Estado.values());
        comboCategoria = new JComboBox<>(Categoria.values());

        String[] tipos = {"AUTOMÓVEL", "MOTOCICLETA", "VAN"};
        comboTipo = new JComboBox<>(tipos);

        Map<String, Enum<?>[]> modelosPorTipo = new HashMap<>();
        modelosPorTipo.put("AUTOMÓVEL", ModeloAutomovel.values());
        modelosPorTipo.put("MOTOCICLETA", ModeloMotocicleta.values());
        modelosPorTipo.put("VAN", ModeloVan.values());

        comboModelo = new JComboBox<>(modelosPorTipo.get("AUTOMÓVEL"));
        comboTipo.addActionListener(e -> {
            String tipoSel = (String) comboTipo.getSelectedItem();
            comboModelo.setModel(new DefaultComboBoxModel<>(modelosPorTipo.get(tipoSel)));
        });

        MaskFormatter mascaraAno = new MaskFormatter("####");
        mascaraAno.setPlaceholderCharacter('_');
        tAno = new JFormattedTextField(mascaraAno);

        MaskFormatter mascaraPlaca = new MaskFormatter("LLL-#A##");
        mascaraPlaca.setPlaceholderCharacter('_');
        tPlaca = new JFormattedTextField(mascaraPlaca);

        DecimalFormat df = new DecimalFormat("#,##0.00");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.forLanguageTag("pt-BR")));
        NumberFormatter formatter = new NumberFormatter(df);
        formatter.setValueClass(Double.class);
        formatter.setMinimum(0.0);
        formatter.setMaximum(Double.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        tValor = new JFormattedTextField(formatter);
        tValor.setColumns(8);

        JButton botaoAdicionar = new JButton("Adicionar Veículo");

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        form.add(new JLabel("Marca:")); form.add(comboMarca);
        form.add(new JLabel("Estado:")); form.add(comboEstado);
        form.add(new JLabel("Categoria:")); form.add(comboCategoria);
        form.add(new JLabel("Tipo:")); form.add(comboTipo);
        form.add(new JLabel("Modelo:")); form.add(comboModelo);
        form.add(new JLabel("Ano:")); form.add(tAno);
        form.add(new JLabel("Valor:")); form.add(tValor);
        form.add(new JLabel("Placa:")); form.add(tPlaca);
        form.add(botaoAdicionar);

        add(form, BorderLayout.NORTH);

        JTable tabela = criarTabelaVeiculos();
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        botaoAdicionar.addActionListener(this::adicionarVeiculo);
    }

    private JTable criarTabelaVeiculos() {
        modeloTabela = new AbstractTableModel() {
            private final String[] colunas = {"Placa", "Ano", "Marca", "Modelo", "Estado", "Categoria", "Valor"};

            @Override
            public int getRowCount() {
                return veiculoController.listarTodos().size();
            }

            @Override
            public int getColumnCount() {
                return colunas.length;
            }

            @Override
            public String getColumnName(int column) {
                return colunas[column];
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Veiculo v = veiculoController.listarTodos().get(rowIndex);
                return switch (columnIndex) {
                    case 0 -> v.getPlaca();
                    case 1 -> v.getAno();
                    case 2 -> v.getMarca();
                    case 3 -> (v instanceof Automovel a) ? a.getModelo()
                            : (v instanceof Motocicleta m) ? m.getModelo()
                            : (v instanceof Van van) ? van.getModelo() : "";
                    case 4 -> v.getEstado();
                    case 5 -> v.getCategoria();
                    case 6 -> String.format("R$ %.2f", v.getValorDeCompra());
                    default -> null;
                };
            }
        };
        return new JTable(modeloTabela);
    }

    private void configurarEventos() {
        // já tratado no construtor
    }

    private void adicionarVeiculo(ActionEvent e) {
        try {
        	Long id = (long) 1;
            String tipo = (String) comboTipo.getSelectedItem();
            Estado estado = (Estado) comboEstado.getSelectedItem();
            Marca marca = (Marca) comboMarca.getSelectedItem();
            Categoria categoria = (Categoria) comboCategoria.getSelectedItem();
            Enum<?> modelo = (Enum<?>) comboModelo.getSelectedItem();

            int ano = Integer.parseInt(tAno.getText().replaceAll("[^\\d]", ""));
            double valor = Double.parseDouble(tValor.getText().replace(".", "").replace(",", "."));
            String placa = tPlaca.getText().replaceAll("[ _]", "");

            if (veiculoController.placaExiste(placa)) {
                JOptionPane.showMessageDialog(this, "Placa já cadastrada!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            switch (tipo) {
                case "AUTOMÓVEL" -> veiculoController.adicionarVeiculo(
                        new Automovel(id, estado, marca, categoria, (ModeloAutomovel) modelo, placa, ano, valor)
                );
                case "MOTOCICLETA" -> veiculoController.adicionarVeiculo(
                        new Motocicleta(id, estado, marca, categoria, (ModeloMotocicleta) modelo, placa, ano, valor)
                );
                case "VAN" -> veiculoController.adicionarVeiculo(
                        new Van(id, estado, marca, categoria, (ModeloVan) modelo, placa, ano, valor)
                );
            }

            JOptionPane.showMessageDialog(this, "Veículo adicionado com sucesso!");
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar veículo: " + ex.getMessage());
        }
    }

    public static void atualizarTabela() {
        modeloTabela.fireTableDataChanged();
    }
}
