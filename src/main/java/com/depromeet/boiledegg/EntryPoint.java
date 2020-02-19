package com.depromeet.boiledegg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EntryPoint {

    // TODO 빌린 것에 대한 상태 추가
    // TODO 반납일자 다가오면 알람

    public static void main(final String... args) {
        SpringApplication.run(EntryPoint.class, args);
    }
}
