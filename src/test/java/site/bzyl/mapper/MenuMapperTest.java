package site.bzyl.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class MenuMapperTest {
    @Resource
    private MenuMapper menuMapper;

    @Test
    public void testSelectPermsByUserId() {
        List<String> strings = menuMapper.selectPermsByUserId(1L);
        System.out.println(strings);
    }
}
