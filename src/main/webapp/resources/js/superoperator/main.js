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
		window.location.href='/zhou/pagesuper/addcoordinate';
	});
	
	//显示公告信息
	$('#notice-message').on('click',function(){
		//alert('点击了notice');
		layui.noticeTable();
	});
	//添加公告信息
    $('#notice-add').on('click',function(){
    	window.location.href='/zhou/pagesuper/addnotice';
    });
    //显示管理员信息
    $('#operator-message').on('click',function(){
    	layui.operatorTable();
    });
    //添加管理员信息
    $('#add-operator').on('click',function(){
    	window.location.href='/zhou/pagesuper/addoperator';
    });
    //显示问题信息表格
    $('#problem-message').on('click',function(){
    	//layer.msg('点击问题');
    	layui.problemTable();
    });
});




