package com.backend.backend.user;

import com.backend.backend.model.ProductEntity;
import com.backend.backend.repository.ProductRepository;
import com.backend.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findUserByEmailTest(){
        assertNotNull(userRepository.findUserByEmail("user1@yahoo.com").get());
        assertEquals("firstName1", userRepository.findUserByEmail("user1@yahoo.com").get().getFirstName());
    }
}
