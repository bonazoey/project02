package com.example.demo.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** 환경 변수로 지정된 Oracle에 읽기 전용 확인 쿼리를 실행한다. */
public final class OracleConnectionCheck {
    private OracleConnectionCheck() {
    }

    public static void main(String[] args) {
        try {
            String url = requiredEnvironment("ORACLE_JDBC_URL");
            String username = requiredEnvironment("ORACLE_USERNAME");
            String password = requiredEnvironment("ORACLE_PASSWORD");
            if (!url.startsWith("jdbc:oracle:thin:@")) {
                throw new IllegalArgumentException("Oracle Thin URL 형식을 확인하세요.");
            }
            // 진단이 장시간 대기하지 않도록 로그인과 쿼리 제한 시간을 둔다.
            DriverManager.setLoginTimeout(10);
            try (Connection connection = DriverManager.getConnection(url, username, password);
                    Statement statement = connection.createStatement()) {
                statement.setQueryTimeout(10);
                try (ResultSet result = statement.executeQuery("SELECT 1 FROM dual")) {
                    if (!result.next() || result.getInt(1) != 1) {
                        throw new SQLException("연결 확인 결과가 예상과 다릅니다.");
                    }
                }
            }
            log("INFO", "Oracle 연결과 조회에 성공했습니다.");
        } catch (IllegalArgumentException exception) {
            log("ERROR", exception.getMessage());
            System.exit(1);
        } catch (SQLException exception) {
            // 상세 JDBC 메시지에 접속 정보가 포함될 수 있어 코드만 출력한다.
            log("ERROR", "Oracle 연결 또는 조회 실패: SQLState="
                    + exception.getSQLState() + ", vendorCode=" + exception.getErrorCode());
            System.exit(1);
        }
    }

    private static String requiredEnvironment(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("필수 환경 변수 누락: " + name);
        }
        return value;
    }

    private static void log(String level, String message) {
        String color = "ERROR".equals(level) ? "\u001B[31m" : "\u001B[32m";
        System.out.println(color + "[" + level
                + "] OracleConnectionCheck.main(): " + message + "\u001B[0m");
    }
}
