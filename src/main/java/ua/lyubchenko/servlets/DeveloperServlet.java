package ua.lyubchenko.servlets;

import ua.lyubchenko.domains.Developer;
import ua.lyubchenko.repositories.entityRepositories.DeveloperRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/developers/*")
public class DeveloperServlet extends HttpServlet {
    private final DeveloperRepository developerRepository = new DeveloperRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createDeveloper")) {
            req.getRequestDispatcher("/WEB-INF/views/developerViews/create.jsp").forward(req, resp);
            return;

        } else if (requestURI.contains("/updateDeveloper")) {
            req.setAttribute("updateId", req.getParameter("updateId"));
            req.getRequestDispatcher("/WEB-INF/views/developerViews/update.jsp").forward(req, resp);
            return;
        }

        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            Developer developer = new Developer();
            developer.setId((int) Long.parseLong(deleteId));
            developerRepository.delete(developer.getId());
            resp.sendRedirect("/developers");

        } else {
            req.setAttribute("developers", developerRepository.read());
            req.getRequestDispatcher("/WEB-INF/views/developerViews/read.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createDeveloper")) {
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String age = req.getParameter("age");
            String sex = req.getParameter("sex");
            String phone_number = req.getParameter("phone_number");
            String salary = req.getParameter("salary");
            if (id == null || id.matches("\\W+") || name == null || name.matches("\\d+") || name.equals("") || age.matches("\\W+")
                    || sex == null || sex.equals("") || !sex.equals("male") && !sex.equals("female")
                    || !phone_number.matches("\\d{10}") || !salary.matches("\\d{1,5}")) {
                resp.sendRedirect("/developers/createDeveloper");
                return;

            }
            Developer developer = new Developer(Integer.parseInt(id), name, Integer.parseInt(age), sex, phone_number, Integer.parseInt(salary));
            developerRepository.create(developer);
            req.getSession().setAttribute("developer", developer);
            resp.sendRedirect("/developers");

        } else if (requestURI.contains("/updateDeveloper")) {
            String name = req.getParameter("name");
            String age = req.getParameter("age");
            String sex = req.getParameter("sex");
            String phone_number = req.getParameter("phone_number");
            String salary = req.getParameter("salary");
            if (name == null || name.matches("\\d+") || name.equals("") || age.matches("\\W+")
                    || sex == null || sex.equals("") || !sex.equals("male") && !sex.equals("female")
                    || !phone_number.matches("\\d{10}") || !salary.matches("\\d{1,5}")) {
                resp.sendRedirect("/developers/updateDeveloper");
                return;

            }
            Developer developer = new Developer(Integer.parseInt(req.getParameter("updateId")),
                    name, Integer.parseInt(age), sex, phone_number, Integer.parseInt(salary));
            developerRepository.update(developer);
            req.getSession().setAttribute("developer", developer);
            resp.sendRedirect("/developers");
        }
    }
}
