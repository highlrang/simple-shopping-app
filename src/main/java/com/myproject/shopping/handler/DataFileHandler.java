package com.myproject.shopping.handler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/** import.sql에 INSERT 쿼리를 생성할 때 사용하는 클래스 */

public class DataFileHandler {

    private static final String path = "../shopping/src/main/resources/";
    private static final List<String> queries = Arrays.asList(
                    "INSERT INTO item(id, name, price, stock) VALUES(533233, '반팔티', 18000, 1000)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(511245, '린넨반바지', 25000, 700)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(701214, '머그컵 세트', 35000, 80)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(611113, '이어폰', 53900, 250)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(733712, '문구 세트', 15000, 1000)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(521123, '원피스', 59900, 300)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(533303, '세미정장자켓', 50000, 300)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(572526, '셔츠(흰색, 검은색, 연하늘색)', 2800, 85)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(666123, '아이패드 5세대 Pro', 1500000, 100)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(635248, '아이패드 mini', 1195000, 100)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(536985, '반팔니트티', 49900, 800)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(547278, '가디건', 63000, 60)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(735289, '미니 조명', 21000, 100)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(557111, '캡모자', 23000, 100)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(754211, '미니 포켓 열쇠고리', 7800, 100)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(700012, '캘린더/다이어리', 9900, 500)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(712119, '캐릭터 파일(A4 사이즈)', 5600, 300)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(756631, '핸드폰 케이스 + 그립톡', 19000, 100)\n" +
                    "INSERT INTO item(id, name, price, stock) VALUES(592121, '와이드 슬랙스', 67000, 500)\n"
    );

    public List<String> getInsertQuery() {
        List<String> insertQueries = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path + "items.csv"), StandardCharsets.UTF_8));

            while (reader.read() != -1) {
                String line = reader.readLine();
                String[] lineSplit = line.split(",(?! )");
                insertQueries.add(
                        String.format(
                                "INSERT INTO item(id, name, price, stock) VALUES(%s, '%s', %s, %s)",
                                (Object[]) lineSplit
                        )
                );
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("ITEM INSERT QUERY 생성 실패");
            insertQueries = queries;
        }

        return insertQueries;
    }

    public void writeInsertQuery(List<String> insertQueries){
        try{
            FileWriter fw = new FileWriter(path + "import.sql");

            insertQueries.forEach(query -> {
                try {
                    fw.write(query + "\n");
                } catch (IOException e) {
                    printWriteException();
                }
            });

            fw.flush();
            fw.close();

        } catch (IOException e) {
            printWriteException();
        }
    }

    private void printWriteException() {
        System.out.println(
                "import.sql에 상품 데이터 insert문 작성이 실패하였습니다. " +
                "\n 초기 상품 데이터 입력이 진행되지 않을 수 있으니, import.sql에 하단의 insert 쿼리를 입력해주세요."
        );
        queries.forEach(System.out::println);
    }
}
