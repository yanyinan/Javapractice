package com.siyi.accesskey.controller;

import com.siyi.accesskey.constant.Constant;
import com.siyi.accesskey.constant.ErrorCode;
import com.siyi.accesskey.ex.AuthenticationException;
import com.siyi.accesskey.model.domain.LoginUser;
import com.siyi.accesskey.model.valid.EmailGroup;
import com.siyi.accesskey.model.valid.PhoneGroup;
import com.siyi.accesskey.service.SmsService;
import com.siyi.accesskey.service.UserService;
import com.siyi.accesskey.utils.EmailSenderUtil;
import com.siyi.accesskey.utils.Resp;
import com.siyi.accesskey.utils.VerificationCodeByPhone;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.siyi.accesskey.constant.Constant.SMS_REPLACE;
import static com.siyi.accesskey.constant.Constant.SMS_TEMPLATE;

/**

 * @author 26481
 * @CreateTime: 2023-11-17  20:16
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
public class GetServiceController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSenderUtil emailSenderUtil;
    /**
     * 获取验证码
     *
     * @param loginUser     只验证手机号
     * @param bindingResult 错误信息
     * @return Resp 成功返回操作结果
     */
    @PostMapping("/getVerificationCode")
    public Resp getVerificationCode(
            @Validated({PhoneGroup.class}) @RequestBody LoginUser loginUser
            , BindingResult bindingResult) throws AuthenticationException {
        if (bindingResult.hasErrors()) {
            return Resp.getErrorMap(bindingResult);
        }
        // 校验手机号是否已经存在重复
        if (userService.phoneExist(loginUser)!=null) {
            throw new AuthenticationException(ErrorCode.PHONE_EXIST);
        }
        // 校验通过,调用API发送短信
        try {
            // 生成验证码
            String code = VerificationCodeByPhone.generateVerificationCode();
            // 发送短信
            smsService.sendSms(loginUser.getPhone(), code);
            // 将验证码存入redis,有效时间60s
            redisTemplate.opsForValue().set(loginUser.getPhone(), code, 60, java.util.concurrent.TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new AuthenticationException(ErrorCode.EMAIL_SEND_FAIL);
        }
        return Resp.ok("验证码已发送");
    }
    /**
     * 发送邮箱验证链接(注册)
     *
     * @param loginUser     只验证邮箱格式
     * @param bindingResult 错误信息
     * @return Resp 成功返回操作结果
     */
    @PostMapping("/getRegisterUrl")
    public Resp getRegisterUrl(@Validated(EmailGroup.class) @RequestBody LoginUser loginUser
            , BindingResult bindingResult) throws AuthenticationException {
        if (bindingResult.hasErrors()) {
            return Resp.getErrorMap(bindingResult);
        }
        // 校验邮箱是否存在
        if (userService.emailExist(loginUser)!=null) {
            throw new AuthenticationException(ErrorCode.EMAIL_EXIST);
        }
        //不存在重复,key为邮箱value
        String activeCode = emailSenderUtil.generateSib(loginUser.getEmail());
        try {
            emailSenderUtil.sendActivationEmail(loginUser.getEmail(), activeCode);
        } catch (MessagingException e) {
            throw new AuthenticationException(ErrorCode.EMAIL_SEND_FAIL);
        }

//         将邮箱存入redis,有效时间300s
        redisTemplate.opsForValue().set(activeCode, "0", 300, TimeUnit.SECONDS);
        return Resp.ok("邮件发送成功!");
    }

    /**
     * 发送邮箱验证码(找回密码)以及登录
     *
     * @param loginUser     只验证邮箱格式
     * @param bindingResult 错误信息
     * @return Resp 成功返回操作结果
     */
    @PostMapping("/getEmailCode")
    public Resp getEmailCode(@Validated(EmailGroup.class) @RequestBody LoginUser loginUser
            , BindingResult bindingResult) throws AuthenticationException {
        if (bindingResult.hasErrors()) {
            return Resp.getErrorMap(bindingResult);
        }
        // 校验邮箱是否存在
        if (userService.emailExist(loginUser )==null) {
            throw new AuthenticationException(ErrorCode.EMAIL_NOT_EXIST);
        }
        //存在
        String code = VerificationCodeByPhone.generateVerificationCode();
        try {
            emailSenderUtil.sendVerificationCodeEmail(loginUser.getEmail(), code);
        } catch (MessagingException e) {
            throw new AuthenticationException(ErrorCode.EMAIL_SEND_FAIL);
        }
        // 将验证码存入redis,有效时间60s
        redisTemplate.opsForValue().set(loginUser.getEmail(), code, 300, TimeUnit.SECONDS);
        return Resp.ok("邮件发送成功!");
    }
    /**
     * 发送手机验证码(找回密码)以及登录
     *
     * @param loginUser     只验证邮箱格式
     * @param bindingResult 错误信息
     * @return Resp 成功返回操作结果
     */
    @PostMapping("/getPhoneCode")
    public Resp getPhoneCode(@Validated(PhoneGroup.class) @RequestBody LoginUser loginUser
            , BindingResult bindingResult) throws AuthenticationException {
        if (bindingResult.hasErrors()) {
            return Resp.getErrorMap(bindingResult);
        }
        // 校验手机号是否存在
        if (userService.phoneExist(loginUser)==null) {
            throw new AuthenticationException(ErrorCode.PHONE_NOT_EXIST);
        }
        //不存在重复,key为手机号value
        String code = VerificationCodeByPhone.generateVerificationCode();
        try {
            smsService.sendSms(loginUser.getPhone(), code);
        } catch (Exception e) {
            throw new AuthenticationException(ErrorCode.EMAIL_SEND_FAIL);
        }
        // 将验证码存入redis,有效时间60s
        redisTemplate.opsForValue().set(loginUser.getPhone(), code, 60, TimeUnit.SECONDS);
        return Resp.ok("短信发送成功!");
    }
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        // 生成的验证码
        String text = specCaptcha.text();
        // 获取当前请求的 HttpSession
        HttpSession session = request.getSession();
        // 获取 sessionId 作为关联的 key

        String sessionId = session.getId();
        //存入验证码,1分钟有效
        redisTemplate.opsForValue().set(sessionId, text, 60, TimeUnit.SECONDS);
        request.getSession().setAttribute(Constant.CAPTCHA, text);

        specCaptcha.out(response.getOutputStream());

    }
}
