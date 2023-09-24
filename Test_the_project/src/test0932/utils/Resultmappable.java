package test0932.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 适配器 —— 结果映射
 */
@FunctionalInterface
public interface Resultmappable<T> {
    T mapper(ResultSet set) throws SQLException;
}
