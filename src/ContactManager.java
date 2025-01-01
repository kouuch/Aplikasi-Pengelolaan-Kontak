import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
