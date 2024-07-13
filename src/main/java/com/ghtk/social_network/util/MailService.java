package com.ghtk.social_network.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailService {
    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMailForgetPassword(String email, String link) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String content = "<p>Bạn vừa gửi yêu cầu thay đổi mật khẩu</p>"
                + "<p>Click vào đường dẫn bên dưới để sang trang web đổi mật khẩu:</p>"
                + "<p><a href=\"" + link + "\">Đổi mật khẩu</a></p>"
                + "<br>"
                + "<p>Nếu bạn không phải là người gửi yêu cầu này, hãy đổi mật khẩu tài khoản ngay lập tức để tránh việc bị truy cập trái phép.</p>";
        helper.setTo(email);
        String subject = "Yêu cầu thay đổi mật khẩu";
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    public void sendMailRegister(String email, String link) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String content = "<p>Bạn vừa gửi yêu cầu đăng ký tài khoản</p>"
                + "<p>Click vào đường dẫn bên dưới để hoàn thành viện tạo tài khoản mới</p>"
                + "<p><a href=\"" + link + "\">Xác nhận email</a><p>"
                + "<br>"
                + "<p>Nếu bạn không phải là người tạo tài khoản mới, vui lòng đổi bỏ qua thư này.</p>";
        helper.setTo(email);
        String subject = "Xác nhận email đăng ký";
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }
}
