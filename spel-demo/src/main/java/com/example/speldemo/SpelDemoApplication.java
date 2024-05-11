package com.example.speldemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@SpringBootApplication
public class SpelDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpelDemoApplication.class, args);
    }

    @Value("#{1 + 1}")
    private int additionResult;



    @Bean
    public String greet() {
        // 使用 SpEL 创建一个简单的字符串
        ExpressionParser parser = new SpelExpressionParser();
        String message = parser.parseExpression("'Hello, ' + 'World!'").getValue(String.class);
        System.out.println(message);
        return message;
    }

    @Bean
    public int multiply() {
        // 使用 SpEL 计算乘法结果
        ExpressionParser parser = new SpelExpressionParser();
        int result = parser.parseExpression("2 * 3").getValue(Integer.class);
        System.out.println(result);
        return result;
    }

    @Bean
    public int getResult() {
        // 使用 SpEL 访问属性值
        System.out.println(additionResult);
        return additionResult;
    }

}
