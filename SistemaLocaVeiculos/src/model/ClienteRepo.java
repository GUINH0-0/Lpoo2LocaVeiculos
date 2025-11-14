package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepo {

    private static final String ARQUIVO = "clientes.dat"; 

    @SuppressWarnings("unchecked")
    public static List<Cliente> load() {
        FileInputStream f;
        try {
            f = new FileInputStream(ARQUIVO);
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(f)) {
            return (ArrayList<Cliente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void save(List<Cliente> lista) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
