/**
 * 
 */
//TODO 向后台添加公告信息
layui.use(['layer','jquery','form','element'],function(){
	var form=layui.form;
	var $=layui.jquery;
	var layer=layui.layer;
	
	form.on('submit(addNoticeSubmit)',function(data){
		var notice={};
		var verifyCodeActual=$('#notice-kaptcha').val();//获取验证码
		//获取表单数据
		var noticeData=form.val('addNoticeForm');
		notice.noticeName=noticeData.noticeName;
		notice.noticePriority=noticeData.noticePriority;
		//定义表单数据对象
		var formData=new FormData();
		formData.append('noticeStr',JSON.stringify(notice));
		//获取公告图片
		var noticeImg=$('#noticeImg')[0].files[0];
		var noticeLink=$('#noticeFile')[0].files[0];
		formData.append("noticeImg",noticeImg);
		formData.append("noticeLink",noticeLink);
		formData.append("verifyCodeActual",verifyCodeActual);//添加验证码数据到表单数据对象中
		var registerNoticeUrl='/zhou/superoperator/registernotice';
		$.ajax({
			url:registerNoticeUrl,
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
						content:'添加公告成功，是否继续添加?',
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
							$("#add-area-form")[0].reset();
							form.render();//重置表单
						},
						cancel: function(index, layero){//点击叉号，重置表单
							$("#add-area-form")[0].reset();
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
	/*点击取消按钮时，返回到notice页面*/
	$('#cancel-super-add-notice').on('click',function(){
		window.location.href='/zhou/pagesuper/main';
	});
});
