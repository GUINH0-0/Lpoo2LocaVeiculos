package controller;

import java.sql.Connection;
import java.sql.Statement;
import model.dao.ConnectionFactory;

public class DatabaseInitializer {

    public static void initialize() {
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {

            // Tabela de clientes
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS clientes (
                    id SERIAL PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    sobrenome VARCHAR(100) NOT NULL,
                    rg VARCHAR(20),
                    cpf VARCHAR(20) UNIQUE NOT NULL,
                    endereco VARCHAR(200)
                );
            """);

            // Tabela de veículos
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS veiculos (
                    id SERIAL PRIMARY KEY,
                    placa VARCHAR(10) UNIQUE NOT NULL,
                    estado VARCHAR(20),
                    marca VARCHAR(50),
                    modelo VARCHAR(50),
                    ano INT,
                    categoria VARCHAR(50),
                    valor_compra NUMERIC(10,2)
                );
            """);

            // Tabela de locações
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS locacoes (
                    id SERIAL PRIMARY KEY,
                    veiculo_id INT NOT NULL,
                    cliente_id INT NOT NULL,
                    dias INT NOT NULL,
                    valor NUMERIC(10,2) NOT NULL,
                    data_inicio DATE NOT NULL,
                    FOREIGN KEY (veiculo_id) REFERENCES veiculos(id) ON DELETE CASCADE,
                    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
                );
            """);

            System.out.println("Banco de dados inicializado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
        }
    }
}
