package model.dao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.Cliente;
import model.Locacao;
import model.Veiculo;

public class LocacaoDaoSql implements LocacaoDao {

    private static LocacaoDaoSql dao;

    private LocacaoDaoSql() {}

    public static LocacaoDaoSql getInstance() {
        if (dao == null) dao = new LocacaoDaoSql();
        return dao;
    }

    // ====================== MÉTODOS DA INTERFACE ======================

    @Override
    public Locacao getById(long id) throws SQLException, IOException {
        String sql = "SELECT * FROM locacoes WHERE id=?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return montarLocacao(rs);
            }
        }
        return null;
    }

    @Override
    public void add(Locacao locacao) throws SQLException, IOException{
        String sql = "INSERT INTO locacoes (veiculo_id, cliente_id, dias, valor, data_inicio) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, locacao.getVeiculo().getId());
            stmt.setLong(2, locacao.getCliente().getId());
            stmt.setInt(3, locacao.getDias());
            stmt.setDouble(4, locacao.getValor());
            stmt.setDate(5, new java.sql.Date(locacao.getData().getTimeInMillis()));

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) locacao.setId(generatedKeys.getLong(1));
            }
        }
    }

    @Override
    public void update(Locacao locacao) throws SQLException, IOException {
        String sql = "UPDATE locacoes SET dias=?, valor=?, data_inicio=?, cliente_id=? WHERE id=?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, locacao.getDias());
            stmt.setDouble(2, locacao.getValor());
            stmt.setDate(3, new java.sql.Date(locacao.getData().getTimeInMillis()));
            stmt.setLong(4, locacao.getCliente().getId());
            stmt.setLong(5, locacao.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Locacao locacao) throws SQLException, IOException {
        String sql = "DELETE FROM locacoes WHERE id=?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, locacao.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Locacao> getAll() throws SQLException, IOException {
        List<Locacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM locacoes";
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) lista.add(montarLocacao(rs));
        }
        return lista;
    }

    @Override
    public void deleteAll() throws SQLException, IOException {
        String sql = "DELETE FROM locacoes";
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // ====================== MÉTODOS AUXILIARES ======================

    @Override
    public List<Locacao> listarPorVeiculo(Veiculo veiculo) throws SQLException, IOException {
        String sql = "SELECT * FROM locacoes WHERE veiculo_id=?";
        List<Locacao> lista = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, veiculo.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) lista.add(montarLocacao(rs));
        }
        return lista;
    }

    @Override
    public List<Locacao> listarPorCliente(Cliente cliente) throws SQLException, IOException {
        String sql = "SELECT * FROM locacoes WHERE cliente_id=?";
        List<Locacao> lista = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, cliente.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) lista.add(montarLocacao(rs));
        }
        return lista;
    }

    @Override
     public Locacao montarLocacao(ResultSet rs) throws SQLException {
        Locacao loc = new Locacao();
        loc.setId(rs.getLong("id"));
        loc.setVeiculo(rs.getLong("veiculo_id"));
        loc.setCliente(rs.getLong("cliente_id"));
        loc.setDias(rs.getInt("dias"));
        loc.setValor(rs.getDouble("valor"));
        

        Calendar data = Calendar.getInstance();
        data.setTime(rs.getDate("data_inicio"));
        loc.setData(data);

        /*Cliente
        String cpfCliente = rs.getString("cliente_cpf"); // supondo que a coluna exista no ResultSet
        try {
            if (cpfCliente != null && !cpfCliente.isEmpty()) {
                loc.setCliente(new controller.ClienteController().buscarPorCpf(cpfCliente));
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar cliente pelo CPF: " + e.getMessage());
        }
		*/

        return loc;
    }
}
