/**
 * 
 */
layui.use(['layer','jquery','element','table'],function(){
	var layer=layui.layer;
	var $=layui.jquery;
	var table=layui.table;
	
	layui.define(['table','layer'],function(exports){
		var table=layui.table;
		var layer=layui.layer;
		
		//先把样式写出来
		var html=''+
		'<div class="layui-container ze-palette">'+
			'<div class="layui-row">'+
				'<table id="coordinate-table" class="layui-table" lay-filter="coordinatetable"></table>'+
			'</div>'+
		'</div>'+
		'<script type="text/html" id="barDemo">'+
		'<a class="layui-btn layui-btn-xs detail" lay-event="start">启用</a>'+
		'<a class="layui-btn layui-btn-xs layui-btn-danger detail" lay-event="stop">禁用</a>'+
		'<a class="layui-btn layui-btn-xs detail" lay-event="operation">分配区域</a>'+
		'</script>';
		
		exports('coordinateTable',function(){
			$('.layui-body').html(html);
			//获取该管理员下所有的站点信息
			var operatorCoordinateUrl='http://localhost:8080/zhou/superoperator/getcoordinatelist';
			coordinateListTable(table,operatorCoordinateUrl);
			//对操作栏的按钮进行事件监听
			watchCoordinateList(table,layer);
		});
		
		//按钮监测
		function watchCoordinateList(table,layer){
			console.log('点击了监测站点按钮');
			table.on('tool(coordinatetable)',function(obj){
				var coordinateList=obj.data;
				var formData=new FormData();
				formData.append('coordinateId',coordinateList.coordinateId);
				switch(obj.event){
				case 'start':
					formData.append('enableStatus',1);
					modifyCoordinateState(formData)
					break;
				case 'stop':
					formData.append('enableStatus',0);
					modifyCoordinateState(formData)
					break;
				case 'operation':
					coordinateOperationInitOfArea(layer,coordinateList.coordinateId);
					break;
				}
			});
		}
		//分配区域信息
		function coordinateOperationInitOfArea(layer,coordinateId){
			var coordinateOperationHtml=''+
			'<div class="layui-form-item">'+
				'<label class="layui-form-label">管理员</label>'+
				'<div class="layui-input-block" style="width:150px;">'+
					'<select id="coordinate-area" name="city" lay-verify="required" class="layui-input">'+
					'<option value="">请选择</option>'+
						'</select>'+
				'</div>'+
			'</div>';//定义下拉列表框的样式
			
			var coordinateOperationOfAreaUrl='/zhou/superadmin/getarealist';//从后台获取所有的操作员的信息
			$.getJSON(coordinateOperationOfAreaUrl,function(data){
				if(data.success){
					var areaList=data.areaList;
					var html='';
					areaList.map(function(item,index){
						html+='<option value="'+item.areaId+'">'+item.areaName+'</option>';
					});
					$('#coordinate-area').append(html);
				}else{
					
				}
			});
			
			layer.open({
				type:1,
				content:coordinateOperationHtml,
				area:['300px','200px'],
				btn:['取消','确定'],
				yes:function(index,layero){//按钮一(取消按钮)
					//点击取消按钮，跳转到展示页面 TODO
				},
				btn2:function(index,layero){//按钮二(继续添加按钮)
					//更新后台area的operator
					var areaId=$('#coordinate-area').val();//获取operatorId
					var formData=new FormData();
					formData.append("areaId",areaId);
					formData.append("coordinateId",coordinateId);
					formData.append("enableStatus",1);
					//更新area
					var changeCoordinatOfArea='/zhou/superoperator/modifycoordinatestate';
					$.ajax({
						url:changeCoordinatOfArea,
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
							$('#coordinate-message').click();
						}
					});
					//console.log('operatorId:'+operatorId);
				}
			});
			
		}
		//更新站点状态
		function modifyCoordinateState(formData){
			var changeCoordinateStateUrl='/zhou/superoperator/modifycoordinatestate';
			$.ajax({
				url:changeCoordinateStateUrl,
				type:'POST',
				data:formData,
				contentType:false,
				processData:false,
				cache:false,//是否启用cache缓存
				success:function(data){
					if(data.success){
						console.log('成功');
						layer.msg('更新站点成功');
					}else{
						layer.msg('更新站点状态失败'+data.errMsg);
					}
					$('#coordinate-message').click();
				}
			});
		}
		
		//站点分页
		function coordinateListTable(table,operatorCoordinateUrl){
			table.render({
				elem:'#coordinate-table',
				url:operatorCoordinateUrl,
				method:'get',
				request:{
					pageName:'pageIndex',
					limitName:'pageSize'
				 },
				 cols:[[ //表头
					  {type: 'checkbox', fixed: 'left'},
					  {fixed:'left',title:'序号',align:'center',width:80,type:'numbers'},
				      {field:'coordinateName',title:'站点名称',align:'center',width:200},
				      {field: 'coordinateEnableStatus',title:'状态',align:'center',width:80,templet:function(data){
				    	  if(data.coordinateEnableStatus==1){
				    		  return '<span style="color:#1E9FFF">可用</span>';
				    	  }else{
				    		  return '<span style="color:red">禁用</span>';
				    	  }
				      }},
				      {field:'areaName',title:'所属区域',align:'center',width:100,templet:function(data){
				    	  return data.area.areaName;
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
						'data':data.coordinateList
					};
				},
			});
		}
	});
});