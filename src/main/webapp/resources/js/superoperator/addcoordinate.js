/**
 * 
 */
layui.use(['jquery','layer','element','form'],function(){
	var $=layui.jquery;
	var layer=layui.layer;
	var element=layui.element;
	var form=layui.form;
	form.on('submit(addCoordinateSubmit)',function(data){
		var coordinate={};
		var verifyCodeActual=$('#coordinate-kaptcha').val();//获取验证码
		//获取表单数据
		var coordinateData=form.val('addCoordinateForm');
		coordinate.coordinateName=coordinateData.coordinateName;
		coordinate.priority=coordinateData.coordinatePriority;
		//将经纬度分开
		var longitudeAndLatitude=coordinateData.coordinateLocation.trim();
		var lonLa=longitudeAndLatitude.split(',');
		coordinate.coordinateLongitude=lonLa[0];
		coordinate.coordinateLatitude=lonLa[1];
		//定义表单数据对象
		var formData=new FormData();
		formData.append('coordinateStr',JSON.stringify(coordinate));
		formData.append("verifyCodeActual",verifyCodeActual);//添加验证码数据到表单数据对象中
		var registerAreaUrl='/zhou/superoperator/registercoordinate';
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
						content:'添加区域成功，是否继续添加?',
						closeBtn:0,
						btn:['取消','继续添加'],
						yes:function(index,layero){//按钮一(取消按钮)
							//点击取消按钮，跳转到展示页面 
							window.location.href='/zhou/pagesuper/main';
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
	
	/*点击取消添加，返回到主页面*/
	$('#cancel-super-add-coordinate').on('click',function(){
		window.location.href='/zhou/pagesuper/main';
	});
});