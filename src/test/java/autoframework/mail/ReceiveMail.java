package autoframework.mail;

import autoframework.utils.Utils;
import org.apache.commons.lang3.StringUtils;

import javax.mail.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceiveMail {

    private String username;
    private String password;
    private String emailConfigPath;

    private Properties props;
    private Store store;
    private Folder folder;
    private String finalContent;

    public String getFinalContent() {
        return finalContent;
    }

    public ReceiveMail(String username, String password, String emailConfigPath) {
        this.username = username;
        this.password = password;
        this.emailConfigPath = emailConfigPath;
    }

    public String verificationCode(String emailSubject, Date dateBeforeInitCode, String regex) throws Exception {

        String code = "";
        boolean find = false;
        int countConnectToEmail = 3;
        int countGetEmailMessage = 5;

        do {
            try {
                loadConfig();
                connectToEmailServer();

                do {
                    Message message[] = getMessage();

                    for (int i = 0, n = message.length; i < n; i++) {
                        String subject = message[i].getSubject();
                        if (StringUtils.endsWithIgnoreCase(subject, emailSubject)) {
                            Date sentData = message[i].getSentDate();
                            if (sentData.getTime() > dateBeforeInitCode.getTime()) {
                                this.finalContent = "";
                                getAllMultipart(message[i]);
                                find = true;
                                break;
                            }
                        }
                    }
                    countGetEmailMessage = countGetEmailMessage - 1;
                    Utils.sleepBySecond(15);
                } while (!find&&countGetEmailMessage > 0);

            } finally {
                close();
            }

            countConnectToEmail = countConnectToEmail - 1;

        } while (!find&&countConnectToEmail > 0);

        if(find) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(this.finalContent);

            if(m.find()) {
                code = m.group();
                return code;
            }

            System.out.println("The email content is \n" + this.finalContent);

            throw new Exception("Failed to get the code, please check the email then update the algorithm");
        }

        throw new Exception("Failed to obtained the email In the default time");
    }

    private void loadConfig() throws IOException {
        // Create empty properties
        this.props = new Properties();
        this.props.load(new FileInputStream(this.emailConfigPath));
    }

    private void connectToEmailServer() throws MessagingException {
        // Get session
        Session session = Session.getDefaultInstance(props, null);

        // Get the store
        this.store = session.getStore();
        store.connect(props.getProperty("mail.pop3.host"), username, password);
    }

    private Message[] getMessage() throws MessagingException {
        // Get folder
        this.folder = store.getFolder("INBOX");
        this.folder .open(Folder.READ_ONLY);

        // Get directory
        Message message[] = this.folder.getMessages();
        return message;
    }

    private void close() {
        try {
            if(this.folder != null) {
                this.folder.close(false);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        try {
            if(this.store != null) {
                this.store.close();
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void getAllMultipart(Part part) throws Exception {
        String contentType = part.getContentType();
        int index = contentType.indexOf("name");
        boolean conName = false;
        if (index != -1) {
            conName = true;
        }

        if (part.isMimeType("text/plain") && !conName) {
            this.finalContent = this.finalContent + (String) part.getContent();
        } else if (part.isMimeType("text/html") && !conName) {
            this.finalContent = this.finalContent + (String) part.getContent();
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                getAllMultipart(multipart.getBodyPart(i));
            }
        }
    }

}
