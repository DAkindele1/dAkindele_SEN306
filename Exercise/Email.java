package Exercise;

public class Email {
    void send(String to, String subject, String body) {
        System.out.println("Email to " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }
}
