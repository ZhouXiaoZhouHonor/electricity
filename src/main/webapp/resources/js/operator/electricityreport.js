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
		'<div class="layui-row layui-col-space10">'+
			'<div class="layui-col-md3">'+
				'<label class="layui-form-label">名称查询</label>'+
				'<div class="layui-input-block">'+
					'<input id="report-list-name" type="text" name="title" required  lay-verify="required" placeholder="请输入名称" autocomplete="off" class="layui-input">'+
				'</div>'+
			'</div>'+
			/*'<div class="layui-col-md3">'+
				'<label class="layui-form-label">区域查询</label>'+
				'<div class="layui-input-block">'+
					'<select id="pile-list-area" name="city" lay-verify="required" class="layui-input">'+
						'<option value="">请选择</option>'+
					'</select>'+
				'</div>'+
			'</div>'+*/
		'</div>'+
		'<div class="layui-row">'+
			'<table id="report-table" class="layui-table" lay-filter="reporttable"></table>'+
		'</div>'+
	'</div>'+
	'<script type="text/html" id="barDemo">'+
	'<a class="layui-btn layui-btn-xs detail" lay-event="detail">查看</a>'+
	/*'<a class="layui-btn layui-btn-xs layui-btn-danger edit" lay-event="edit">注销</a>'+*/
	/*'<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>'+*/
	/*'<div class="layui-col-md3">'+
	'<label class="layui-form-label">站点查询</label>'+
	'<div class="layui-input-block">'+
		'<input type="text" name="title" required  lay-verify="required" placeholder="请选择站点" autocomplete="off" class="layui-input">'+
	'</div>'+
	'</div>'+*//*站点查询就不要了*/
	'</script>';
	
	layui.define(['table','layer'],function(exports){
		var table=layui.table;
		var layer=layui.layer;
		//var pileList;/*将table从后台获取的数据保存起来，在点击查看详情的时候，将数据拿出来进行显示即可*/
		exports('reportTable',function(){
			//先将样式插入进去，搜索框的样式
			$('.layui-body').html(html);
			//填充内容
			reportListTable(table);
			//监听按钮
			watchReportList(table,layer);
		});
	});
	
	function reportListTable(table){
		var reportUrl='/zhou/watchpileelectricity/electricityreport';
		table.render({
			elem:'#report-table',
			url:reportUrl,
			method:'get',
			request:{
				pageName:'pageIndex',
				limitName:'pageSize'
			 },
			 cols:[[ //表头
				  {type: 'checkbox', fixed: 'left'},
				  {fixed:'left',title:'序号',align:'center',width:80,type:'numbers'},
			      {field: 'electricityReportName',title:'报表名称',align:'center',width:200},
			      {field: 'electricityReportEnableStatus',title:'状态',align:'center',width:80,templet:function(data){
			    	  if(data.electricityReportEnableStatus==1){
			    		  return '正常';
			    	  }else{
			    		  return '不正常';
			    	  }
			      }},
			      {field: 'createTime',title:'生成时间',align:'center',width:400,templet:function(data){
			    	  return new Date(data.createTime).Format("yyyy-MM-dd")
			      }},
			      {field: 'pile.pileName',title:'所属充电桩',align:'center',width: 100,templet:function(data){
			    	  return data.pile.pileName;
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
					'data':data.reportList
				};
			},
		});
	}
	
	//对操作栏按钮实施监听
	function watchReportList(table,layer){
		table.on('tool(reporttable)',function(obj){
			//从obj中取出数据，主要是地址数据
			var report=obj.data;
			switch(obj.event){
			case'detail':
				console.log('点击了，文件路径为:'+(report.electricityReportLink).replace('.doc','.pdf'));
				var pdfUrl=(report.electricityReportLink).replace('.doc','.pdf');
				var report=''+
				'<div style="width:600px;height:600;overflow:auto;">'+
				'<embed src="'+pdfUrl+'"></embed>'+
				'</div>';
				//获取文件并展示
				layer.open({
					type:1,//默认的信息框比较好
					area:['600px','600px'],
					anim:1,//弹出方式
					title:'预览PDF',//跟的是充电桩的名字
					content:report,//充电桩的详细信息，包含所有信息
				});
			break;
			}
		});
	}
});



