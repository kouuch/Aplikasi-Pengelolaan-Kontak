import com.mysql.cj.xdevapi.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class ContactManager {

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
}
