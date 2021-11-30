package ua.lyubchenko.connection;



public interface ISqlHelper {

    int update(String sql, SettPrepare settPrepare);

    void query(String sql, SettPrepare settPrepare);
}
