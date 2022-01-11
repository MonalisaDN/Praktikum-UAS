import javafx.application.Application;
import javafx.scene.Scene;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.ResultSet;
import javafx.scene.control.*;
import javafx.scene.control.Button;

public class App extends Application {
    TableView<dataorang> tableView = new TableView<dataorang>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tabel Data");
        TableColumn<dataorang, String> columnid = new TableColumn<>("Id");
        columnid.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<dataorang, String> columnnama = new TableColumn<>("Nama");
        columnnama.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<dataorang, String> columnumur = new TableColumn<>("Umur");
        columnumur.setCellValueFactory(new PropertyValueFactory<>("umur"));

        TableColumn<dataorang, String> columntinggi = new TableColumn<>("Tinggi");
        columntinggi.setCellValueFactory(new PropertyValueFactory<>("tinggi"));

        TableColumn<dataorang, String> columnberat = new TableColumn<>("Berat");
        columnberat.setCellValueFactory(new PropertyValueFactory<>("berat"));

        tableView.getColumns().add(columnid);
        tableView.getColumns().add(columnnama);
        tableView.getColumns().add(columnumur);
        tableView.getColumns().add(columntinggi);
        tableView.getColumns().add(columnberat);

        ToolBar toolBar = new ToolBar();

        Button button1 = new Button("Add data");
        toolBar.getItems().add(button1);
        button1.setOnAction(e -> add());

        Button button2 = new Button("Delete");
        toolBar.getItems().add(button2);
        button2.setOnAction(e -> delete());

        Button button3 = new Button("Edit");
        toolBar.getItems().add(button3);
        button3.setOnAction(e -> edit());

        Button button4 = new Button("Refresh");
        toolBar.getItems().add(button4);
        button4.setOnAction(e -> re());

        VBox vbox = new VBox(tableView, toolBar);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();
        load();
        Statement stmt;
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from orang");
            tableView.getItems().clear();
            // tampilkan hasil query
            while (rs.next()) {
                tableView.getItems().add(new dataorang(rs.getInt("id"), rs.getString("nama"), rs.getString("umur"), rs.getString("tinggi"), rs.getString("berat")));
            }

            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add() {
        Stage addStage = new Stage();
        Button save = new Button("simpan");

        addStage.setTitle("add data orang");

        TextField namaField = new TextField();
        TextField umurField = new TextField();
        TextField tinggiField = new TextField();
        TextField beratField = new TextField();
        Label labelNama = new Label("Nama");
        Label labelumur = new Label("Umur");
        Label labeltinggi = new Label("Tinggi");
        Label labelberat = new Label("Berat");

        VBox hbox1 = new VBox(5, labelNama, namaField);
        VBox hbox2 = new VBox(5, labelumur, umurField);
        VBox hbox3 = new VBox(5, labeltinggi, tinggiField);
        VBox hbox4 = new VBox(5, labelberat, beratField);
        VBox vbox = new VBox(20, hbox1, hbox2, hbox3, hbox4, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "insert into orang SET nama='%s', umur='%s', tinggi='%s', berat='%s'";
                sql = String.format(sql, namaField.getText(), umurField.getText(), tinggiField.getText(), beratField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void delete() {
        Stage addStage = new Stage();
        Button save = new Button("DELETE");

        addStage.setTitle("Delete Data");

        TextField namaField = new TextField();
        Label labelnama = new Label("Nama");

        VBox hbox1 = new VBox(5, labelnama, namaField);
        VBox vbox = new VBox(20, hbox1, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "delete from orang WHERE nama='%s'";
                sql = String.format(sql, namaField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
                System.out.println();
            }
        });
        addStage.setScene(scene);
        addStage.show();
    }

    public void edit() {
        Stage addStage = new Stage();
        Button save = new Button("Simpan");

        addStage.setTitle("Edit data");

        TextField namaField = new TextField();
        TextField umurField = new TextField();
        TextField tinggiField = new TextField();
        TextField beratField = new TextField();
        Label labelNama = new Label("Nama");
        Label labelumur = new Label("Umur");
        Label labeltinggi = new Label("Tinggi");
        Label labelberat = new Label("Berat");

        VBox hbox1 = new VBox(5, labelNama, namaField);
        VBox hbox2 = new VBox(5, labelumur, umurField);
        VBox hbox3 = new VBox(5, labeltinggi, tinggiField);
        VBox hbox4 = new VBox(5, labelberat, beratField);
        VBox vbox = new VBox(20, hbox1, hbox2, hbox3, hbox4, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "UPDATE orang SET umur ='%s', tinggi = '%s', berat = '%s' WHERE nama = '%s'";
                sql = String.format(sql, umurField.getText(), tinggiField.getText(), beratField.getText(), namaField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void load() {
        Statement stmt;
        tableView.getItems().clear();
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from orang");
            while (rs.next()) {
                tableView.getItems().addAll(new dataorang(rs.getInt("id"), rs.getString("nama"), rs.getString("umur"), rs.getString("tinggi"), rs.getString("berat")));
            }
            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void re() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "ALTER TABLE orang DROP id";
            sql = String.format(sql);
            state.execute(sql);
            re2();

        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println();
        }
    }

    public void re2() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "ALTER TABLE orang ADD id INT NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST";
            sql = String.format(sql);
            state.execute(sql);
            load();
        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println();
        }
    }
}