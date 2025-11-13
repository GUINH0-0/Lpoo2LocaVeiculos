package view;

import controller.VeiculoController;
import enums.Estado;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Automovel;
import model.Motocicleta;
import model.Van;
import model.Veiculo;

@SuppressWarnings("serial")
public class TelaDevolverVeiculos extends JPanel {

    private JTable tabelaVeiculos;
    private DefaultTableModel tabelaModel;
    private JButton botaoDevolver;

    private VeiculoController veiculoController;

    public TelaDevolverVeiculos(VeiculoController veiculoController) {
        this.veiculoController = veiculoController;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("DEVOLVER VEÍCULOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.add(criarPainelTabela(), BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout(10, 10));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botaoDevolver = new JButton("Devolver Veículo");
        botaoDevolver.setPreferredSize(new Dimension(150, 30));
        botaoDevolver.setEnabled(false);
        painelBotoes.add(botaoDevolver);
        painelInferior.add(painelBotoes, BorderLayout.SOUTH);

        painelPrincipal.add(painelInferior, BorderLayout.SOUTH);
        add(painelPrincipal, BorderLayout.CENTER);

        configurarEventos();
        atualizarTabela();
    }

    private JScrollPane criarPainelTabela() {
        String[] colunas = {
            "Cliente", "Placa", "Marca", "Modelo", "Ano",
            "Data Locação", "Preço Diária", "Dias Locado", "Valor Total"
        };

        tabelaModel = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaVeiculos = new JTable(tabelaModel);
        tabelaVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return new JScrollPane(tabelaVeiculos);
    }

    private void configurarEventos() {
        tabelaVeiculos.getSelectionModel().addListSelectionListener(e -> {
            botaoDevolver.setEnabled(tabelaVeiculos.getSelectedRow() != -1);
        });

        botaoDevolver.addActionListener(e -> realizarDevolucao());
    }

    private void realizarDevolucao() {
        int linhaSelecionada = tabelaVeiculos.getSelectedRow();
        if (linhaSelecionada == -1) return;

        String placa = (String) tabelaModel.getValueAt(linhaSelecionada, 1);

        try {
            veiculoController.devolverVeiculo(placa);
            JOptionPane.showMessageDialog(this, "Devolução realizada com sucesso!");
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao devolver veículo: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTabela() {
        tabelaModel.setRowCount(0);

        var veiculos = veiculoController.listarTodos();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Veiculo v : veiculos) {
            if (v.getEstado() != Estado.LOCADO) continue;

            String modelo = "";
            if (v instanceof Automovel) modelo = ((Automovel) v).getModelo().name();
            else if (v instanceof Motocicleta) modelo = ((Motocicleta) v).getModelo().name();
            else if (v instanceof Van) modelo = ((Van) v).getModelo().name();

            var locacao = v.getLocacao();
            String dataLocacao = locacao != null
                    ? sdf.format(locacao.getData().getTime())
                    : "-";

            tabelaModel.addRow(new Object[]{
                (locacao != null ? locacao.getCliente().getNome() + " " + locacao.getCliente().getSobrenome() : "-"),
                v.getPlaca(),
                v.getMarca(),
                modelo,
                v.getAno(),
                dataLocacao,
                String.format("R$ %.2f", v.getValorDiariaLocacao()),
                (locacao != null ? locacao.getDias() : "-"),
                (locacao != null ? String.format("R$ %.2f", locacao.getValor()) : "-")
            });
        }
    }
}
