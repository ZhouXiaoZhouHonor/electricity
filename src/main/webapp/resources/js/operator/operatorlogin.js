/**
 * 
 */
layui.use(['jquery','element','layer','form'],function(){
	var $=layui.jquery;
	var layer=layui.layer;
	var form=layui.form;
	
	//监听提交按钮
	form.on('submit(operatorLogin)',function(data){
		//alert('点击了登陆按钮');
		var operator={};//定义json对象
		var verifyCodeActual=$('#pile-kaptcha').val();//获取验证码
		console.log('验证码：'+verifyCodeActual);
		//获取表单数据
		var operatorData=form.val('operatorLoginForm');
		operator.operatorAccountNumber=operatorData.operatorUser;
		var password=operatorData.operatorPassword;
		//MD5是单向不可逆的，所以在验证账号的时候，就直接向后台提交真正的密码并进行比对即可
		//之后在手机用户注册过程中，会先加密然后进行提交
		operator.operatorPassword=password;
		
		console.log('账号:'+operatorData.operatorUser+'密码:'+operator.operatorPassword);
		var formData=new FormData();
		formData.append('operatorStr',JSON.stringify(operator));
		formData.append("verifyCodeActual",verifyCodeActual);
		
		$.ajax({
			url:'/zhou/login/operatorcheck',
			type:'POST',
			data:formData,
			contentType:false,
			processData:false,
			cache:false,//是否启用cache缓存
			success:function(data){
				if(data.success){
					//账号验证成功，需要跳转到主页面
					console.log('登陆成功');
					window.localStorage.setItem('operatorName',data.operator.operatorName);
					window.localStorage.setItem('operatorImg',data.operator.operatorImg);
					window.location.href='/zhou/page/main';
				}else{
					$('#login-reset').click();//清空内容
					$('#kaptcha_img').click();//刷新验证码图片
					layer.msg('账号或密码不正确，请重新输入登录');
				}
			}
		});
		return false;
	});
	
	//密码明文、暗文显示
	$('#eye-password').on('click',function(){
		//alert('点击了眼睛');
		//alert($('#ze-password').attr('type'));//获取元素值
		if($('#ze-password').attr('type')=='password'){
			//添加删除class值
			$('#eye-password').removeClass('layui-icon-extendyanjing1');
			$('#eye-password').addClass('layui-icon-extendyanjing');
			//更换某一属性的属性值
			$('#ze-password').attr('type','text');
		}else{
			$('#eye-password').removeClass('layui-icon-extendyanjing');
			$('#eye-password').addClass('layui-icon-extendyanjing1');
			$('#ze-password').attr('type','password');
		}
	});
	
});