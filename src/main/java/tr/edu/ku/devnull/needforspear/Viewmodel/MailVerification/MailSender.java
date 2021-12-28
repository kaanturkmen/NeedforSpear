package tr.edu.ku.devnull.needforspear.Viewmodel.MailVerification;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * MailSender is a class which has a main method which sends an email
 * to the player's address with a generated code to guide them during the
 * authentication or recovery of their accounts.
 *
 * @author Kaan Turkmen
 */
public class MailSender {
    private final Integer verificationCode;
    private final String userMail;
    private final String username;

    /**
     * Constructor for MailSender.
     *
     * @param userMail String containing email address of user.
     * @param username String containing username of user.
     */
    public MailSender(String userMail, String username) {
        this.userMail = userMail;
        this.username = username;
        this.verificationCode = getRandomCode();
    }

    /**
     * Constructor for MailSender
     *
     * @param userMail String containing email address of user.
     */
    public MailSender(String userMail) {
        this.userMail = userMail;
        this.username = null;
        this.verificationCode = getRandomCode();
    }

    /**
     * Sends players an email containing information regarding
     * their authentication or recovery request.
     *
     * @return Boolean which evaluates if the email was successfully sent.
     */
    public boolean sendVerification() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(MailConstants.SENDER_MAIL, MailConstants.SENDER_MAIL_PASSWORD);
                    }
                });
        try {
            if (username != null) {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(MailConstants.SENDER_MAIL, "Need for Spear - Devnull"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(this.userMail));
                message.setSubject("Verify your Need for Spear Account!");
                message.setText("Hello " + username + ",\n" + "You have just registered to our game Need for Spear with this email. "
                        + "You need to verify your account to play our game. To do that, please enter the code into the game. \n\n" + "Verification Code: "
                        + verificationCode + "\n\n" + "If this was not you who registered our game, you can ignore this mail." + "\n\n" + "Thanks,\nDevnull");

                Transport.send(message);

                return true;
            } else {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(MailConstants.SENDER_MAIL, "Need for Spear - Devnull"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(this.userMail));
                message.setSubject("Verify your Need for Spear Account!");
                message.setText("Hello, \n" + "You have requested a verification code to reset your password. " +
                        "\n\n" + "Verification Code: " + verificationCode + "\n\n" + "If it was not you who did not requested this, you can ignore this mail." + "\n\n" + "Thanks,\nDevnull");

                Transport.send(message);

                return true;
            }

        } catch (MessagingException | UnsupportedEncodingException e) {
            return false;
        }
    }

    /**
     * Creates a randomly generated authentication code.
     *
     * @return Integer value of generated code.
     */
    private Integer getRandomCode() {
        Random r = new Random();
        int number = r.nextInt(999999);
        return Integer.parseInt(String.format("%06d", number));
    }

    /**
     * Gets the verification code of MailSender.
     *
     * @return Integer value of verification code.
     */
    public Integer getVerificationCode() {
        return verificationCode;
    }
}
