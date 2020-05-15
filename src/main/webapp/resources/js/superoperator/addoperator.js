/*
 * 
 */
layui.use(['layer','jquery','form','element'],function(){
	var form=layui.form;
	var $=layui.jquery;
	var layer=layui.layer;
	
	form.on('submit(addOperatorSubmit)',function(data){
		var operator={};
		var verifyCodeActual=$('#operator-kaptcha').val();//获取验证码
		//获取表单数据
		var operatorData=form.val('addOperatorForm');
		operator.operatorName=operatorData.operatorName;
		operator.operatorAccountNumber=operatorData.operatorAccountNumber;
		var password=operatorData.operatorPassword;
		operator.operatorPassword=getSaltMD5(password);
		//定义表单数据对象
		var formData=new FormData();
		formData.append('operatorStr',JSON.stringify(operator));
		formData.append("verifyCodeActual",verifyCodeActual);//添加验证码数据到表单数据对象中
		//获取头像
		var operatorImg=$('#operatorImg')[0].files[0];
		formData.append('operatorImg',operatorImg);
		var registerAreaUrl='/zhou/superoperator/registeroperator';
		$.ajax({
			url:registerAreaUrl,
			method:'POST',
			data:formData,
			contentType:false,
			processData:false,
			cache:false,//是否启用cache缓存
			success:function(data){
				if(data.success){
					//给出提示框判断是继续添加还是跳转到详情页面
					layer.open({
						title:false,
						content:'添加管理员成功，是否继续添加?',
						closeBtn:0,
						btn:['取消','继续添加'],
						yes:function(index,layero){//按钮一(取消按钮)
							//点击取消按钮，跳转到展示页面 TODO
							window.location.href='/zhou/pagesuper/main';
							//$('#area-message').click();
						},
						btn2:function(index,layero){//按钮二(继续添加按钮)
							//刷新本页面，继续添加
							/*使用layui框架，在页面进行表单数据提交后,要使页面表单数据清空，可使用下面方法*/
							$("#add-operator-form")[0].reset();
							form.render();//重置表单
						},
						cancel: function(index, layero){//点击叉号，重置表单
							$("#add-operator-form")[0].reset();
							form.render();//重置表单
							return false; 
						}
					});
				}else{
					layer.msg("errMsg:"+data.errMsg);
				}
			}
		});
		return false;
	});
	/*点击取消按钮时，返回到area页面*/
	$('#cancel-super-add-operator').on('click',function(){
		window.location.href='/zhou/pagesuper/main';
	});
});