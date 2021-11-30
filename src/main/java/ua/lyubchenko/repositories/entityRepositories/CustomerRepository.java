package ua.lyubchenko.repositories.entityRepositories;

import lombok.SneakyThrows;
import ua.lyubchenko.connection.ISqlHelper;
import ua.lyubchenko.connection.SqlHelper;
import ua.lyubchenko.domains.Customer;
import ua.lyubchenko.repositories.EntityRepository;


import java.sql.ResultSet;

public class CustomerRepository extends EntityRepository<Customer> {
    private final ISqlHelper sqlHelper = new SqlHelper();

    @Override
    protected String getTableName() {
        return "customers";
    }

    @SneakyThrows
    @Override
    protected Customer getEntity(ResultSet resultSet) {
        Customer customer = new Customer();
        customer.setId(resultSet.getInt("id"));
        customer.setName(resultSet.getString("name"));
        customer.setLocation(resultSet.getString("location"));
        return customer;
    }

    @Override
    public void create(Customer customer) {
        String sql = String.format("INSERT INTO %s (id, name, location) VALUES(?,?,?)", getTableName());
        sqlHelper.update(sql, preparedStatement -> {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getLocation());
        });
        System.out.println("CUSTOMER WAS CREATOR");

    }

    @Override
    public void update(Customer customer) {
        String sql = String.format("UPDATE  %s SET name = ?, location = ? WHERE id = ?", getTableName());
        sqlHelper.update(sql, preparedStatement -> {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getLocation());
            preparedStatement.setInt(3, customer.getId());
        });
        System.out.println("CUSTOMER WAS UPDATE");
    }
}
