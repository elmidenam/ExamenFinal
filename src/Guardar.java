import javax.swing.*;
import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

public class Guardar extends JFrame {
    private BufferedReader reader;
    private String datosCSV;
    private String bd = "inventario";
    private String ip = "localhost:5432";

    public Guardar() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JFileChooser input = new JFileChooser();
        input.showOpenDialog(null);
        input.setDialogTitle("Examen Elmidena Mazariegos");
        File archivo;

        try{
            archivo = input.getSelectedFile(); //selecciona el archivo
            leer(String.valueOf(archivo));
            //confirmamos la importacion
            JOptionPane.showMessageDialog(null, "Importado OK");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void leer(String archivo){
        try {
            reader = new BufferedReader(new FileReader(archivo));
            while ((datosCSV = reader.readLine()) != null) {
                importarBD();
            }
            reader.close();
            datosCSV = null;
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void importarBD() {
        String db = "jdbc:postgresql://"+ip+"/"+bd;

        try{
            Class.forName("org.postgresql.Driver");
            Connection conexion = DriverManager.getConnection(db, "root", "elmidena");
            Statement createStatement = conexion.createStatement();
            //hacemos el query
            PreparedStatement newStatement = conexion.prepareStatement("insert into inventario values (?, ?)");

            //aqui se separan por comas los registros del archivo
            String registros [] = datosCSV.split(",");

            newStatement.setString(1, registros[0]);
            newStatement.setString(2, registros[1]);
            newStatement.executeQuery();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}

