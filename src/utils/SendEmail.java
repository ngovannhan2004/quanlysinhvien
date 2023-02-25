package utils;

public class SendEmail extends Thread {
    private String to;
    private String name;
    private String newPassword;
    private String loginUrl;

    public SendEmail(String to, String name, String newPassword, String loginUrl) {
        this.to = to;
        this.name = name;
        this.newPassword = newPassword;
        this.loginUrl = loginUrl;
    }

    public void run() {
        try {
            MailUtil emailUtil = new MailUtil();
            emailUtil.sendNewPasswordFromHtml(to, name, newPassword, loginUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
