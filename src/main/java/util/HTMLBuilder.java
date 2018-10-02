package util;

import model.User;

import java.util.List;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 10. 2..
 */
public class HTMLBuilder {
    public static String userList(List<User> users) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<div class=\"container\" id=\"main\">\n");
        htmlBuilder.append("<div class=\"col-md-10 col-md-offset-1\">\n");
        htmlBuilder.append("      <div class=\"panel panel-default\">\n");
        htmlBuilder.append("          <table class=\"table table-hover\">\n");
        htmlBuilder.append("              <thead>\n");
        htmlBuilder.append("                <tr>\n");
        htmlBuilder.append("                    <th>#</th> <th>사용자 아이디</th> <th>이름</th> <th>이메일</th><th></th>\n");
        htmlBuilder.append("                </tr>\n");
        htmlBuilder.append("              </thead>\n");
        htmlBuilder.append("              <tbody>\n");
        for (int seq = 1; seq <= users.size(); ++seq) {
            User user = users.get(seq - 1);
            htmlBuilder.append("<tr>\n");
            htmlBuilder.append("<th scope=\\\"row\\\">" + seq + "</th>" +
                    " <td> " + user.getUserId() + "</td>" +
                    " <td>" + user.getName() + "</td>" +
                    " <td>" + user.getEmail() + "</td>" +
                    "<td><a href=\\\"#\\\" class=\\\"btn btn-success\\\" role=\\\"button\\\">수정</a></td>\\n\"");
            htmlBuilder.append("</tr>\n");
        }
        htmlBuilder.append(" </tbody>\n </table>\n </div>\n </div>\n </div>");

        return htmlBuilder.toString();
    }
}
