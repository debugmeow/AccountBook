package main.util;
/*
* MariaDB 데이터베이스 연결을 관리하는 클래스
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    // DB 접속 정보 (자신의 환경에 맞게 수정)
    private static final String URL = "jdbc:mariadb://localhost:3306/account_book";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    /*
    * DB 연결을 반환하는 메서드
    * @return Connection 객체
    * @throws SQLException DB 연결 실패 시 예외 발생
    */

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
