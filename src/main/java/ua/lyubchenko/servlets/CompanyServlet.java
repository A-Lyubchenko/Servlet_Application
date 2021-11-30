package ua.lyubchenko.servlets;


import ua.lyubchenko.domains.Company;
import ua.lyubchenko.repositories.entityRepositories.CompanyRepository;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/companies/*")
public class CompanyServlet extends  HttpServlet {
    private final CompanyRepository companyRepository = new CompanyRepository();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createCompany")) {
            req.getRequestDispatcher("/WEB-INF/views/companyViews/create.jsp").forward(req, resp);
            return;
        } else if (requestURI.contains("/updateCompany")) {
            req.setAttribute("updateId", req.getParameter("updateId"));
            req.getRequestDispatcher("/WEB-INF/views/companyViews/update.jsp").forward(req, resp);
            return;
        }
        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            Company company = new Company();
            company.setId((int) Long.parseLong(deleteId));
            companyRepository.delete(company.getId());
            resp.sendRedirect("/companies");
            return;
        }

        req.setAttribute("companies", companyRepository.read());
        req.getRequestDispatcher("/WEB-INF/views/companyViews/read.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createCompany")) {
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String location = req.getParameter("location");
            if (id == null || id.matches("\\W+") || name == null || location == null || name.equals("") || location.equals("")
                    || name.matches("\\d+") || location.matches("\\d+")) {
                resp.sendRedirect("/companies/createCompany");
                return;

            }
            Company company = new Company(Integer.parseInt(id), name, location);

            companyRepository.create(company);
            req.getSession().setAttribute("company", company);
            resp.sendRedirect("/companies");

        } else if (requestURI.contains("/updateCompany")) {
            String name = req.getParameter("name");
            String location = req.getParameter("location");
            if (name == null || location == null || name.equals("") || location.equals("")
                    || name.matches("\\d+") || location.matches("\\d+")) {
                resp.sendRedirect("/companies/createCompany");
                return;

            }
            Company company = new Company(Integer.parseInt(req.getParameter("updateId")), name, location);
            companyRepository.update(company);
            req.getSession().setAttribute("company", company);
            resp.sendRedirect("/companies");
        }
    }

}


