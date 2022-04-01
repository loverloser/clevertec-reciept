package ru.clevertec;

import lombok.Cleanup;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.clevertec.config.ApplicationConfig;

public class ApplicationRunner {
    public static void main(String[] args) {
        @Cleanup AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);

        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }

}
