package ua.lyubchenko.connection;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class DataSourceHolder {

    private static DataSourceHolder value;

    private final DataSource dataSource;

    private DataSourceHolder() {
        Properties props = ApplicationConnection.getInstance();
        PGSimpleDataSource dataSource = initPg(props);
        if ("postgres".equals(props.getProperty("db.type"))) {
            initPg(props);
        }
        this.dataSource = dataSource;
    }

    private PGSimpleDataSource initPg(Properties props) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(props.getProperty("URL"));
        dataSource.setUser(props.getProperty("NAME"));
        dataSource.setPassword(props.getProperty("PASSWORD"));
        return dataSource;
    }


    public static DataSource getDataSource() {
        if (value == null) {
            value = new DataSourceHolder();
        }
        return value.dataSource;
    }
}