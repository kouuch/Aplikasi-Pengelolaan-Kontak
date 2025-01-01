import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class ContactManager {

    // Add a new contact
    public static void addContact(String name, String phone, String address, String category) throws SQLException {
        String sql = "INSERT INTO contacts (name, phone, address, category) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, address);
            pstmt.setString(4, category);
            pstmt.executeUpdate();
        }
    }

    // Get all contacts
    public static DefaultTableModel getAllContacts() throws SQLException {
        String sql = "SELECT * FROM contacts";
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nama", "Telepon", "Alamat", "Kategori"}, 0);

        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("category")
                });
            }
        }
        return model;
    }

    // Update a contact
    public static void updateContact(int id, String name, String phone, String address, String category) throws SQLException {
        String sql = "UPDATE contacts SET name = ?, phone = ?, address = ?, category = ? WHERE id = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, address);
            pstmt.setString(4, category);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        }
    }

    // Delete a contact
    public static void deleteContact(int id) throws SQLException {
        String sql = "DELETE FROM contacts WHERE id = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // Search contacts by name or phone
    public static DefaultTableModel searchContacts(String keyword) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE name LIKE ? OR phone LIKE ?";
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nama", "Telepon", "Alamat", "Kategori"}, 0);

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("category")
                    });
                }
            }
        }
        return model;
    }

    // Export contacts to CSV
    public static void exportContacts(String filePath) throws SQLException, IOException {
        String sql = "SELECT * FROM contacts";

        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             FileWriter writer = new FileWriter(filePath)) {

            // Write CSV header
            writer.write("ID,Name,Phone,Address,Category\n");

            // Write data
            while (rs.next()) {
                writer.write(rs.getInt("id") + ",");
                writer.write(rs.getString("name") + ",");
                writer.write(rs.getString("phone") + ",");
                writer.write(rs.getString("address") + ",");
                writer.write(rs.getString("category") + "\n");
            }
        }
    }

    // Import contacts from CSV
    public static void importContacts(String filePath) throws SQLException, IOException {
        String sql = "INSERT INTO contacts (name, phone, address, category) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip CSV header
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length < 4) continue; // Skip invalid rows

                pstmt.setString(1, data[1]); // Name
                pstmt.setString(2, data[2]); // Phone
                pstmt.setString(3, data[3]); // Address
                pstmt.setString(4, data[4]); // Category
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        }
    }
}
