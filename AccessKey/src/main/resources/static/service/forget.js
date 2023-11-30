layui.use(['form', 'layer'], function () {
    var form = layui.form;
    var layer = layui.layer;

    // 自定义验证规则
    form.verify({
        key: function (value, item) {
            // 判断输入的内容是手机号或者邮箱
            if (!/^(13\d|14[5-9]|15[0-3,5-9]|166|17[0-8]|18\d|19[8,9])\d{8}$/.test(value) &&
                !/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value)) {
                return '请输入正确的手机号或邮箱';
            }
        },
        password: function (value, item) {
            // 自定义密码规则，这里只是一个示例
            if (!/^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z]{6,18}$/.test(value)) {
                return '密码必须6到16位，包括大小写字母且不能有空格特殊字符';
            }
        },
        confirmPassword: function (value, item) {
            var password = $(item).parents('.layui-form-item').prev().find('input[name=password]').val();
            if (value !== password) {
                return '两次输入的密码不一致';
            }
        },

    });

    // 获取验证码
    $('#send').click(function () {

        // 获取邮箱输入框的值
        let Value = $('#content').val();
        let $button = $(this);


        // 禁用按钮，修改样式
        $button.attr('disabled', true).addClass('disabled');

        let path;
        // 如果邮箱合法，改变路径
        if (/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(Value)) {
            // 如果邮箱合法，发送 POST 请求获取验证码
            axios.post(ctx + 'getEmailCode', {
                email: Value
            })
                .then(function (response) {
                    let result = response.data;
                    // 处理获取验证码成功的逻辑
                    if (result.code === 200) {
                        layer.msg(result.data || '验证码已发送');
                        startCountdown($button, 300); // 300 秒倒计时
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
        } else if (/^(13\d|14[5-9]|15[0-3,5-9]|166|17[0-8]|18\d|19[8,9])\d{8}$/.test(Value)) {
            axios.post(ctx + 'getPhoneCode', {
                phone: Value
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
        }

        //验证成功


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

    function SuccessAlert(result) {
        document.getElementById('form1').style.display = 'none';

        // 2. 显示第二个表单
        document.getElementById('form2').style.display = 'block';

        // 3. 刷新第一个表单
        document.getElementById('form1').reset();
    }

    // 提交验证码和号码
    form.on('submit(demo-reg-confirm)', function (data) {
        let field = data.field; // 获取表单字段值
        //判断是手机找回还是邮箱找回

        //邮箱找回
        axios.post(ctx + 'forget', {
            key: field.key,
            code: field.vercode,
        })
            .then(function (response) {
                let result = response.data;
                // 处理提交成功的逻辑
                if (result.code === 200) {
                    SuccessAlert(result);
                    console.log(result);
                } else {
                    console.log(result);
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
                    layer.msg(error.response.data.msg || '找回失败');
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

    });

    function showSuccessAlert(result) {
        layer.alert(result.msg || +'请输入新密码', {
            icon: 1, // 1表示成功的图标
            title: '提示',
            closeBtn: 0 // 关闭按钮不显示
        }, function (index) {
            // 用户点击确定后执行的回调函数
            layer.close(index); // 关闭提示框
        });
    }

    function showFailedAlert(result) {
        layer.alert(result.msg || '修改失败!', {
            icon: 2, // 1表示成功的图标
            title: '提示',
            closeBtn: 0 // 关闭按钮不显示
        }, function (index) {
            // 用户点击确定后
            setTimeout(function () {
                layer.close(index); // 关闭提示框
                window.location.href = ctx + 'login'; // 跳转到登录页面
            }, 3000); // 3000毫秒即3秒
        });
    }


    // 提交新密码
    form.on('submit(demo-reg-modify)', function (data) {
        let field = data.field; // 获取表单字段值
        //判断是手机找回还是邮箱找回

        //邮箱找回
        axios.post(ctx + 'modify', {
            password: field.password,
            confirmPassword: field.confirmPassword,
        })
            .then(function (response) {
                let result = response.data;
                // 处理提交成功的逻辑
                if (result.code === 200) {
                    modifySuccessAlert(result);
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
                    layer.msg(error.response.data.msg || '找回失败');
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


        function modifySuccessAlert(result) {
            layer.alert(result.msg || +'请输入新密码', {
                icon: 1, // 1表示成功的图标
                title: '提示',
                closeBtn: 0 // 关闭按钮不显示
            }, function (index) {
                // 用户点击确定后执行的回调函数
                window.location.href = ctx + 'login'; // 跳转到登录页面
                layer.close(index); // 关闭提示框
            });
        }

    });

});
