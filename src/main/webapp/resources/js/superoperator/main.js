/**
 * 
 */
layui.use(['jquery','element','layer','table'],function(){
	var $=layui.jquery;
	var layer=layui.layer;
	var table=layui.table;
	
	//显示区域信息
	$('#area-message').on('click',function(){
		layui.areaTable();
	});
	//添加区域信息
	$('#area-add').on('click',function(){
		//alert('点击了添加按钮');
		layui.areaAdd();
	});
	
});