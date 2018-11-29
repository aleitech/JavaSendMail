package tech.alei.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 最基本的邮件发送代码  阿里云默认已封掉25端口，可以使用465端口调用第三方邮件服务器
 *
 * @author Steven  转自https://blog.csdn.net/songdeitao/article/details/17509189
 */
public class SSLSendMailUtil {
    public static void main(String[] args) throws Exception {
        // 创建邮件的发送过程中用到的主机和端口号的属性文件
        Properties pro = new Properties();
        // 设置邮件发送方的主机地址如果是163邮箱，则为smtp.163.com
        pro.put("mail.smtp.host", "smtp.qq.com");
        // 设置邮件发送需要认证
        pro.put("mail.smtp.auth", "true");
        // 创建邮件验证信息，即发送邮件的用户名和密码
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 重写验证方法，填写用户名，密码
                return new PasswordAuthentication("whosend@qq.com", "123456");
            }
        };

        // 如果设置,指定实现javax.net.SocketFactory接口的类的名称,这个类将被用于创建SMTP的套接字。
        pro.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        // 如果设置为true,未能创建一个套接字使用指定的套接字工厂类将导致使用java.net.Socket创建的套接字类。默认值为true。
        pro.setProperty("mail.smtp.socketFactory.fallback", "false");
        // 指定的端口连接到在使用指定的套接字工厂。如果没有设置,将使用默认端口。
        pro.setProperty("mail.smtp.socketFactory.port", "465");

        // 根据邮件会话 构建一个邮件的session
        Session sendMailSession = Session
                .getDefaultInstance(pro, authenticator);
        // 创建一个邮件消息
        Message message = new MimeMessage(sendMailSession);
        // 创建邮件发送者地址
        Address sourceAddress = new InternetAddress("whosend@qq.com");
        // 将原地址设置到消息的信息中
        message.setFrom(sourceAddress);
        // 创建邮件的接收者地址
        Address destAddress = new InternetAddress("whoreceive@163.com");
        // 将接收者的地址设置到消息的信息中
        message.setRecipient(Message.RecipientType.TO, destAddress);
        // 设置邮件的主题
        message.setSubject("Merry Christmas!");
        // 设置邮件的发送内容
        message.setText("你好，圣诞节快乐！使用465端口发送邮件");
        //可选：设置邮件的发送时间(就是对方看邮件发送的时间)，和真实时间偏差太大会被服务器拒绝
        String sendDate = "2018-11-28 17:55:00";
        Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(sendDate);
        message.setSentDate(date);
        // 发送邮件
        Transport.send(message);
    }

}
