package presentation.commands;

import data.models.RoleEnum;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.UserController;
import logic.exceptions.CommandException;
import logic.exceptions.UserException;
import data.models.User;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;

/**
 *
 * @author Andreas Vikke
 */
public class LoginCommand extends Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            boolean valid = UserController.validateUser(email, password);

            if (valid) {
                User user = UserController.getUser(email);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                
                response.addHeader("redirect", request.getContextPath() + "/account");
                request.getRequestDispatcher("/employee").forward(request, response);
            } else {
                throw new CommandException("Incorrect username and/or password");
            }
        } catch (UserException | SQLException | ServletException | IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            throw new CommandException(ex.getMessage());
        }
    }
}
