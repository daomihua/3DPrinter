/*------------------------- 购物车管理 -----------------------------*/
angular.module('PrinterApp')
.controller('CartCtrl',['$scope','$rootScope','$state','CartService',
function($scope,$rootScope,$state,CartService){
	
	$scope.cartlist=[];
	if($rootScope.isLogin){
		getCarts();
	}

	function getCarts(){
		(new CartService()).$queryCarts().then(function(resp){
			$scope.cartlist = resp.data;
			console.log(resp.data);
			if($scope.cartlist.length==0)
				$scope.show=false;
			else
				$scope.show=true;
		});
	}

	//删除购物车
	$scope.deleteCart=function(index,id){
		(new CartService({
			'modelID':id
		})).$deleteCart({key:id}).then(function(resp){
			console.log(resp.msg);
			if(resp.state){
				$scope.cartlist.splice(index,1);
			}
		});
	};
	
	$scope.changeNum=function(id,num){
		(new CartService({
			'modelID':id,
			'quantity':num
		})).$updateCart().then(function(resp){ console.log(resp.msg);});
	};
	
	$scope.checkInfo=function(){
		$state.go('index.deliver');
	};

}]);

//----------------------------上传模型文件 && 添加购物车--------------------------*/
angular.module('PrinterApp')
.controller('PrintCtrl', ['$scope','$rootScope','$state','ModelService', 'CartService',
function($scope,$rootScope,$state,ModelService,CartService){

	(new ModelService()).$queryMaterial().then(function(resp){
		$scope.material = resp.data;
	});

	$scope.onemodel={};
	$scope.oders=[];
	$scope.order={};
	
	$scope.color = {
			value: '原色',
			text:'原色'
		   };

	var user = JSON.parse(sessionStorage.getItem("User"));

	$scope.addToCart=function(){
		if(!$rootScope.isLogin){
			alert("您还没有登录哦~");
			return;
		}
		$scope.order.size=$scope.length+'×'+$scope.width+'×'+$scope.height;
		if($scope.order.modelID==null || $scope.order.modelID==undefined){
			alert("请上传模型文件");
		}else if($scope.order.size==null || $scope.order.size==undefined){
			alert("请设置模型大小");
		}else if($scope.choosematerial==null || $scope.choosematerial==undefined){
			alert("请选择材料");
		}else if($scope.color==null || $scope.color==undefined){
			alert("请选择材料");
		}else{
			$scope.order.material=$scope.choosematerial.materialName;
//			console.log($scope.choosematerial);
			$scope.order.quantity=1;
			(new CartService({
				'modelID':$scope.order.modelID,
				'material':$scope.choosematerial.materialName,
				'size':$scope.order.size,
				'quantity':$scope.order.quantity,
				'color':$scope.color.value
			})).$addCart().then(function(resp){
				alert(resp.msg);
				if(resp.state)
					getCartNum();
			});
		}
	}
	
	//更新购物车数据
	function getCartNum(){
		(new CartService()).$queryCartNum().then(function(resp){
			sessionStorage.setItem('Carts',resp.data);
			$rootScope.cartNum=resp.data;
		});
	}

	$scope.putOrder=function(){
		if(!$rootScope.isLogin){
			alert("您还没有登录哦~");
			return;
		}
		$scope.order.material=$scope.choosematerial.materialName;
		$scope.order.size=$scope.length+'×'+$scope.width+'×'+$scope.height;
		$scope.order.quantity=1;
		
		if($scope.order.modelID==null || $scope.order.modelID==undefined){
			alert("请上传模型文件");
		}else if($scope.order.size==null || $scope.order.size==undefined){
			alert("请设置模型大小");
		}else if($scope.order.material==null || $scope.order.material==undefined){
			alert("请选择材料");
		}else{
			sessionStorage.setItem("OrderItem",JSON.stringify($scope.order));
			console.log(JSON.stringify($scope.order));
			$state.go('index.deliver');
		}
	}

}]);


angular.module("PrinterApp")
	.controller('UploadCtrl',function($scope,$http,baneFileFactory){
		    var type=[".stl",".obj",".igs",".dxf",".vrml"];
			$scope.imgType=type;
			$scope.data={"a":"1","b":"2"};
			$scope.onSelect=function(files,error,dataForm){
				console.log(dataForm[0]);
				baneFileFactory.uploadDataForm("rest/models/add",dataForm[0]).then(function(resp){
					alert(resp.data.msg);
					$scope.order.modelID = resp.data.modelid;
					console.log(resp.data.msg);
				});
			};
			
			
		});
	
