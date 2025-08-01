import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "abc123";
        String hash = encoder.encode(password);
        System.out.println("Password: " + password);
        System.out.println("Hash: " + hash);
        
        // 验证现有的哈希
        String existingHash = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKyZFzlNAcqmvJ9iUorJkKbqKT4O";
        System.out.println("Existing hash matches abc123: " + encoder.matches(password, existingHash));
        System.out.println("New hash matches abc123: " + encoder.matches(password, hash));
    }
}
