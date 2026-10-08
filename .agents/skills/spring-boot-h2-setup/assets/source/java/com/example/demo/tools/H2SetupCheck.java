package com.example.demo.tools;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;
import org.h2.tools.RunScript;

/** 실제 앱 DB와 분리한 메모리 DB에서 공지사항 SQL을 검증한다. */
public final class H2SetupCheck {
    private H2SetupCheck() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("schema.sql과 data.sql 경로를 지정하세요.");
        }
        String url = "jdbc:h2:mem:skill_" + UUID.randomUUID();
        try (Connection connection = DriverManager.getConnection(url, "sa", "")) {
            for (String file : args) {
                try (Reader reader = Files.newBufferedReader(Path.of(file), StandardCharsets.UTF_8)) {
                    RunScript.execute(connection, reader);
                }
            }
            long initialRows = count(connection);
            if (initialRows != 3) {
                throw new IllegalStateException("초기 공지사항 3건을 확인할 수 없습니다.");
            }
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("INSERT INTO notice(title, content) VALUES ('검증', '커밋')",
                        Statement.RETURN_GENERATED_KEYS);
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (!keys.next() || keys.getLong(1) <= 0) {
                        throw new IllegalStateException("생성 키를 확인할 수 없습니다.");
                    }
                }
            }
            connection.commit();
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("INSERT INTO notice(title, content) VALUES ('검증', '롤백')");
            }
            connection.rollback();
            // 별도 연결로 커밋 결과와 롤백 후 행 수를 확인한다.
            try (Connection verification = DriverManager.getConnection(url, "sa", "")) {
                if (count(verification) != initialRows + 1) {
                    throw new IllegalStateException("커밋 또는 롤백 결과가 다릅니다.");
                }
            }
        }
        System.out.println("\u001B[32m[INFO] H2SetupCheck.main(): SQL·생성 키·커밋·롤백 검증 성공\u001B[0m");
    }

    private static long count(Connection connection) throws Exception {
        try (Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM notice")) {
            result.next();
            return result.getLong(1);
        }
    }
}
