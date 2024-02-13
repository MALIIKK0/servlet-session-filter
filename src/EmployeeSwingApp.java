import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class EmployeeSwingApp extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public EmployeeSwingApp() {
        setTitle("Employee Management");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Position");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshData() {
        model.setRowCount(0); // Clear existing data

        try {
            URL url = new URL("http://localhost:8080/YourWebApp/GetEmployeeServlet");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder response = new StringBuilder();
            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                String position = jsonObject.getString("position");
                model.addRow(new Object[]{id, name, position});
            }

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                EmployeeSwingApp app = new EmployeeSwingApp();
                app.setVisible(true);
            }
        });
    }
}
