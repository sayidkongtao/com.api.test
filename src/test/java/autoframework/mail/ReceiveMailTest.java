package autoframework.mail;

import autoframework.utils.Utils;
import org.testng.annotations.Test;

import javax.mail.*;
import java.io.*;
import java.util.*;

public class ReceiveMailTest {

    public String s = "";
    final String content = "";

    @Test
    public void receiveMail1() throws Exception {
        String path = Utils.getAbsolutePath("email/126.properties");
        ReceiveMail receiveMail = new ReceiveMail("test_Toyota001@126.com", "VZKJGEYTOLRMVRFU", path);

        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-5); //把日期往后增加一天,整数  往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果

        String code = receiveMail.verificationCode("Your Toyota App Verification Code", date, "(?<=verification code: )\\d+");
        System.out.println(code);
    }

    @Test
    public void test1() throws IOException {
        String path = Utils.getAbsolutePath("email/126.properties");
        Properties props = new Properties();
        props.load(new FileInputStream(path));

        Enumeration enum1 = props.propertyNames();

        while(enum1.hasMoreElements()) {
            String strKey = (String) enum1.nextElement();
            String strValue = props.getProperty(strKey);
            System.out.println(strKey + "=" + strValue);
        }
    }

    @Test
    public void test2() throws IOException {
        Date date1 = new Date();
        Utils.sleepBySecond(2);
        Date date2 = new Date();

        System.out.println(date1.getTime() > date2.getTime());


     }

    @Test
    public void receiveMail() throws Exception {
        String host = "pop.126.com";
        String username = "test_Toyota001@126.com";
        String password = "VZKJGEYTOLRMVRFU";

        // Create empty properties
        Properties props = new Properties();
        props.setProperty("mail.pop3.host", host);

        // Get session
        Session session = Session.getDefaultInstance(props, null);

        // Get the store
        Store store = session.getStore("pop3");
        store.connect(host, username, password);

        // Get folder
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);

        // Get directory
        Message message[] = folder.getMessages();

        for (int i = 0, n = message.length; i < n; i++) {

            System.out.println(i + ": " + message[i].getFrom()[0]

                    + "/t" + message[i].getSubject() + "/t" + message[i].getSentDate());
            //System.out.println("Email Content");
           // System.out.println("Type: " + message[i].getContentType());
           // System.out.println("Content: " + message[i].getContent());
        }
        folder.close(false);
        store.close();
/*        this. s = "";
        try{
            getAllMultipart(message[2]);
        }finally {
            // Close connection
            folder.close(false);
            store.close();
        }

        // System.out.println(s);

        Pattern p = Pattern.compile("(?<=verification code: )\\d+");
        Matcher m = p.matcher(s);

        if(m.find()) {
            System.out.println(m.group());
        }*/

    }

    private  void getAllMultipart(Part part) throws Exception {
        String contentType = part.getContentType();
        int index = contentType.indexOf("name");
        boolean conName = false;
        if (index != -1) {
            conName = true;
        }
        //判断part类型
        if (part.isMimeType("text/plain") && !conName) {
            //System.out.println((String) part.getContent());
            this.s = this.s + (String) part.getContent();
        } else if (part.isMimeType("text/html") && !conName) {
            //System.out.println((String) part.getContent());
            this.s = this.s + (String) part.getContent();
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                //递归获取数据
                getAllMultipart(multipart.getBodyPart(i));
                //附件可能是截图或上传的(图片或其他数据)
                if (multipart.getBodyPart(i).getDisposition() != null) {
                    //附件为截图
                    if (multipart.getBodyPart(i).isMimeType("image/*")) {
                        InputStream is = multipart.getBodyPart(i)
                                .getInputStream();
                        String name = multipart.getBodyPart(i).getFileName();
                        String fileName;
                        //截图图片
                        if (name.startsWith("=?")) {
                            fileName = name.substring(name.lastIndexOf(".") - 1, name.lastIndexOf("?="));
                        } else {
                            //上传图片
                            fileName = name;
                        }

                        FileOutputStream fos = new FileOutputStream("D:\\"
                                + fileName);
                        int len = 0;
                        byte[] bys = new byte[1024];
                        while ((len = is.read(bys)) != -1) {
                            fos.write(bys, 0, len);
                        }
                        fos.close();
                    } else {
                        //其他附件
                        InputStream is = multipart.getBodyPart(i)
                                .getInputStream();
                        String name = multipart.getBodyPart(i).getFileName();
                        FileOutputStream fos = new FileOutputStream("D:\\"
                                + name);
                        int len = 0;
                        byte[] bys = new byte[1024];
                        while ((len = is.read(bys)) != -1) {
                            fos.write(bys, 0, len);
                        }
                        fos.close();
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            getAllMultipart((Part) part.getContent());
        }
    }

    /**
     * 解析附件内容
     *
     * @param part
     * @throws Exception
     */
    private void getAttachmentMultipart(Part part) throws Exception {
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int count = multipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.getDisposition() != null) {
                    InputStream is = bodyPart.getInputStream();
                    FileOutputStream fos = new FileOutputStream("路径+文件名");
                    int len = 0;
                    byte[] bys = new byte[1024];
                    while ((len = is.read(bys)) != -1) {
                        fos.write(bys, 0, len);
                    }
                    fos.close();
                }
            }
        }

    }
}
