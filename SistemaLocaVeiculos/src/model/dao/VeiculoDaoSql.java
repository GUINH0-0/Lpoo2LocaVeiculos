package model.dao;

import enums.*;
import java.sql.*;
import java.util.*;
import model.*;

public class VeiculoDaoSql implements VeiculoDao {

    private Connection connection;

    private static VeiculoDaoSql instance;

    private VeiculoDaoSql(Connection connection) {
        this.connection = connection;
    }

    public static VeiculoDaoSql getInstance() {
        if (instance == null) {
            try {
                instance = new VeiculoDaoSql(ConnectionFactory.getConnection());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao criar VeiculoDaoSql", e);
            }
        }
        return instance;
    }

    // ====================== MÉTODOS DA INTERFACE ======================

    @Override
    public void add(Veiculo veiculo) throws SQLException {
        String sql = "INSERT INTO veiculos (tipo, placa, marca, categoria, estado, ano, valor_compra, modelo) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, veiculo.getClass().getSimpleName());
            ps.setString(2, veiculo.getPlaca());
            ps.setString(3, veiculo.getMarca().name());
            ps.setString(4, veiculo.getCategoria().name());
            ps.setString(5, veiculo.getEstado().name());
            ps.setInt(6, veiculo.getAno());
            ps.setDouble(7, veiculo.getValorDeCompra());

            if (veiculo instanceof Automovel a) {
                ps.setString(8, a.getModelo().name());
            } else if (veiculo instanceof Van v) {
                ps.setString(8, v.getModelo().name());
            } else if (veiculo instanceof Motocicleta m) {
                ps.setString(8, m.getModelo().name());
            } else {
                ps.setString(8, null);
            }

            ps.executeUpdate();
        }
    }

    @Override
    public void update(Veiculo veiculo) throws SQLException {
        String sql = "UPDATE veiculos SET tipo = ?, marca = ?, categoria = ?, estado = ?, ano = ?, valor_compra = ?, modelo = ? "
                + "WHERE placa = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, veiculo.getClass().getSimpleName());
            ps.setString(2, veiculo.getMarca().name());
            ps.setString(3, veiculo.getCategoria().name());
            ps.setString(4, veiculo.getEstado().name());
            ps.setInt(5, veiculo.getAno());
            ps.setDouble(6, veiculo.getValorDeCompra());

            if (veiculo instanceof Automovel a) {
                ps.setString(7, a.getModelo().name());
            } else if (veiculo instanceof Van v) {
                ps.setString(7, v.getModelo().name());
            } else if (veiculo instanceof Motocicleta m) {
                ps.setString(7, m.getModelo().name());
            } else {
                ps.setString(7, null);
            }

            ps.setString(8, veiculo.getPlaca());

            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Veiculo veiculo) throws SQLException {
        String sql = "DELETE FROM veiculos WHERE placa = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, veiculo.getPlaca());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM veiculos";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    @Override
    public Veiculo getById(long id) throws SQLException {
        // Aqui você precisa ter um campo ID na tabela; se não tiver, pode usar placa
        String sql = "SELECT * FROM veiculos WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return criarVeiculo(rs);
        }
        return null;
    }

    @Override
    public Veiculo buscarPorPlaca(String placa) throws SQLException {
        String sql = "SELECT * FROM veiculos WHERE placa=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, placa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return criarVeiculo(rs);
        }
        return null;
    }

    @Override
    public List<Veiculo> getAll() throws SQLException {
        List<Veiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM veiculos";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(criarVeiculo(rs));
        }
        return lista;
    }

    // ====================== MÉTODO AUXILIAR ======================

    public Veiculo criarVeiculo(ResultSet rs) throws SQLException {
    	Long id = rs.getLong("id");
        String tipo = rs.getString("tipo");
        String modeloStr = rs.getString("modelo");
        Estado estado = Estado.valueOf(rs.getString("estado"));
        Marca marca = Marca.valueOf(rs.getString("marca"));
        Categoria categoria = Categoria.valueOf(rs.getString("categoria"));
        String placa = rs.getString("placa");
        int ano = rs.getInt("ano");
        double valorCompra = rs.getDouble("valor_compra");

        return switch (tipo) {
            case "Automovel" -> new Automovel(id, estado, marca, categoria, ModeloAutomovel.valueOf(modeloStr), placa, ano, valorCompra);
            case "Van" -> new Van(id, estado, marca, categoria, ModeloVan.valueOf(modeloStr), placa, ano, valorCompra);
            case "Motocicleta" -> new Motocicleta(id, estado, marca, categoria, ModeloMotocicleta.valueOf(modeloStr), placa, ano, valorCompra);
            default -> throw new IllegalArgumentException("Tipo de veículo inválido: " + tipo);
        };
    }


    public void deleteByPLaca(String placa) throws SQLException {
    String sql = "DELETE FROM veiculo WHERE placa = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, placa);
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            System.out.println("⚠️ Nenhum veículo encontrado com a placa: " + placa);
        } else {
            System.out.println("✅ Veículo deletado com sucesso!");
        }
    }
}

}


