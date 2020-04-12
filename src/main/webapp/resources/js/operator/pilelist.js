/**
 * 
 */
/*<select name="city" lay-verify="">
  <option value="">请选择一个城市</option>
  <option value="010">北京</option>
  <option value="021">上海</option>
  <option value="0571">杭州</option>
</select>    */
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
					'<input id="zhouze" type="text" name="title" required  lay-verify="required" placeholder="请输入名称" autocomplete="off" class="layui-input">'+
				'</div>'+
			'</div>'+
			'<div class="layui-col-md3">'+
				'<label class="layui-form-label">区域查询</label>'+
				'<div class="layui-input-block">'+
					'<select name="city" lay-verify="required" class="layui-input">'+
						'<option value="">请选择</option>'+
						'<option value="0">北京</option>'+
						'<option value="1">上海</option>'+
					'</select>'+
				'</div>'+
			'</div>'+
			'<div class="layui-col-md3">'+
				'<label class="layui-form-label">站点查询</label>'+
				'<div class="layui-input-block">'+
					'<input type="text" name="title" required  lay-verify="required" placeholder="请选择站点" autocomplete="off" class="layui-input">'+
				'</div>'+
			'</div>'+
		'</div>'+
		'<div class="layui-row">'+
			'<table id="pile-table" class="layui-table" lay-filter="piletable"></table>'+
		'</div>'+
	'</div>'+
	'<script type="text/html" id="barDemo">'+
	'<a class="layui-btn layui-btn-xs detail" lay-event="detail">查看</a>'+
	'<a class="layui-btn layui-btn-xs edit" lay-event="edit">编辑</a>'+
	/*'<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>'+*/
	'</script>';
	//动态添加HTML和表单
	//需要将方法暴露出来才可以让别的js文件中的layui.use()进行调用
	layui.define(['table','layer'],function(exports){
		var table=layui.table;
		var layer=layui.layer;
		//var pileList;/*将table从后台获取的数据保存起来，在点击查看详情的时候，将数据拿出来进行显示即可*/
		exports('pileTable',function(){
			//先将样式插入进去，搜索框的样式
			$('.layui-body').html(html);
			//TODO 需要另外将该操作员下的所有管理的区域以及站点全部列出来。方便查询
			
			//获取该管理员下的所有充电桩信息列表
			pileListTable(table);
			//对操作栏的按钮进行事件监听
			watchPileList(table,layer);
			//
		});
	});
	
	//获取该管理员下的所有区域信息以及站点信息
	function pileListAreaAndCoordianteInit(){
		//var get
		$.getJSON();
	}
	
	//获取该管理员下的所有充电桩信息列表
	function pileListTable(table){
		table.render({
			elem:'#pile-table',
			url:'http://localhost:8080/zhou/operator/getpilelist',
			method:'get',
			request:{
				pageName:'pageIndex',
				limitName:'pageSize'
			 },
			 cols:[[ //表头
				  {type: 'checkbox', fixed: 'left'},
				  {fixed:'left',title:'序号',align:'center',width:80,type:'numbers'},
			      {field: 'pileName',title:'充电桩名称',align:'center',width:200},
			      {field: 'pileEnableStatus',title:'状态',align:'center',width:80,templet:function(data){
			    	  if(data.pileEnableStatus==1){
			    		  return '正常';
			    	  }else{
			    		  return '不可用';
			    	  }
			      }},
			      {field: 'pileAddr',title:'地址',align:'center',width:400},
			      {field: 'pileNumber',title:'位置标号',align:'center',width: 100,templet:function(data){
			    	  return data.pileNumber+1;
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
					'data':data.pileList
				};
			},
			/*done:function(res,curr,count){
				console.log('res:'+res.data[0].pileLongitude);
				console.log('curr:'+curr);
				console.log('count:'+count);
				pileList=res.data;//将数据赋值给全局变量，用于后面的页面展示
			}*/
		});
		/*table显示过程中，多出来一列的原因：
		 *  给每一列设置了宽度，加起来还达不到容器的宽度，就自动填充了。
		          解决办法 给某一列不设置宽度，让其自适应*/
		/*layui 给数据表格加序号:
		 * 第一种需求，只给当前页加序号
			（1），给你的数据加上 templet属性或者toolbar属性
			（2），定义<script>标签：<script type="text/html" id="zizeng">
									{{d.LAY_TABLE_INDEX+1}}
								</script>
		第二种需求，包括分页的数据也加上序号
		 （1），直接添加type:'numbers'属性即可*/
	}
	
	//对操作栏的按钮实施监听
	function watchPileList(table,layer){
		table.on('tool(piletable)',function(obj){
			//obj中存储的是从后台返回的所有数据
			var pileList=obj.data;
			switch(obj.event){
			case 'detail':
				console.log('event的长度:'+JSON.stringify(obj));
				console.log('pileEnableStatus:'+pileList.pileEnableStatus);
				//点击查看建可以弹出充电桩详情信息的弹出框
				//该弹出层需要很大的页面，需要自定义才可以，这样看着比较美观
				//TODO 设计内容页面，展示充电桩的详细信息。使用栅格化即可
				var pileMessage='<div id="pileMessages" class="layui-container">'+
				'<div class="layui-row">'+
					'<div class="layui-col-md7">'+
						'<img src="'+pileList.pileImg+'"/>'+
					'</div>'+
					'<div class="layui-col-md5">'+
						'<div class="layui-row">'+
							'<i class="iconfont layui-icon-extendguanliyuan"></i>'+
							'<span>'+pileList.operator.operatorName+'</span>'+
						'</div>'+
						'<div class="layui-row">'+
							'<i class="iconfont layui-icon-extendgongzuozhuangtai"></i>'+
							(pileList.pileEnableStatus==1?'<span style="color:green;">良好</span>':'<span style="color:red;">故障</span>')+
						'</div>'+
					'</div>'+
				'</div>'+
				'<div class="layui-row">'+
					'<i class="iconfont layui-icon-extendzuobiao"></i>'+
					'<span>坐标:'+pileList.pileLongitude+','+pileList.pileLatitude+'</span>'+
				'</div>'+
				'<div class="layui-row">'+
					'<i class="iconfont layui-icon-extenddizhi"></i>'+
					'<span>地址:'+pileList.pileAddr+'</span>'+
				'</div>'+
				'<div class="layui-row">'+
					'<i class="iconfont layui-icon-extendchuangjianshijian"></i>'+
					'<span>创建时间:'+new Date(pileList.createTime).Format("yyyy-MM-dd")+'</span>'+
				'</div>'+
				'<div class="layui-row">'+
					'<i class="iconfont layui-icon-extendjieshao"></i>'+
					'<span>介绍:'+pileList.pileDesc+'</span>'+
				'</div>'+
				'<div class="layui-row">'+
					'<i class="iconfont layui-icon-extendshishijiance"></i>'+
					'<span>监测:'+'</span>'+'<button type="button" class="layui-btn layui-btn-radius layui-btn-normal">'+
					  '<i class="iconfont layui-icon-extendshishijiance-copy-copy"></i>'+
					  '</button>'+
				'</div>'+
				'</div>';//先定义好内容展示的HTML。拼接完成后，直接插入即可
				/*(pileList.pileEnableStatus==1?'良好':'故障')*/
				/*'<i class="iconfont layui-icon-extendgongzuozhuangtai" style="font-size: 18px; color: #F8F8FF; padding-right:5px;"></i>:'+
				pileList.pileEnableStatus*/
				layer.open({
					type:1,//默认的信息框比较好
					area:['400px','500px'],
					anim:1,//弹出方式
					title:pileList.pileName,//跟的是充电桩的名字
					content:pileMessage,//充电桩的详细信息，包含所有信息
				});
				break;
			case 'edit':
				layer.msg('点击了编辑按钮');
				console.log('edit:'+obj.event.length);
				break;
			};
		});
	}
	
	/*layui.use的结束符号*/
});







//对三个搜索框进行监听，用来查询特定或者某一范围的充电桩列表信息