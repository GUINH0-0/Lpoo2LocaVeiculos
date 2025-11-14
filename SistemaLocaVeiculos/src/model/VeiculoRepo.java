package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoRepo {

    private static final String ARQUIVO = "veiculos.dat";

    @SuppressWarnings("unchecked")
    public static List<Veiculo> load() {
        File f = new File(ARQUIVO);
        if (!f.exists()) {           
            return new ArrayList<>();
        }

        try (ObjectInputStream ois =
                new ObjectInputStream(new FileInputStream(f))) {
            return (ArrayList<Veiculo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

            return new ArrayList<>();
        }
    }

    public static void save(List<Veiculo> lista) {
        try (ObjectOutputStream oos =
                new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}