package tech.alei.mail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 最基本的邮件发送代码
 *
 * @author Steven  转自https://blog.csdn.net/songdeitao/article/details/17509189
 */
public class SendMailUtil {
    public static void main(String[] args) throws Exception {
        // 创建邮件的发送过程中用到的主机和端口号的属性文件
        Properties pro = new Properties();
        // 设置邮件发送方的主机地址如果是163邮箱，则为smtp.163.com
        pro.put("mail.smtp.host", "smtp.qq.com");
        // 设置发送邮件端口号
        pro.put("mail.smtp.port", "25");
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
        message.setText("你好，圣诞节快乐！使用25端口发送邮件");
        //可选：设置邮件的发送时间(就是对方看邮件发送的时间)，和真实时间偏差太大会被服务器拒绝
        String sendDate = "2018-11-28 17:55:00";
        Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(sendDate);
        message.setSentDate(date);
        // 发送邮件
        Transport.send(message);
    }

}
