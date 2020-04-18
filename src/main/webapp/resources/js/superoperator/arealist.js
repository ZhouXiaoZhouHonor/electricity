/**
 * 
 */
layui.use(['jquery','table','layer','element'],function(){
	var $=layui.jquery;
	var form=layui.form;
	var table=layui.table;
	var element=element;
	
	//先把样式写出来
	var html=''+
	'<div class="layui-container ze-palette">'+
		'<div class="layui-row">'+
			'<table id="area-table" class="layui-table" lay-filter="piletable"></table>'+
		'</div>'+
	'</div>'+
	'<script type="text/html" id="barDemo">'+
	'<a class="layui-btn layui-btn-xs detail" lay-event="stop">启用</a>'+
	'<a class="layui-btn layui-btn-xs layui-btn-danger detail" lay-event="stop">禁用</a>'+
	/*'<a class="layui-btn layui-btn-xs edit" lay-event="edit">编辑</a>'+*/
	/*'<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>'+*/
	/*'<div class="layui-col-md3">'+
	'<label class="layui-form-label">站点查询</label>'+
	'<div class="layui-input-block">'+
		'<input type="text" name="title" required  lay-verify="required" placeholder="请选择站点" autocomplete="off" class="layui-input">'+
	'</div>'+
	'</div>'+*//*站点查询就不要了*/
	'</script>';
	
	//动态添加HTML和表单
	//需要将方法暴露出来才可以让别的js文件中的layui.use()进行调用
	layui.define(['table','layer'],function(exports){
		var table=layui.table;
		var layer=layui.layer;
		//var pileList;/*将table从后台获取的数据保存起来，在点击查看详情的时候，将数据拿出来进行显示即可*/
		exports('areaTable',function(){
			//先将样式插入进去，搜索框的样式
			$('.layui-body').html(html);
			//需要另外将该操作员下的所有管理的区域全部列出来。方便查询
			//pileListAreaAndCoordianteInit();
			//获取该管理员下的所有充电桩信息列表
			var operatorAreaUrl='http://localhost:8080/zhou/superoperator/getarealist';
			areaListTable(table,operatorAreaUrl);
			//对操作栏的按钮进行事件监听
			//watchPileList(table,layer);
			
		});
	});
	
	function areaListTable(table,operatorAreaUrl){
		table.render({
			elem:'#area-table',
			url:operatorAreaUrl,
			method:'get',
			request:{
				pageName:'pageIndex',
				limitName:'pageSize'
			 },
			 cols:[[ //表头
				  {type: 'checkbox', fixed: 'left'},
				  {fixed:'left',title:'序号',align:'center',width:80,type:'numbers'},
			      {field: 'areaName',title:'区域名称',align:'center',width:200},
			      {field: 'areaEnableStatus',title:'状态',align:'center',width:80,templet:function(data){
			    	  if(data.areaEnableStatus==1){
			    		  return '可用';
			    	  }else{
			    		  return '禁用';
			    	  }
			      }},
			      {fixed: 'right',title:'操作',align:'center',toolbar:'#barDemo'}
			    ]],
		    page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
				layout: ['count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
		        limit:5, //一页显示多少条
		        groups:2,//只显示 2 个连续页码
		        first: "首页", //不显示首页
		        last: "尾页", //不显示尾页
		    },
			parseData:function(data){
				return{
					'code':0,
					'msg':'',
					'count':data.count,
					'data':data.areaList
				};
			},
			/*done:function(res,curr,count){
				console.log('res:'+res.data[0].pileLongitude);
				console.log('curr:'+curr);
				console.log('count:'+count);
				pileList=res.data;//将数据赋值给全局变量，用于后面的页面展示
			}*/
		});
	}
	
});








