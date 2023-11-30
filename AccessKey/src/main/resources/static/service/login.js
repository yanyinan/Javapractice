layui.use(['form', 'layer'], function () {
    var form = layui.form;
    var layer = layui.layer;

    var isValid = true;
    // 用户名重复校验

    // 为用户名输入框添加失去焦点事件监听
    $('#username-form input:first').focus(); // 将焦点聚焦到用户名输入框
    let captchaImgs = document.querySelectorAll(".captchaImg");
    captchaImgs.forEach(img => {
        img.onclick = function () {
            // this = captchaImg;
            this.src = ctx + "captcha?" + new Date().getTime();
        };
    });

    //按钮监听 获取手机验证码
    $('#send-login-phone').on('click', function () {
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
        axios.post(ctx + 'getPhoneCode', {
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
    $('#send-login-email').on('click', function () {

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
        axios.post(ctx + 'getEmailCode', {
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
            if (!/^[a-zA-Z]{4,16}$/.test(value)) {
                return '用户名必须为4到16位（字母、数字、下划线、减号）';
            }
        },
        password: function (value, item) {
            if (!/^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z]{6,18}$/.test(value)) {
                return '密码必须6到12位，包括大小写字母且不能有空格';
            }
        },
        cellphone: function (value, item) {
            if (!/^\d{11}$/.test(value)) {
                return '请输入有效的11位手机号码';
            }
        },
        email: function (value, item) {
            if (!/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/.test(value)) {
                return '请输入有效的邮箱地址';
            }
        }
    });

    $('.icon-toggle').on('click', function () {
        var targetForm = $(this).data('target');
        $('.layui-form').hide();
        $('#' + targetForm).show();
        $('#' + targetForm).find('input:first').focus();
        resetForm('#' + targetForm);
        // form.render();
    });

    function resetForm(formId) {
        // 获取表单元素
        var form = $(formId)[0];

        // 重置表单
        form.reset();

        // 如果使用了Layui表单，还需要重新渲染
        if (typeof form.render === 'function') {
            form.render();
        }
    }

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

    form.on('submit(demo-login)', function (data) {
        isValid = validateFormFields(data);
        if (!isValid) {
            return false;
        }
        // 获取当前显示的表单类型
        let currentFormId = $('.layui-form:visible').attr('id');


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
        axios.post(ctx + 'login/username', {
            username: formData.username,
            password: formData.password,
            captcha: formData.captcha,
            rememberMe: formData.remember === "on",
        })
            .then(function (response) {
                let result = response.data;
                // 处理提交成功的逻辑
                if (result.code === 200) {
                    showSuccessAlert(result);
                } else {
                    // 处理注册失败的逻辑
                   showFailedAlert(result)
                }
            })
            .catch(function (error) {
                // 处理提交失败的逻辑
                if (error.response) {
                    // 请求已发送，但服务器返回状态码不是 2xx
                    console.error('Error Response:', error.response);

                    // 例如，显示服务器返回的错误信息
                    layer.msg(error.response.data.msg || '登录失败');
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
        layer.alert(result.msg || '登录成功!' + '点击确定前往登录页面', {
            icon: 1, // 1表示成功的图标
            title: '提示',
            closeBtn: 0 // 关闭按钮不显示
        }, function (index) {
            // 用户点击确定后执行的回调函数
            window.location.href = result.data; // 跳转到登录页面
            layer.close(index); // 关闭提示框
        });
    }

    function showFailedAlert(result) {
        layer.alert(result.msg || '登录失败!', {
            icon: 2, // 1表示成功的图标
            title: '提示',
            closeBtn: 0 // 关闭按钮不显示
        }, function (index) {
            // 用户点击确定后刷新页面
            window.location.reload()


            layer.close(index); // 关闭提示框
        });
    }

    // 提交手机号注册表单的具体实现
    function submitPhoneForm(formData) {
        // 编写提交手机号注册表单的逻辑
        axios.post(ctx + 'login/phone', {
            phone: formData.cellphone,
            code: formData.vercode,
            captcha: formData.captcha,
        rememberMe: formData.remember === "on",

        })
            .then(function (response) {
                // 检查后端返回的 Resp 对象中的 code 字段
                let result = response.data;
                // 处理提交成功的逻辑
                if (result.code === 200) {
                    showSuccessAlert(result);

                } else {
                    // 处理注册失败的逻辑
                    showFailedAlert(result)
                }
            })
            .catch(function (error) {
                // 检查捕获的错误类型
                if (error.response) {
                    // 请求发出去，后端返回非 2xx 的状态码
                    layer.msg(error.response.data.msg || '登录失败');
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
        axios.post(ctx + 'login/email', {
            email: formData.email,
            code: formData.vercode,
            captcha: formData.captcha,
            rememberMe: formData.remember === "on",
        })
            .then(function (response) {
                let result = response.data;
                // 处理提交成功的逻辑
                if (result.code === 200) {
                    showSuccessAlert(response.data);
                } else {
                    showFailedAlert(result)
                }
            })
            .catch(function (error) {
                // 处理提交失败的逻辑
                if (error.response) {
                    // 请求发出去，后端返回非 2xx 的状态码
                    layer.msg(error.response.data.msg || '登录失败');
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