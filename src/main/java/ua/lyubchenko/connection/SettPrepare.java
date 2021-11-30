package ua.lyubchenko.connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SettPrepare {

    void set(PreparedStatement preparedStatement) throws SQLException;
}
