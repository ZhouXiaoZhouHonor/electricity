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
		'<img src="../resources/images/about.jpeg">'+
		 '</div>';
	//定义函数
	layui.define(['jquery'],function(exports){
		//var $=layui.jquery;
		//var layer=layui.layer;
		exports('teamIntroduce',function(){
			//将布局样式先插入
			$('#container').html(html);
		});
	});
});