package tr.edu.ku.devnull.needforspear.Model.Player;

import tr.edu.ku.devnull.needforspear.Viewmodel.MailVerification.MailSender;

/**
 * Account is a class which contains information regarding the account
 * of a player and signals to send them verification codes regarding their account.
 */
public class Account {
    private String uid;
    private String username;
    private String email;
    private String password;
    private Integer verificationCode;
    private boolean isVerified = false;

    /**
     * Constructor for Account.
     */
    public Account() {
    }

    /**
     * Constructor for Account
     *
     * @param username String containing the username of account.
     * @param email    String containing the email of account.
     * @param password String containing the password of account.
     */
    public Account(String username, String email, String password) {
        this.uid = "" + System.currentTimeMillis();
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Sends verification message to the email account of the user.
     */
    public void sendVerification() {
        MailSender ms = new MailSender(email, username);
        if (ms.sendVerification()) {
            verificationCode = ms.getVerificationCode();
            System.out.println("Verification is sent.");
        }
    }

    /**
     * Sends verification message to the email account of the user.
     *
     * @param email String containing the email the verification message will be sent to.
     */
    public void sendVerification(String email) {
        MailSender ms = new MailSender(email);
        if (ms.sendVerification()) {
            verificationCode = ms.getVerificationCode();
            System.out.println("Verification is sent.");
        }
    }

    /**
     * Gets the unique id of account.
     *
     * @return String containing uid of Account.
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets the unique id of account.
     *
     * @param uid String containing uid to be set for Account.
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Gets the username of account.
     *
     * @return String containing username of Account.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of account.
     *
     * @param username String containing username to be set for Account.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email of account.
     *
     * @return String containing email of Account.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of account.
     *
     * @param email String containing email to be set for Account.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of account.
     *
     * @return String containing password of Account.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of account.
     *
     * @param password String containing password to be set for Account.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the verification code created of account.
     *
     * @return Integer value of verificationCode for Account.
     */
    public Integer getVerificationCode() {
        return verificationCode;
    }

    /**
     * Sets the verification code of account.
     *
     * @param verificationCode Integer value of verificationCode to be set for Account.
     */
    public void setVerificationCode(Integer verificationCode) {
        this.verificationCode = verificationCode;
    }

    /**
     * Checks whether account is verified or not.
     *
     * @return Boolean value regarding the verification of Account.
     */
    public boolean isVerified() {
        return isVerified;
    }

    /**
     * Sets the verification status of account.
     *
     * @param verified Boolean value regarding verification of Account.
     */
    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    /**
     * Textually represents the attributes of game map.
     *
     * @return String containing information of Account.
     */
    @Override
    public String toString() {
        return "Account Info of " + username + '\n' +
                "----------------------------------" + '\n' +
                "Password: " + password + '\n' +
                "User identification number: " + uid + '\n' +
                "User email address: " + email + '\n';
    }
}
