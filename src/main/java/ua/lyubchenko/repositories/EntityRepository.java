package ua.lyubchenko.repositories;

import lombok.SneakyThrows;
import lombok.val;
import ua.lyubchenko.connection.ISqlHelper;
import ua.lyubchenko.connection.SqlHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public abstract class EntityRepository<T extends Identity> implements ICrud<T>, IRepositories<T> {
    private final ISqlHelper sqlHelper = new SqlHelper();

    protected abstract String getTableName();

    protected abstract T getEntity(ResultSet resultSet);

    @SneakyThrows
    private Integer getSalary(ResultSet resultSet) {
        return resultSet.getInt("sum");
    }

    @SneakyThrows
    private String getSkillName(ResultSet resultSet) {
        return resultSet.getString("name");
    }


    @SneakyThrows
    private String getString(ResultSet resultSet) {
        return resultSet.getString("deadline") +
                " " +
                resultSet.getString("name") +
                " " +
                resultSet.getString("count");
    }

    @SneakyThrows
    private Integer setNameOfMaxId(ResultSet resultSet) {
        return resultSet.getInt("max");
    }

    @Override
    public Integer getMaxId() {
        AtomicReference<Integer> maxId = new AtomicReference<>(0);
        String sql = String.format("SELECT MAX(id) FROM %s", getTableName());
        sqlHelper.query(sql, preparedStatement -> {
            val resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                maxId.set(setNameOfMaxId(resultSet));
            }
            System.out.println("MAX ID WAS COMPLETED");
        });
        return maxId.get();
    }

    @Override
    public List<String> listOfCountDevelopersWorkingOnProjects() {
        List<String> developersList = new ArrayList<>();
        String sql = "SELECT projects.deadline, projects.name, COUNT(developers.name)\n" +
                "FROM developer_project\n" +
                "INNER JOIN projects ON developer_project.project_id = projects.id\n" +
                "INNER JOIN developers ON developer_project.developer_id = developers.id\n" +
                "GROUP BY projects.deadline, projects.name;";
        sqlHelper.query(sql, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                developersList.add(getString(resultSet));
            }
            System.out.println("LIST OF COUNT DEVELOPERS WORKING BY PROJECT WAS COMPLETED");
            System.out.println("FORMAT IS (start project - name project - count developers)");
        });
        return developersList;
    }

    @Override
    public List<String> getMiddleDevelopers() {
        List<String> developersList = new ArrayList<>();
        String sql = "SELECT developers.name\n" +
                "FROM developer_skill\n" +
                "INNER JOIN developers ON developer_skill.developer_id = developers.id\n" +
                "INNER JOIN skills ON developer_skill.skill_id = skills.id\n" +
                "WHERE skills.level = 'Middle'\n" +
                "GROUP BY developers.name";
        sqlHelper.query(sql, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                developersList.add(getSkillName(resultSet));
            }
        });
        System.out.println("LIST OF \"MIDDLE\" DEVELOPERS WAS COMPLETED");
        return developersList;
    }

    @Override
    public List<String> getJavaDevelopers() {
        List<String> developersList = new ArrayList<>();
        String sql = "SELECT developers.name\n" +
                "FROM developer_skill\n" +
                "INNER JOIN developers ON developer_skill.developer_id = developers.id\n" +
                "INNER JOIN skills ON developer_skill.skill_id = skills.id\n" +
                "WHERE skills.department = 'Java'\n" +
                "GROUP BY developers.name;";
        sqlHelper.query(sql, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                developersList.add(getSkillName(resultSet));
            }
        });
        System.out.println("LIST OF DEVELOPERS WORKING ON \"JAVA\" WAS COMPLETED");
        return developersList;
    }

    @Override
    public List<String> getDevelopersOfProject(T entity) {
        List<String> developers = new ArrayList<>();
        String sql = "SELECT developers.name\n" +
                "FROM developer_project\n" +
                "INNER JOIN developers ON developer_project.developer_id = developers.id\n" +
                "INNER JOIN projects ON developer_project.project_id = projects.id\n" +
                "WHERE projects.name = ?\n" +
                "GROUP BY developers.name";
        sqlHelper.query(sql, preparedStatement -> {
            preparedStatement.setString(1, entity.getName());
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                developers.add(getSkillName(resultSet));
            }
        });
        System.out.println("DEVELOPERS OF PROJECT " + entity.getName() + " WAS CREATOR");
        return developers;
    }

    @Override
    public Integer salaryDevelopers(T entity) {
        AtomicReference<Integer> sum = new AtomicReference<>(0);
        String sql = "SELECT SUM(salary)\n" +
                "FROM developer_project\n" +
                "INNER JOIN projects ON projects.id = developer_project.project_id\n" +
                "INNER JOIN developers ON developers.id = developer_project.developer_id\n" +
                "WHERE projects.name = ?\n" +
                "GROUP BY projects.name";
        sqlHelper.query(sql, preparedStatement -> {
            preparedStatement.setString(1, entity.getName());
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sum.set(getSalary(resultSet));
            }
        });
        System.out.println("SALARY OF DEVELOPERS PROJECT BY " + entity.getName() + " WAS CREATOR");
        return sum.get();
    }


    @Override
    public List<T> read() {
        List<T> listOfEntity = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s ORDER BY id", getTableName());
        sqlHelper.query(sql, preparedStatement -> {
            val resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listOfEntity.add(getEntity(resultSet));
            }
        });
        System.out.println("READ WAS COMPLETED");
        return listOfEntity;
    }

    @Override
    public void delete(int id) {
        String sql = String.format("DELETE FROM %s WHERE id = ?", getTableName());
        sqlHelper.update(sql, preparedStatement -> {
            preparedStatement.setInt(1, id);
        });
        System.out.println("DELETE WAS COMPLETED");
    }
}
