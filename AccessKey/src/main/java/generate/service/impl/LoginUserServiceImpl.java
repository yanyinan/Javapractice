package generate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generate.domain.LoginUser;
import generate.service.LoginUserService;
import generate.mapper.LoginUserMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【login_user】的数据库操作Service实现
* @createDate 2023-11-24 23:03:21
*/
@Service
public class LoginUserServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser>
    implements LoginUserService{

}




