package ua.lyubchenko.servlets;

import ua.lyubchenko.domains.Company;
import ua.lyubchenko.domains.Customer;
import ua.lyubchenko.repositories.entityRepositories.CustomerRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customers/*")
public class CustomerServlet extends HttpServlet {
    private final CustomerRepository customerRepository = new CustomerRepository();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createCustomer")) {
            req.getRequestDispatcher("/WEB-INF/views/customerViews/create.jsp").forward(req, resp);
            return;

        } else if (requestURI.contains("/updateCustomer")) {
            req.setAttribute("updateId", req.getParameter("updateId"));
            req.getRequestDispatcher("/WEB-INF/views/customerViews/update.jsp").forward(req, resp);
            return;
        }

        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            Company company = new Company();
            company.setId((int) Long.parseLong(deleteId));
            customerRepository.delete(company.getId());
            resp.sendRedirect("/customers");
            return;

        }
        req.setAttribute("customers", customerRepository.read());
        req.getRequestDispatcher("/WEB-INF/views/customerViews/read.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createCustomer")) {
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String location = req.getParameter("location");
            if (id == null || id.matches("\\W+") || name == null || location == null || name.equals("") || location.equals("")
                    || name.matches("\\d+ | \\d") || location.matches("\\d+ | \\d")){
                resp.sendRedirect("/customers/createCustomer");
                return;

            }
            Customer customer = new Customer(Integer.parseInt(id), name, location);
            customerRepository.create(customer);
            req.getSession().setAttribute("customer", customer);
            resp.sendRedirect("/customers");

        } else if (requestURI.contains("/updateCustomer")) {
            String name = req.getParameter("name");
            String location = req.getParameter("location");
            Customer customer = new Customer(Integer.parseInt(req.getParameter("updateId")), name, location);
            if (name == null || location == null || name.equals("") || location.equals("")
                    || name.matches("\\d+") || location.matches("\\d+")) {
                resp.sendRedirect("/customers/updateCustomer");
                return;

            }

            customerRepository.update(customer);
            req.getSession().setAttribute("customer", customer);
            resp.sendRedirect("/customers");
        }


    }
}


