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
			'<table id="area-table" class="layui-table" lay-filter="areatable"></table>'+
		'</div>'+
	'</div>'+
	'<script type="text/html" id="barDemo">'+
	'<a class="layui-btn layui-btn-xs detail" lay-event="start">启用</a>'+
	'<a class="layui-btn layui-btn-xs layui-btn-danger detail" lay-event="stop">禁用</a>'+
	'<a class="layui-btn layui-btn-xs layui-btn-warm detail" lay-event="operation">分配管理员</a>'+
	'</script>';
	
	//动态添加HTML和表单
	//需要将方法暴露出来才可以让别的js文件中的layui.use()进行调用
	layui.define(['table','layer'],function(exports){
		var table=layui.table;
		var layer=layui.layer;
		//var pileList;/*将table从后台获取的数据保存起来，在点击查看详情的时候，将数据拿出来进行显示即可*/
		//可以定义多个exports
		//区域表格
		exports('areaTable',function(){
			//先将样式插入进去，搜索框的样式
			$('.layui-body').html(html);
			//获取该管理员下的所有充电桩信息列表
			var operatorAreaUrl='http://localhost:8080/zhou/superoperator/getarealist';
			areaListTable(table,operatorAreaUrl);
			//对操作栏的按钮进行事件监听
			watchAreaList(table,layer);
			
		});
		//添加区域信息
		exports('areaAdd',function(){
			//alert('通过layui调用了areaAdd');
			
		});
	});
	
	//操作栏中按钮实施监听
	function watchAreaList(table,layer){
		table.on('tool(areatable)',function(obj){
			var areaList=obj.data;
			var formData=new FormData();
			formData.append('areaId',areaList.areaId);
			switch(obj.event){
			case 'start':
				console.log('点击了启用按钮');
				formData.append('enableStatus',1);
				modifyAreaState(formData)
				break;
			case 'stop':
				formData.append('enableStatus',0);
				modifyAreaState(formData)
				break;
			case 'operation':
				//弹出带有下拉列表框的弹出层，进行选择，将区域进行分配
				areaOperationInitOfOperator(layer,areaList.areaId);
				break;
			}
		});
	}
	//更新area状态
	function modifyAreaState(formData){
		//定义地址
		var changeAreaStateUrl='/zhou/superoperator/modifyareastate';
		$.ajax({
			url:changeAreaStateUrl,
			method:'POST',
			data:formData,
			contentType:false,
			processData:false,
			cache:false,//是否启用cache缓存
			success:function(data){
				if(data.success){
					layer.msg('更新成功');
				}else{
					layer.msg('更新失败'+data.errMsg);
				}
				$('#area-message').click();
			}
		});
	}
	
	//获取所有的管理员的信息，用于在下拉列表框中进行展示
	function areaOperationInitOfOperator(layer,areaId){
		var areaOperationHtml=''+
			'<div class="layui-form-item">'+
				'<label class="layui-form-label">管理员</label>'+
				'<div class="layui-input-block" style="width:150px;">'+
					'<select id="area-operator" name="city" lay-verify="required" class="layui-input">'+
					'<option value="">请选择</option>'+
						'</select>'+
				'</div>'+
			'</div>';//定义下拉列表框的样式
		var areaOperationOfOperatorUrl='/zhou/superoperator/getoperatorlist';//从后台获取所有的操作员的信息
		$.getJSON(areaOperationOfOperatorUrl,function(data){
			if(data.success){
				var operatorList=data.operatorList;
				var html='';
				operatorList.map(function(item,index){
					html+='<option value="'+item.operatorId+'">'+item.operatorName+'</option>';
				});
				$('#area-operator').append(html);
			}else{
				
			}
		});
		layer.open({
			type:1,
			content:areaOperationHtml,
			area:['300px','200px'],
			btn:['取消','确定'],
			yes:function(index,layero){//按钮一(取消按钮)
				//点击取消按钮，跳转到展示页面 TODO
			},
			btn2:function(index,layero){//按钮二(继续添加按钮)
				//更新后台area的operator
				var operatorId=$('#area-operator').val();//获取operatorId
				var formData=new FormData();
				formData.append("operatorId",operatorId);
				formData.append("areaId",areaId);
				formData.append("enableStatus",1);
				//更新area
				var changeAreaOfOperator='/zhou/superoperator/modifyareastate';
				$.ajax({
					url:changeAreaOfOperator,
					method:'POST',
					data:formData,
					contentType:false,
					processData:false,
					cache:false,//是否启用cache缓存
					success:function(data){
						if(data.success){
							layer.msg('更新成功');
						}else{
							layer.msg('更新失败'+data.errMsg);
						}
						$('#area-message').click();
					}
				});
				//console.log('operatorId:'+operatorId);
			}
		});
	}
	
	//形成分页表格显示区域信息
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
			      {field:'areaName',title:'区域名称',align:'center',width:200},
			      {field:'operator.operatorName',title:'管理员',align:'center',templet:function(data){
			    	  if(data.operator==null){
			    		  return '<span style="color:#009688">无</span>';
			    	  }else{
			    		  return data.operator.operatorName;
			    	  }
			      }},
			      {field: 'areaEnableStatus',title:'状态',align:'center',width:80,templet:function(data){
			    	  if(data.areaEnableStatus==1){
			    		  return '<span style="color:#1E9FFF">可用</span>';
			    	  }else{
			    		  return '<span style="color:red">禁用</span>';
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
		});
	}
});








