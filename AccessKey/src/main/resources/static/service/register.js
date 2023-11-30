let layer = layui.layer;

layui.use(['form', 'layer'], function () {
    var form = layui.form;
    var layer = layui.layer;
    // 全局校验状态
    var isValid = true;
    // 用户名重复校验
    var isUsernameValid = false;

    // 为用户名输入框添加失去焦点事件监听
    $('#username-form input:first').focus(); // 将焦点聚焦到用户名输入框
    let captchaImg = document.getElementById("captchaImg");
    captchaImg.onclick = function(){
        // this = captchaImg;
        captchaImg.src = ctx + "captcha?" + new Date().getTime();
    }
    // 使用 jQuery 监听所有表单的共同祖先元素，通过事件委托处理用户名输入框的 blur 事件
    $('.demo-reg-container').on('blur', 'input[lay-filter="username-filter"]', function () {
        // 获取用户名输入框的值
        var usernameValue = $(this).val();
        if (!/^[a-zA-Z]{4,16}$/.test(usernameValue)) {
            return;
        }
        // 进行异步校验
        axios.post(ctx + 'register/checkUsername', {
            username: usernameValue
        })
            .then(function (response) {
                let result = response.data;
                if (result.code === 200) {
                    // 用户名可用
                    isUsernameValid = true;
                } else {
                    // 用户名重复
                    isUsernameValid = false;
                    layer.msg(result.msg || '用户名已存在',
                        {
                            time: 5000, // 设置显示时间为 2000 毫秒
                        });
                }
            })
            .catch(function (error) {
                // 请求失败，不确定用户名是否重复，交给后续验证处理
                isUsernameValid = false;
            });
    });

    //按钮监听 获取手机验证码
    $('#send-registration-phone').on('click', function () {
        // 获取手机号输入框的值
        let phoneValue = $('#reg-cellphone').val();

        // 校验手机号是否合法
        if (!/^1[3456789]\d{9}$/.test(phoneValue)) {
            layer.msg('请输入有效的11位手机号码');
            return;
        }
        let $button = $(this);

        // 禁用按钮，修改样式
        $button.attr('disabled', true).addClass('disabled');
        // 如果手机号合法，发送 POST 请求获取验证码
        axios.post(ctx + 'getVerificationCode', {
            phone: phoneValue
        })

            .then(function (response) {
                let result = response.data;
                // 处理获取验证码成功的逻辑
                if (result.code === 200) {
                    layer.msg(result.data || '验证码已发送');
                    startCountdown($button, 60); // 300 秒倒计时
                } else {
                    layer.msg(result.msg || '获取验证码失败,请重试');
                    $button.removeAttr('disabled').removeClass('disabled');

                }
            })

            .catch(function (error) {
                // 处理获取验证码失败的逻辑
                layer.msg('获取验证码失败');

            })
            .finally(function () {
                // 在请求完成后，无论成功还是失败，都启用按钮并恢复样式
                $button.removeAttr('disabled').removeClass('disabled');
            });

        //按钮倒计时
        function startCountdown($button, seconds) {
            var countdown = seconds;
            var timer = setInterval(function () {
                $button.text(countdown + '秒后重新获取');

                if (countdown <= 0) {
                    // 倒计时结束，恢复按钮状态和样式
                    $button.removeAttr('disabled').removeClass('disabled').text('获取验证码');
                    clearInterval(timer);
                }

                countdown--;
            }, 1000);
        }
    });


// 按钮监听 获取邮箱验证码
    $('#send-registration-email').on('click', function () {

        // 获取邮箱输入框的值
        let emailValue = $('#reg-email').val();

        // 校验邮箱是否合法
        if (!/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/.test(emailValue)) {
            layer.msg('请输入有效的邮箱地址');
            return;
        }

        let $button = $(this);

        // 禁用按钮，修改样式
        $button.attr('disabled', true).addClass('disabled');

        // 如果邮箱合法，发送 POST 请求获取验证码
        axios.post(ctx + 'getRegisterUrl', {
            email: emailValue
        })
            .then(function (response) {
                let result = response.data;
                // 处理获取验证码成功的逻辑
                if (result.code === 200) {
                    layer.msg(result.data || '验证码已发送');
                    startCountdownEmail($button, 300); // 300 秒倒计时
                } else {
                    layer.msg(result.msg || '获取验证码失败，请重试');
                }
            })
            .catch(function (error) {
                // 处理获取验证码失败的逻辑
                layer.msg('获取验证码失败，请重试');
            })
            .finally(function () {
                // 在请求完成后，无论成功还是失败，都启用按钮并恢复样式
                $button.removeAttr('disabled').removeClass('disabled');
            });

        function startCountdownEmail($button, seconds) {
            var countdown = seconds;
            var timer = setInterval(function () {
                $button.text(countdown + '秒后重新获取');

                if (countdown <= 0) {
                    // 倒计时结束，恢复按钮状态和样式
                    $button.removeAttr('disabled').removeClass('disabled').text('获取验证码');
                    clearInterval(timer);
                }

                countdown--;
            }, 1000);
        }
    });

    // 自定义校验规则
    form.verify({

        username: function (value, item) {
            // value：表单的值、item：表单的DOM对象
            if (!/^[\S]{6,16}$/.test(value)) {
                isValid = false;
                return '用户名必须为4到16位的大小写字母';
            }
        },
        password: function (value, item) {
            // 自定义密码规则，这里只是一个示例
            if (!/^[\S]{6,16}$/.test(value)) {
                isValid = false;
                return '密码必须6到16位，包括大小写字母且不能有空格特殊字符';
            }
        },
        confirmPassword: function (value, item) {
            var password = $(item).parents('.layui-form-item').prev().find('input[name=password]').val();
            if (value !== password) {
                isValid = false;
                return '两次输入的密码不一致';
            }
        },
        cellphone: function (value, item) {
            if (!/^1[3456789]\d{9}$/.test(value)) {
                isValid = false;
                return '请输入有效的11位手机号码';
            }
        },
        email: function (value, item) {
            if (!/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/.test(value)) {
                isValid = false;
                return '请输入有效的邮箱地址';
            }
        }
        // 其他自定义规则可以继续添加
    });


    // 切换表单
    $('.icon-toggle').on('click', function () {
        var targetForm = $(this).data('target'); // 获取目标表单的ID
        $('.layui-form').hide(); // 隐藏所有表单
        $('#' + targetForm).show(); // 显示目标表单
        $('#' + targetForm).find('input:first').focus(); // 将焦点聚焦到目标表单的第一个输入框
        form.render(); // 重新渲染表单，使其生效
    });

    // 提取验证逻辑为一个函数
    function validateFormFields(data) {
        isValid = true;
        for (var key in form.verify) {
            if (form.verify.hasOwnProperty(key)) {
                var result = form.verify[key](data.field[key], data.field);
                if (result !== true) {
                    isValid = false;
                    layer.msg(result);
                    break;  // 如果有一个字段验证不通过，就跳出循环
                }
            }
        }
        return isValid;
    }

    // 表单提交
    form.on('submit(demo-reg)', function (data) {
        isValid = validateFormFields(data);
        // 获取当前显示的表单类型
        let currentFormId = $('.layui-form:visible').attr('id');

        if (currentFormId === 'phone-form' || currentFormId === 'email-form') {
            isUsernameValid = true;
        }
        // 检查所有输入框的校验状态
        if (!isUsernameValid || !isValid) {
            layer.msg('请检查您的用户名或个人信息');
            return false;
        }


        // 根据表单类型选择对应的提交请求
        switch (currentFormId) {
            case 'username-form':
                submitUsernameForm(data.field);
                break;
            case 'phone-form':
                submitPhoneForm(data.field);
                break;
            case 'email-form':
                submitEmailForm(data.field);
                break;
            default:
                console.error('未知的表单类型');
        }

        return false; // 阻止表单跳转
    });


    // 提交用户名注册表单的具体实现
    function submitUsernameForm(formData) {
        axios.post(ctx + 'register/username', {
            username: formData.username,
            password: formData.password,
            code: formData.captcha
        })
            .then(function (response) {
                let result = response.data;
                // 处理提交成功的逻辑
                if (result.code === 200) {
                    showSuccessAlert(result);
                } else {
                    // 处理注册失败的逻辑
                    layer.msg(result.msg || '注册失败');
                }
            })
            .catch(function (error) {
                // 处理提交失败的逻辑
                if (error.response) {
                    // 请求已发送，但服务器返回状态码不是 2xx
                    console.error('Error Response:', error.response);

                    // 例如，显示服务器返回的错误信息
                    layer.msg(error.response.data.msg || '注册失败');
                } else if (error.request) {
                    // 请求已发送，但没有收到响应
                    console.error('No response received from the server');
                    layer.msg('请求没有收到响应，请检查网络');
                } else {
                    // 在设置请求时触发错误
                    console.error('Error Config:', error.config);
                    layer.msg('请求发送时发生错误');
                }
            });
    }

    //注册成功后的提示框
    function showSuccessAlert(result) {
        layer.alert(result.data || '注册成功!' + '点击确定前往登录页面', {
            icon: 1, // 1表示成功的图标
            title: '提示',
            closeBtn: 0 // 关闭按钮不显示
        }, function (index) {
            // 用户点击确定后执行的回调函数
            window.location.href = '/login'; // 跳转到登录页面
            layer.close(index); // 关闭提示框
        });
    }

    // 提交手机号注册表单的具体实现
    function submitPhoneForm(formData) {
        // 编写提交手机号注册表单的逻辑
        axios.post(ctx + 'register/phone', {
            phone: formData.cellphone,
            code: formData.vercode,
            password: formData.password,
        })
            .then(function (response) {
                // 检查后端返回的 Resp 对象中的 code 字段
                let result = response.data;
                // 处理提交成功的逻辑
                if (result.code === 200) {
                    showSuccessAlert(result);

                } else {
                    // 处理注册失败的逻辑
                    layer.msg(result.msg || '注册失败');
                }
            })
            .catch(function (error) {
                // 检查捕获的错误类型
                if (error.response) {
                    // 请求发出去，后端返回非 2xx 的状态码
                    layer.msg(error.response.data.msg || '注册失败');
                } else if (error.request) {
                    // 请求发出去，但没有收到响应
                    layer.msg('请求没有收到响应，请检查网络');
                } else {
                    // 在设置请求时触发错误
                    layer.msg('请求发送时发生错误');
                }
                // 其他类型的错误，例如代码错误
                console.error('Error:', error);
            });

    }

    // 提交邮箱注册表单的具体实现
    function submitEmailForm(formData) {
        // 编写提交邮箱注册表单的逻辑
        axios.post(ctx + 'register/email', {
            email: formData.email,
            password: formData.password,
        })
            .then(function (response) {
                let result = response.data;
                // 处理提交成功的逻辑
                if (result.code === 200) {
                    showSuccessAlert(response.data);
                } else {
                    layer.msg(response.data.msg || '注册失败');
                }
            })
            .catch(function (error) {
                // 处理提交失败的逻辑
                if (error.response) {
                    // 请求发出去，后端返回非 2xx 的状态码
                    layer.msg(error.response.data.msg || '注册失败');
                } else if (error.request) {
                    // 请求发出去，但没有收到响应
                    layer.msg('请求没有收到响应，请检查网络');
                } else {
                    // 在设置请求时触发错误
                    layer.msg('请求发送时发生错误');
                }
                // 其他类型的错误，例如代码错误
                console.error('Error:', error);
            });
    }
});
