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
	
	
});