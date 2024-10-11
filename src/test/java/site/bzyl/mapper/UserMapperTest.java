package site.bzyl.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import site.bzyl.dto.req.PageUserReqDTO;
import site.bzyl.entity.UserDO;

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

    @Test
    public void testPageUserByRoleId() {
        PageUserReqDTO request = new PageUserReqDTO();
        request.setRoleId(1L);
        List<UserDO> userDOS = userMapper.selectPageByRoleId(request);
        System.out.println(userDOS);
    }
}
