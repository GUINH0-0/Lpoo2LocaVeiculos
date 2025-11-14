package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;

public class ClienteDaoSql implements ClienteDao {

    private Connection connection;

    private static ClienteDaoSql instance;

    private ClienteDaoSql(Connection connection) {
        this.connection = connection;
    }

    public static ClienteDaoSql getInstance() {
        if (instance == null) {
            try {
                instance = new ClienteDaoSql(ConnectionFactory.getConnection());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao criar ClienteDaoSql", e);
            }
        }
        return instance;
    }

    // ====================== MÉTODOS DA INTERFACE ======================

    @Override
    public void add(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nome, sobrenome, rg, cpf, endereco) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());
            stmt.setString(3, cliente.getRG());
            stmt.setString(4, cliente.getCPF());
            stmt.setString(5, cliente.getEndereco());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) cliente.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nome=?, sobrenome=?, rg=?, cpf=?, endereco=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());
            stmt.setString(3, cliente.getRG());
            stmt.setString(4, cliente.getCPF());
            stmt.setString(5, cliente.getEndereco());
            stmt.setLong(6, cliente.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Cliente cliente) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, cliente.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM clientes";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    @Override
    public Cliente getById(long id) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return criarCliente(rs);
            }
        }
        return null;
    }

    @Override
    public List<Cliente> getAll() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(criarCliente(rs));
        }
        return lista;
    }

    @Override
    public Cliente buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE cpf=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return criarCliente(rs);
            }
        }
        return null;
    }

    @Override
    public void removerPorCpf(String cpf) throws SQLException {
        String sql = "DELETE FROM clientes WHERE cpf=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        }
    }

    // ====================== MÉTODO AUXILIAR ======================

    private Cliente criarCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente(
                rs.getString("nome"),
                rs.getString("sobrenome"),
                rs.getString("rg"),
                rs.getString("cpf"),
                rs.getString("endereco")
        );
        c.setId(rs.getLong("id"));
        return c;
    }
}
