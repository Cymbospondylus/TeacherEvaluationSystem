package site.bzyl.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import site.bzyl.eneity.UserDO;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class UserMapperTest {
    @Resource
    private UserMapper userMapper;
    @Test
    public void testListUsers() {
        List<UserDO> userDOList = userMapper.selectList(null);
        System.out.println(userDOList);
    }
}
