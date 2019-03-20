package presentation.commands;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.UserController;
import logic.exceptions.CommandException;
import logic.exceptions.UserException;

/**
 *
 * @author Andreas Vikke
 */
public class RegisterCommand extends Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String email = request.getParameter("email");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        
        if (password1.equals(password2)) {
            try {
                UserController.createUser(email, password1);
                
                response.addHeader("redirect", request.getContextPath() + "/login");
                request.getRequestDispatcher("/customer").forward(request, response);
                
            } catch (UserException | SQLException | ServletException | IOException ex) {
                throw new CommandException(ex.getMessage());
            }
        } else {
            throw new CommandException("The two passwords did not match");
        }
    }

}