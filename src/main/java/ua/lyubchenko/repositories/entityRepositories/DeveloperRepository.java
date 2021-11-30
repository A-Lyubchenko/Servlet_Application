package ua.lyubchenko.repositories.entityRepositories;

import lombok.SneakyThrows;
import ua.lyubchenko.connection.ISqlHelper;
import ua.lyubchenko.connection.SqlHelper;
import ua.lyubchenko.domains.Developer;
import ua.lyubchenko.repositories.EntityRepository;


import java.sql.ResultSet;

public class DeveloperRepository extends EntityRepository<Developer> {
    private final ISqlHelper sqlHelper = new SqlHelper();

    @Override
    protected String getTableName() {
        return "developers";
    }

    @SneakyThrows
    @Override
    protected Developer getEntity(ResultSet resultSet) {
        Developer developer = new Developer();
        developer.setId(resultSet.getInt("id"));
        developer.setName(resultSet.getString("name"));
        developer.setAge(Integer.parseInt(resultSet.getString("age")));
        developer.setSex(resultSet.getString("sex"));
        developer.setPhone_number(resultSet.getString("phone_number"));
        developer.setSalary(Integer.parseInt(resultSet.getString("salary")));
        return developer;
    }

    @Override
    public void create(Developer developer) {
        String sql = String.format("INSERT INTO %s (id, name, age, sex, phone_number, salary) VALUES(?,?,?,?,?,?)", getTableName());
        sqlHelper.update(sql, preparedStatement -> {
            preparedStatement.setInt(1, developer.getId());
            preparedStatement.setString(2, developer.getName());
            preparedStatement.setInt(3, developer.getAge());
            preparedStatement.setString(4, developer.getSex());
            preparedStatement.setString(5, developer.getPhone_number());
            preparedStatement.setInt(6, developer.getSalary());
        });
        System.out.println("DEVELOPER WAS CREATOR");

    }

    @Override
    public void update(Developer developer) {
        String sql = String.format("UPDATE %s SET name = ?, age = ?, sex = ?, phone_number = ?, salary = ? WHERE id = ?", getTableName());
        sqlHelper.update(sql, preparedStatement -> {
            preparedStatement.setString(1, developer.getName());
            preparedStatement.setInt(2, developer.getAge());
            preparedStatement.setString(3, developer.getSex());
            preparedStatement.setString(4, developer.getPhone_number());
            preparedStatement.setInt(5, developer.getSalary());
            preparedStatement.setInt(6, developer.getId());
        });
        System.out.println("DEVELOPER WAS UPDATE");
    }
}
