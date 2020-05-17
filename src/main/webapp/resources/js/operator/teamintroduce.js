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
		'<div id="phoneUserInfo" class="layui-container">中小城市地方'+
		'<img src="../resources/images/about.jpeg">'+
		 '</div>';
	
	//定义函数
	layui.define(['table','layer'],function(exports){
		var table=layui.table;
		var layer=layui.layer;
		exports('teamIntroduce',function(){
			alert('进来了');
			//将布局样式先插入
			$('#container').html(html);
		});
	});
});