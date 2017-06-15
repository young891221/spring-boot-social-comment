package com.social;

import com.social.domain.User;
import com.social.domain.enums.SocialType;
import com.social.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by KimYJ on 2017-05-31.
 */
@RunWith(SpringRunner.class)
//@DataJpaTest(useDefaultFilters = false, excludeFilters = @ComponentScan.Filter(RedisHttpSessionConfiguration.class))
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(classes = SocialApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserJpaTest {
    private static final String TEST_PRINCIPAL = "1355321987876904";

    @Autowired
    private UserRepository userRepository;

    @Before
    public void init() {
        User user = User.builder()
                            .userPrincipal(TEST_PRINCIPAL)
                            .userName("test")
                            .userEmail("email")
                            .userImage(null)
                            .socialType(SocialType.FACEBOOK)
                            .build();
        userRepository.save(user);
    }

    @Test
    public void 기존에_가입했는지_조회_쿼리() {
        User user = userRepository.findByUserPrincipalIs(TEST_PRINCIPAL);
        assertThat(user.getUserPrincipal(), is(TEST_PRINCIPAL));
    }
}
