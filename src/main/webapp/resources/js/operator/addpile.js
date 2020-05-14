/**
 * 
 */
layui.use(['form','layer','jquery','element'],function(){
	var form=layui.form;
	var layer=layui.layer;
	var $=layui.jquery;
	var element=layui.element;
	var coordinateList;//站点集合对象
	var coordinateId;//站点Id
	//var coordinateNumber;//保存站点中可容纳充电桩数量的值
	var pileList;//充电桩集合对象
	var pileNumberIndex;//座位号弹出层的序号
	var isAddPileIndex;//是否继续添加充电桩的弹出层的序号
	
	getArea();
	//初始化页面信息
	//先获取区域信息，当区域信息选中后响应式获取对应的coordinate
	function getArea(){
		var areaUrl='/zhou/operator/getarealist';
		$.getJSON(areaUrl,function(data){
			if(data.success){
				var areaList=data.areaList;
				var html='<option value=""></option>';
				areaList.map(function(item,index){
					html+='<option value="'+item.areaId+'">'+item.areaName+'</option>';
				});
				$('#area-list').html(html);
				form.render('select');//每一次更改内容，都要进行刷新
			}else{
				layer.msg('获取区域信息失败');
			}
		});
	}
	
	//当区域信息选中后，会通过getJSON将相关的coordinate显示出来
	form.on('select(arealist)',function(data){
		var areaId=data.value;
		//console.log();
		var coordinateUrl='/zhou/operator/getcoordinatelist?areaId='+areaId;
		$.getJSON(coordinateUrl,function(data){
			if(data.success){//将数据插入HTML中
				coordinateList=data.coordinateList;
				var html='<option value=""></option>';
				coordinateList.map(function(item,index){
					html+='<option value="'+item.coordinateId+'">'
					+item.coordinateName+'</option>';
				});
				
				$('#coordinate-list').html(html);
				form.render('select');//每一次更改内容，都要进行刷新
			}else{
				//说明该区域没有坐标点，则将区域信息恢复成默认值并给出提示
				layer.msg('获取站点失败');
			}
		});
	});
	
	//当coordinate发生改变时，需要将该站点下还有几个可用的充电桩显示出来其座位号
	form.on('select(coordinatelist)',function(data){
		coordinateId=data.value;
		var coordinateNumberUrl='/zhou/operator/getpilelist?coordinateId='+
		coordinateId+'&pageSize=50';//pagesize写死
		$.getJSON(coordinateNumberUrl,function(data){
			if(data.success){//向充电站插入选位置的html进行选择
				pileList=data.pileList;
				//如果该站点是全新的站点，则直接从获取站点信息
				if(pileList==null||pileList==undefined||pileList==""){
					pileNullNumberInit(coordinateId);
				}else{
					pileNumberInit(pileList);
				}
				
			}else{
				layer.msg(data.errMsg);
			}
		});
	});
	
	//当单独点击站点选择时，也应该可以将站点信息显示出来
	$('#pile-number').click(function(){
		if(coordinateId==undefined){
			layer.msg('请先选择站点');
		}
		pileNumberInit(pileList);
	});
	//如果该站点是全新的站点，则不能从充电桩中获取站点桩数信息
	function pileNullNumberInit(coordinateId1){
		var coordinateId=coordinateId1;
		var getCoordinateUrl='/zhou/operator/getcoordinate?coordinateId='+coordinateId;
		$.getJSON(getCoordinateUrl,function(data){
			if(data.success){
				var pileNumber=data.coordinate.coordinatePileNumber;
				var pileNumberHtml='';
				for(var i=1;i<=pileNumber;i++){
					if(i<10){
						pileNumberHtml+='<button type="button" '+
							'class="layui-btn layui-btn-normal pile-number" '+
								'value="'+i+'">0'+i+'</button> ';
					}else{
						pileNumberHtml+='<button type="button" '+
							'class="layui-btn layui-btn-normal pile-number" '+
							'value="'+i+'">'+i+'</button> ';
					}
				}
				var html='<div class="layui-btn-container">'+
				pileNumberHtml+'</div>';
				pileNumberIndex=layer.open({
					type: 1, 
					title:'请选择充电桩位置',
					anim: 5,//显示样式-渐显
					content:html//这里content是一个普通的String
					});
			}else{
				layer.msg(data.errMsg);
			}
		});
	}
	
	//初始化站点信息的位置
	function pileNumberInit(piles){
		var coordinatePileNumber=piles[0].coordinate.coordinatePileNumber;
		var pileNumberHtml='';
		var optionElem=new Array();
		for(var i=1;i<=coordinatePileNumber;i++){
			if(i<10){
				pileNumberHtml+='<button type="button" '+
					'class="layui-btn layui-btn-normal pile-number" '+
						'value="'+i+'">0'+i+'</button> ';
			}else{
				pileNumberHtml+='<button type="button" '+
					'class="layui-btn layui-btn-normal pile-number" '+
					'value="'+i+'">'+i+'</button> ';
			}
		}
		var html='<div class="layui-btn-container">'+
		pileNumberHtml+'</div>';
		pileNumberIndex=layer.open({
			type: 1, 
			title:'请选择充电桩位置',
			anim: 5,//显示样式-渐显
			content:html//这里content是一个普通的String
			});
		//var pileNumberBtn=$('.pile-number');
		/*因为这样$('label')[j].addClass()已经转换为js了，不是jquery，
		 * 当然没有了addClass()方法了，如果还想使用jquery,
		 * 可以是$(..).eq(j).addClass();*/
		pileList.map(function(item,index){
			$('.pile-number').eq(item.pileNumber).removeClass('layui-btn-normal').addClass('layui-btn-disabled');
		});
	}
	
	//当点击弹出框中的按钮时，将值插入input中
	$(document).on('click','.layui-btn-normal',function(e){
		var pileNumber=$(e.target).val();//获取当前被选中的按钮的值
		$('#pile-number').val(pileNumber);//将值插入
		layer.close(pileNumberIndex);//当值插入后关闭充电桩位置序号弹出层
	});
	
	//监听提交按钮，进行数据后台提交
	form.on('submit(addPileSubmit)',function(data){
		var pile={};//定义pile JSON对象 
		var verifyCodeActual=$('#pile-kaptcha').val();//获取验证码
		console.log('验证码：'+verifyCodeActual);
		//获取表单数据
		var pileData=form.val('addPileForm');
		//将数据填充到JSON对象中pile
		pile.pileDesc=pileData.desc;//描述
		pile.pileImg=pileData.pileImg;//图片名称
		pile.pileNumber=pileData.pileCoordinateNumber-1;//位置号码
		pile.area={areaId:pileData.area};//区域Id
		pile.coordinate={coordinateId:pileData.coordinate};//站点Id
		console.log('寻找经纬度1-0'+coordinateList[0].coordinateId);
		coordinateList.map(function(item,index){
			//console.log('寻找经纬度1');
			//console.log('寻找经纬度1-1'+pileData.coordinate);
			//console.log('寻找经纬度1-2'+item.coordinateId+'@@'+index);
			if(pileData.coordinate==item.coordinateId){
				//console.log('寻找经纬度2');
				//添加经纬度
				pile.pileLongitude=item.coordinateLongitude;//经度不变
				pile.pileLatitude=item.coordinateLatitude+0.00004*pileData.pileCoordinateNumber;//纬度变，每隔4米放一个充电桩
				pile.pileName=item.coordinateName+pileData.pileCoordinateNumber;//充电桩名称
			}
		});
		pile.pileAddr=$('#area-list option:selected').text()+$('#coordinate-list option:selected').text();//充电桩地址
		console.log('地址'+$('#area-list option:selected').text()+$('#coordinate-list option:selected').text());
		var pileImg=$('#pile-img')[0].files[0];//获取图片文件流
		console.log(pileData);
		console.log(pileImg);
		console.log(pileData instanceof FormData);
		var formData=new FormData();//定义表单数据对象
		formData.append('pileStr',JSON.stringify(pile));//将pile进行转化
		formData.append('pileImg',pileImg);//添加图片数据到表单数据对象中
		formData.append("verifyCodeActual",verifyCodeActual);//添加验证码数据到表单数据对象中
		console.log('formdata:'+formData.get('pileStr'));
		
		//将数据发送到后端
		var registerPileUrl='/zhou/operator/registerpile';
		$.ajax({
			url:registerPileUrl,
			type:'POST',
			data:formData,
			contentType:false,
			processData:false,
			cache:false,//是否启用cache缓存
			success:function(data){
				if(data.success){//注册成功给出提示
					layer.msg('添加充电桩成功');
					//判断是继续添加，还是跳转到展示页面
					isAddPileIndex=layer.open({
						title:false,
						content:'添加充电桩成功，是否继续添加?',
						btn:['取消','继续添加'],
						yes:function(index,layero){//按钮一(取消按钮)
							//点击取消按钮，跳转到展示页面 TODO
						},
						btn2:function(index,layero){//按钮二(继续添加按钮)
							//刷新本页面，继续添加
							/*使用layui框架，在页面进行表单数据提交后,要使页面表单数据清空，可使用下面方法*/
							$("#add-pile-form")[0].reset();
							form.render();//重置表单
						}
					});
				}else{
					layer.msg('添加失败');
				}
			}
		});
		return false;//一定要加上，不然会直接进行页面刷新，传递的验证码就会不一致。
	});
	//监听取消按钮，一旦取消则返回到主页面
	$('#cancel-add').on('click',function(){
		window.location.href='/zhou/page/main';
	});
});