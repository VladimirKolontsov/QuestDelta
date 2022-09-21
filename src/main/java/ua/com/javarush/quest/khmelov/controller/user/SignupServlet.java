package ua.com.javarush.quest.khmelov.controller.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.javarush.quest.khmelov.dto.FormData;
import ua.com.javarush.quest.khmelov.dto.UserDto;
import ua.com.javarush.quest.khmelov.entity.Role;
import ua.com.javarush.quest.khmelov.service.AvatarService;
import ua.com.javarush.quest.khmelov.service.UserService;
import ua.com.javarush.quest.khmelov.util.Go;
import ua.com.javarush.quest.khmelov.util.Jsp;

import java.io.IOException;
import java.util.Optional;

import static ua.com.javarush.quest.khmelov.util.Jsp.Key.ROLES;

@MultipartConfig(fileSizeThreshold = 1 << 20)
@WebServlet(Go.SIGNUP)
public class SignupServlet extends HttpServlet {

    private final UserService userService = UserService.INSTANCE;
    private final AvatarService avatarService = AvatarService.INSTANCE;

    @Override
    public void init() {
        getServletContext().setAttribute(ROLES, Role.values());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Jsp.getId(req);
        Optional<UserDto> opUser = userService.get(id);
        opUser.ifPresent(value -> req.setAttribute("user", value));
        Jsp.forward(req, resp, Go.SIGNUP);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        userService.create(FormData.of(req));
        avatarService.updateAvatar(req);
        Jsp.redirect(req, resp, Go.USERS);
    }

}