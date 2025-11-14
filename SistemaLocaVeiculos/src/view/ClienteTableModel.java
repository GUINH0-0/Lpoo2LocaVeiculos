package view;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Cliente;

@SuppressWarnings("serial")
public class ClienteTableModel extends AbstractTableModel {

    private final String[] colunas = {"Nome", "Sobrenome", "RG", "CPF", "Endereço"};
    private List<Cliente> clientes;

    public ClienteTableModel(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public int getRowCount() {
        return clientes.size();
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
        Cliente c = clientes.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> c.getNome();
            case 1 -> c.getSobrenome();
            case 2 -> c.getRG();
            case 3 -> c.getCPF();
            case 4 -> c.getEndereco();
            default -> null;
        };
    }

    public Cliente getClienteAt(int rowIndex) {
        return clientes.get(rowIndex);
    }

    public void atualizarTabela() {
        fireTableDataChanged();
    }
}
