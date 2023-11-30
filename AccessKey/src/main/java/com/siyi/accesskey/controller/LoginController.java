package com.siyi.accesskey.controller;
import com.siyi.accesskey.constant.Constant;
import com.siyi.accesskey.constant.ErrorCode;
import com.siyi.accesskey.ex.AuthenticationException;
import com.siyi.accesskey.model.domain.LoginUser;
import com.siyi.accesskey.model.domain.LoginUserCodeVO;
import com.siyi.accesskey.model.valid.EmailGroup;
import com.siyi.accesskey.model.valid.PhoneGroup;
import com.siyi.accesskey.model.valid.UserRegisterGroup;
import com.siyi.accesskey.service.SmsService;
import com.siyi.accesskey.service.UserService;
import com.siyi.accesskey.service.imp.UserBlacklistService;
import com.siyi.accesskey.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Map;

import static com.siyi.accesskey.constant.Constant.*;


/**
 * @author 26481
 * @CreateTime: 2023-11-09  22:34
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserBlacklistService userBlacklistService;
    @Autowired
    private IpUtil ipUtil;

    @GetMapping()
    public ModelAndView loginPage(HttpServletRequest request) {
        return new ModelAndView("login/login");
    }

    @GetMapping("/tem")
    public ModelAndView tem(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login/tem");
        Object attribute = request.getSession().getAttribute(SHOW_CAPTCHA);
        modelAndView.addObject("user", (boolean) attribute);
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(@CookieValue(value = Constant.TOKEN_KEY) String token, HttpServletResponse response, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        // 清除 redis 值
        redisTemplate.delete(token);
        // 清除登录标识
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        // 清除记住我标识, 删除 cookie
        Cookie cookie = new Cookie(Constant.REMEMBER_ME, "");
        cookie.setPath("/login");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

    @PostMapping("/username")
    public Resp loginByUsername(@Validated(UserRegisterGroup.class) @RequestBody LoginUserCodeVO loginUserCodeVO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse resp) throws AuthenticationException {
        if (bindingResult.hasErrors()) {
            return Resp.getErrorMap(bindingResult);
        }
        //判断是否需要验证码
        boolean showCaptcha = getShowCaptchaFromSession(request);
        if (showCaptcha) {
            checkCaptcha(loginUserCodeVO.getCaptcha(), request);
        }
        //设置账号
        boolean rememberMe = loginUserCodeVO.isRememberMe();
        String username = loginUserCodeVO.getUsername();
        String password = loginUserCodeVO.getPassword();
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(username);
        loginUser.setPassword(password);
        // 登录
        LoginUser user = userService.login(loginUser);
        if (user == null) {
            //登录失败，将ip记录次数
            String ip = ipUtil.getIpAddr(request);
            userService.handleLoginAttempt(ip);
            throw new AuthenticationException(ErrorCode.USERNAME_PASSWORD_ERROR);
        }
        //检查是否记住密码,如果记住密码则将cookie存入
        if (rememberMe) {
            checkRememberMe(loginUser, resp);
        }
        // 将用户信息存入session
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        //返回是否异地登录
        if (!checkCity(request,user)){
            return Resp.ok("检测到您本次登录与上次地址不同!","/index");
        }
        //返回需要重定向的地址
        return Resp.ok("登录成功!", "/index");
    }


    /**
     * 手机号登录
     *
     * @param loginUserCodeVO 用户手机号
     * @return 登录成功返回用户信息，否则返回null
     */
    @PostMapping("/phone")
    public Resp loginByPhone(@Validated(PhoneGroup.class) @RequestBody LoginUserCodeVO loginUserCodeVO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException {
        if (bindingResult.hasErrors()) {
            return Resp.getErrorMap(bindingResult);
        }
        boolean showCaptcha = getShowCaptchaFromSession(request);
        if (showCaptcha) {
            checkCaptcha(loginUserCodeVO.getCaptcha(), request);
        }
        String phone = loginUserCodeVO.getPhone();
        String code = loginUserCodeVO.getCode();
        LoginUser user = new LoginUser();
        user.setPhone(phone);
        // 查询信息
        LoginUser result = userService.phoneStatus(user);
        //检查短信验证码是否正确
        String s = redisTemplate.opsForValue().get(user.getPhone());
        if (!code.equals(s)) {
            //登录失败，记录登录失败次数
            userService.handleLoginAttempt(ipUtil.getIpAddr(request));
            throw new AuthenticationException(ErrorCode.CAPTCHA_ERROR);
        }
        boolean rememberMe = loginUserCodeVO.isRememberMe();
        if (rememberMe) {
            checkRememberMe(user, httpServletResponse);
        }
        //登录成功，将用户信息存入session
        request.getSession().setAttribute(USER_LOGIN_STATE, result);
        //判断本次登录所在城市是否与上次登录城市相同
        if (!checkCity(request,result)){
            return Resp.ok("检测到您本次登录与上次地址不同!","/index");
        }
        //返回需要重定向的地址
        return Resp.ok("登录成功!", "/index");
    }

    @PostMapping("/email")
    public Resp loginByEmail(@Validated(EmailGroup.class) @RequestBody LoginUserCodeVO loginUserCodeVO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException {
        if (bindingResult.hasErrors()) {
            return Resp.getErrorMap(bindingResult);
        }
        //判断是否需要验证码
        boolean showCaptcha = getShowCaptchaFromSession(request);
        if (showCaptcha) {
            checkCaptcha(loginUserCodeVO.getCaptcha(), request);
        }
        String email = loginUserCodeVO.getEmail();
        String code = loginUserCodeVO.getCode();
        LoginUser user = new LoginUser();
        user.setEmail(email);

        // 查询信息
        LoginUser result = userService.emailStatus(user);
        //检查邮箱验证码是否正确
        String s = redisTemplate.opsForValue().get(user.getEmail());
        if (!code.equals(s)) {
            throw new AuthenticationException(ErrorCode.CAPTCHA_ERROR);
        }

        boolean rememberMe = loginUserCodeVO.isRememberMe();
        if (rememberMe) {
            checkRememberMe(user, httpServletResponse);
        }
        //登录成功，将用户信息存入session
        request.getSession().setAttribute(USER_LOGIN_STATE, result);
        //返回是否异地登录
        if (!checkCity(request,result)){
            return Resp.ok("检测到您本次登录与上次地址不同!","/index");
        }
        //返回需要重定向的地址
        return Resp.ok("登录成功!", "/index");
    }

    /**
     * 是否需要验证码
     */
    private boolean getShowCaptchaFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (boolean) session.getAttribute(SHOW_CAPTCHA);
    }

    /**
     * 验证码校验
     */
    private void checkCaptcha(String captcha, HttpServletRequest request) throws AuthenticationException {
        //获取session中的验证码
        String sessionId = request.getSession().getId();
        String redisCaptcha = redisTemplate.opsForValue().get(sessionId);
        //判断验证码是否正确
        if (!captcha.equalsIgnoreCase(redisCaptcha)) {
            //验证码错误，记录登录失败次数
            userService.handleLoginAttempt(ipUtil.getIpAddr(request));
            throw new AuthenticationException(ErrorCode.CAPTCHA_ERROR);
        }
        //验证码正确，删除redis中的验证码
        redisTemplate.delete(sessionId);
    }

    /**
     * 检查是否记住密码
     */
    private void checkRememberMe(LoginUser loginUser, HttpServletResponse response) {
        //判断是哪种登录方式
        String cookieValue = null;
        if (loginUser.getUsername() != null) {
            //用户名登录加密
            try {
                cookieValue = AesUtil.AES_CBC_Encrypt_String(loginUser.getUsername());
            } catch (Exception e) {
                log.error("加密失败");
            }
        } else if (loginUser.getPhone() != null) {
            //手机号登录加密
            try {
                cookieValue = AesUtil.AES_CBC_Encrypt_String(loginUser.getPhone());
            } catch (Exception e) {
                log.error("加密失败");
            }

        } else if (loginUser.getEmail() != null) {
            //邮箱登录加密
            try {
                cookieValue = AesUtil.AES_CBC_Encrypt_String(loginUser.getEmail());
            } catch (Exception e) {
                log.error("加密失败");
            }
        }
        //将cookie存入response
        Cookie cookie = new Cookie(REMEMBER_ME, cookieValue);
        //设置cookie的有效期为7天
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setPath("/login");
        response.addCookie(cookie);
    }

    /**
     * 发送短信验证码
     *
     * @param user 请求与用户查询的信息
     * @return 是否与上次登陆相同
     */
    private boolean checkCity(HttpServletRequest request, LoginUser user) {
        //获取ip地址
        String newIpAddr = ipUtil.getIpAddr(request);
        Map<String, Object> ipRegion = IpAddr.getIpRegion(newIpAddr);
        String city = (String) ipRegion.get("城市");
        //查看用户上一次的登录信息
        String oldIpAddress = user.getIpAddress();
        Map<String, Object> OldIpRegion = IpAddr.getIpRegion(oldIpAddress);
        return newIpAddr.equals(oldIpAddress);
    }
}
