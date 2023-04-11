package com.System.PharmacyManagement.Database;

import com.System.PharmacyManagement.Model.Login;
import com.System.PharmacyManagement.Repository.LoginRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Data
@Configuration
public class A_LoginDatabase {

    long[] loginIDs = new long[]{1, 2, 3, 4};
    String[] usernames = new String[]{"user1", "user2", "user3", "user4"};
    String[] passwords = new String[]{"password1", "password2", "password3", "password4"};
    Login[] loginList = new Login[4];

    @Bean
    @Primary
    CommandLineRunner initLoginDatabase(LoginRepository loginRepository) {
        return new CommandLineRunner() {

            //Logger
            private final Logger logger = LoggerFactory.getLogger(A_LoginDatabase.class);

            @Override
            public void run(String... args) throws Exception {
                for (int i = 0; i < 4; i++) {
                    loginList[i] = new Login(loginIDs[i], usernames[i], passwords[i]);
                }
                for (int i = 0; i < 4; i++) {
                    logger.info("insert data: " + loginRepository.save(loginList[i]));
                }
            }
        };
    }

    public Login getLogin(int i) {
        Login login = new Login(loginIDs[i - 1], usernames[i - 1], passwords[i - 1]);
        return login;
    }
}
