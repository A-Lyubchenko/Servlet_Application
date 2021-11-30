package ua.lyubchenko.repositories.entityRepositories;

import lombok.SneakyThrows;
import ua.lyubchenko.connection.ISqlHelper;
import ua.lyubchenko.connection.SqlHelper;
import ua.lyubchenko.domains.Skill;
import ua.lyubchenko.repositories.EntityRepository;

import java.sql.ResultSet;

public class SkillRepository extends EntityRepository<Skill> {
    private final ISqlHelper sqlHelper = new SqlHelper();

    @Override
    protected String getTableName() {
        return "skills";
    }

    @SneakyThrows
    @Override
    protected Skill getEntity(ResultSet resultSet) {
        Skill skill = new Skill();
        skill.setId(resultSet.getInt("id"));
        skill.setDepartment(resultSet.getString("department"));
        skill.setLevel(resultSet.getString("level"));
        return skill;
    }

    @Override
    public void create(Skill skill) {
        String sql = String.format("INSERT INTO %s (id, department, level) VALUES(?,?,?)", getTableName());
        sqlHelper.update(sql, preparedStatement -> {
            preparedStatement.setInt(1, skill.getId());
            preparedStatement.setString(2, skill.getDepartment());
            preparedStatement.setString(3, skill.getLevel());
        });
        System.out.println("SKILL WAS CREATOR");


    }

    @Override
    public void update(Skill skill) {
        String sql = String.format("UPDATE %s SET department = ?, level = ? WHERE id = ?", getTableName());
        sqlHelper.update(sql, preparedStatement -> {
            preparedStatement.setString(1, skill.getDepartment());
            preparedStatement.setString(2, skill.getLevel());
            preparedStatement.setInt(3, skill.getId());
        });
        System.out.println("SKILL WAS UPDATE");
    }
}
