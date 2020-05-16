/**
 * 
 */
layui.use(['jquery','table','layer','element'],function(){
	var $=layui.jquery;
	var form=layui.form;
	var table=layui.table;
	var element=element;
	
	//定义总体样式
	var html=''+
		'<div id="phoneUserInfo" class="layui-container">'+
			
		 '</div>';
	//定义函数
	layui.define(['table','layer'],function(exports){
		var table=layui.table;
		var layer=layui.layer;
		exports('phoneUserList',function(){
			//获取所有手机用户
			getAllPhoneUser();
		});
	});
	
	function getAllPhoneUser(){
		var getAllUserUrl='/zhou/operator/getalluser';
		$.getJSON(getAllUserUrl,function(data){
			if(data.success){
				var userNumberHtml=''+
					'<div class="layui-row">'+
						'<div class="layui-col-md12">'+
							'用户总数：'+data.count+
						'</div>'+
					'</div>';
				$('#phoneUserInfo').append(userNumberHtml);
			}else{
				layer.msg('没有获取到数据');
			}
		});
	}
});