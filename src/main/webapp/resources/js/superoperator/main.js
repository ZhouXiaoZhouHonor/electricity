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
		//跳转到添加页面
		window.location.href='/zhou/pagesuper/addarea';
		//TODO 能跳转也应该给出能返回的按钮
	});
	
	//显示站点信息
	$('#coordinate-message').on('click',function(){
		console.log('点击了站点信息');
		layui.coordinateTable();
	});
	//添加站点信息
	$('#coordinate-add').on('click',function(){
		console.log('点击了站点添加信息');
	});
	
});




