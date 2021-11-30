package ua.lyubchenko.repositories.entityRepositories;

import lombok.SneakyThrows;
import ua.lyubchenko.connection.ISqlHelper;
import ua.lyubchenko.connection.SqlHelper;
import ua.lyubchenko.domains.Company;
import ua.lyubchenko.repositories.EntityRepository;


import java.sql.ResultSet;

public class CompanyRepository extends EntityRepository<Company> {
    private final ISqlHelper sqlHelper = new SqlHelper();

    @Override
    protected String getTableName() {
        return "companies";
    }

    @SneakyThrows
    @Override
    protected Company getEntity(ResultSet resultSet) {
        Company company = new Company();
        company.setId(Integer.parseInt(resultSet.getString("id")));
        company.setName(resultSet.getString("name"));
        company.setLocation(resultSet.getString("location"));
        return company;
    }

    @Override
    public void create(Company company) {
        String sql = String.format("INSERT INTO %s (id, name, location) VALUES(?,?,?)", getTableName());
        sqlHelper.update(sql, preparedStatement -> {
            preparedStatement.setInt(1, company.getId());
            preparedStatement.setString(2, company.getName());
            preparedStatement.setString(3, company.getLocation());
        });
        System.out.println("COMPANY WAS CREATOR");
    }

    @Override
    public void update(Company company) {
        String sql = String.format("UPDATE %s SET name = ?, location = ? WHERE id = ?", getTableName());
        sqlHelper.update(sql, preparedStatement -> {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getLocation());
            preparedStatement.setInt(3, company.getId());
        });
        System.out.println("COMPANY WAS UPDATE");
    }
}
