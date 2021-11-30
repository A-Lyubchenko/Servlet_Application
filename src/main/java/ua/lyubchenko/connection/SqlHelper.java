package ua.lyubchenko.connection;

import lombok.SneakyThrows;


import java.sql.Connection;

import java.sql.PreparedStatement;


public class SqlHelper implements ISqlHelper {
    @SneakyThrows
    @Override
    public int update(String sql, SettPrepare settPrepare) {
        try (Connection connection = DataSourceHolder.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            settPrepare.set(preparedStatement);
            return preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    @Override
    public void query(String sql, SettPrepare settPrepare) {
        try (Connection connection = DataSourceHolder.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            settPrepare.set(preparedStatement);
            preparedStatement.executeQuery();
        }
    }
}
