// angular.module('PrinterApp')
// .controller('OrderCtrl', ['$scope','$rootScope','$state','OrderService', 
// function($scope,$rootScope,$state,OrderService){
// 	(new OrderService({'state':0})).$queryOrdersByState({key:0})
//     .then(function(resp){
//   	$scope.orders0=resp.data;
//     });
	
// 	(new OrderService()).$queryOrdersByState({key:1})
//     .then(function(resp){
//   	$scope.orders1=resp.data;
//     });
	
// 	(new OrderService()).$queryOrdersByState({key:2})
//     .then(function(resp){
//   	$scope.orders2=resp.data;
//     });
	
// 	(new OrderService()).$queryOrdersByState({key:3})
//     .then(function(resp){
//   	$scope.orders3=resp.data;
//     });
	
// 	(new OrderService()).$queryOrdersByState({key:4})
//     .then(function(resp){
//   	$scope.orders4=resp.data;
//     });
	
// 	$scope.toDtail=function(id){
// 		$state.go('index.detail', {orderID:id});
// 	}

// 	$scope.cancelOrder=function(index,id){
// 		(new OrderService()).$updateOrder({key1:0,key2:id}).then(function(resp){
// 			if(resp.data==1){
// 				alert('订单取消成功');
// 				$scope.orders0.splice(index,1);
// 			}
// 		});
// 	}
	
// }]);

//---------------------------------------用户获生产订单中心-----------------------------------//

angular.module('PrinterApp')
.controller('OrderCtrl', ['$scope','$rootScope','$state','OrderService', 
function($scope,$rootScope,$state,OrderService){

	(new OrderService()).$queryOrdersByState({key:0})
	.then(function(resp){
	  	$scope.orders=resp.data;
	});
	console.log($scope.orders);
	$scope.show=0;
	
	$scope.state=[
			{name:'待确认',value:'0'},
			{name:'待付款',value:'1'},
			{name:'待生产',value:'2'},
			{name:'待发货',value:'3'}
	];
	
	function refresh(){
		(new OrderService()).$queryOrdersByState({key:0})
		.then(function(resp){
		  	$scope.orders=resp.data;
		});
		$scope.show=0;
	}
	
	//控制样式、选择订单状态
    $scope.select=function(index){
    	$scope.show=index;
    	console.log(index);
    	(new OrderService()).$queryOrdersByState({key:index})
		.then(function(resp){
		  	$scope.orders=resp.data;
		});
    };
	
    //跳转订单详情页
	$scope.toDtail=function(id){
		$state.go('index.detail', {orderID:id});
	};

	//取消订单
	$scope.cancelOrder=function(index,id){
		(new OrderService()).$updateOrder({key1:0,key2:id}).then(function(resp){
			if(resp.state){
				alert('订单付款成功');
				$scope.orders.splice(index,1);
				refresh();
			}
		});
	};
	
	//支付订单
	$scope.payOrder=function(index,id){
		(new OrderService()).$updateOrder({key1:4,key2:id}).then(function(resp){
			if(resp.data==1){
				alert('订单支付成功');
				refresh();
			}
		});
	};
	
}]);

//----------------------------------------设计订单列表---------------------------------------//
angular.module('PrinterApp')
.controller('UserDOrderCtrl', ['$scope','$rootScope','$state','OrderService', 
function($scope,$rootScope,$state,OrderService){

	//默认
	(new OrderService({
		'userName':$rootScope.currentUser.userName,
		'state':'待确认'
	})).$queryDesignOrders()
	.then(function(resp){
	  	$scope.orders=resp.data;
	});
	$scope.show=0;
	
	$scope.state=[
			{name:'待确认',value:'0'},
			{name:'待报价',value:'1'},
			{name:'待付款',value:'2'},
			{name:'待设计',value:'3'},
			{name:'已设计',value:'4'}
	];
	
    $scope.select=function(index,state){
    	
    	$scope.show=index;
    	console.log(index);
    	(new OrderService({
    		'userName':$rootScope.currentUser.userName,
    		'state':state.name
    	})).$queryDesignOrders()
		.then(function(resp){
		  	$scope.orders=resp.data;
		});
    };
	
	$scope.toDtail=function(id){
		$state.go('index.dodetail', {orderid:id});
	};

	//取消设计订单
	$scope.cancelOrder=function(index,id){
		var check=confirm('确定要取消该订单吗？');
		if(check){
			(new OrderService({
				'dorderID':id,
				'state':'已取消'
			})).$userUpdateOrder().then(function(resp){
				if(resp.state){
					alert('订单取消成功');
					$scope.orders.splice(index,1);
					$rootScope.back();
				}
			});
		}
		
	};
	
	//设计订单付款
//	$scope.payOrder=function(index,id){
//		(new OrderService({
//			'dorderID':id,
//			'state':'待设计'
//		})).$userUpdateOrder().then(function(resp){
//			if(resp.state){
//				alert('订单付款成功');
//				$scope.orders.splice(index,1);
//			}
//		});
//	};
	
}]);

/*------------------------- 用户确认生产订单收货信息 ------------------------------*/
angular.module('PrinterApp')
.controller('OrderCtrl1', ['$scope','$state', 'AdminService','OrderService','OrderItemService','CartService',
function($scope,$state,AdminService,OrderService,OrderItemService,CartService){

	(new AdminService()).$queryDesigners().then(function(resp){
		$scope.designers = resp.data;
	}); 
	(new CartService()).$queryCarts().then(function(resp){
		$scope.items=resp.data;
	});

	if(sessionStorage.getItem("OrderItem")!=undefined)
		$scope.order = JSON.parse(sessionStorage.getItem("OrderItem"));
	$scope.user = JSON.parse(sessionStorage.getItem("User"));

	$scope.putOrder=function(){
		if($scope.user.realName==null || $scope.user.realName==undefined)
			alert("请填写收件人");
		else if($scope.user.phone==null || $scope.user.phone==undefined)
			alert("请填写联系电话");
		else if($scope.user.postCode==null || $scope.user.postCode==undefined)
			alert("请填写邮编");
		else if($scope.user.address==null || $scope.user.address==undefined)
			alert("请填写收件地址");
		else if($scope.chooseadID==null || $scope.chooseadID==undefined)
			alert("请选择设计师");
		else if($scope.order!=null && $scope.order!=undefined){
			putOneItem();
			console.log("order is not null");
		}
		else{
			putCartToOrder();
			console.log("order is null");
		}
	};
	
	function putCartToOrder(){		
		var count =0;
		(new OrderService({
			'userName':$scope.user.userName,
			'receiver':$scope.user.realName,
			'phone':$scope.user.phone,
			'postCode':$scope.user.postCode,
			'direction':$scope.user.address,
			'remark':$scope.remark,
			'adID':$scope.chooseadID.adID
		})).$addOrder().then(function(resp){
			if(resp.state){ 
				$scope.orderid=resp.orderid; 
				console.log("orderid1:"+$scope.orderid);
				for(var i=0;i<$scope.items.length;i++){
					(new OrderItemService({
						'orderID':$scope.orderid,
						'modelID':$scope.items[i].modelID,
						'material':$scope.items[i].material,
						'size':$scope.items[i].size,
						'quantity':$scope.items[i].quantity,
						'color':$scope.items[i].color
					})).$addItem().then(function(r){
						if(r.state)
						{	console.log(r.msg);
							count++;
							if(count==$scope.items.length){
								alert('下单成功！');
								$state.go('index.order');
							}
						}
					});
				}
			}
		});
		
			

	}
	
	function putOneItem(){
		(new OrderService({
			'userName':$scope.user.userName,
			'receiver':$scope.user.realName,
			'phone':$scope.user.phone,
			'postCode':$scope.user.postCode,
			'direction':$scope.user.address,
			'remark':$scope.remark,
			'adID':$scope.chooseadID.adID
		})).$addOrder().then(function(resp){
			if(resp.state){
				(new OrderItemService({
					'orderID':resp.orderid,
					'modelID':$scope.order.modelID,
					'material':$scope.order.material,
					'size':$scope.order.size,
					'quantity':$scope.order.quantity
				})).$addItem().then(function(resp){
					if(resp.state)
					{	alert('下单成功');
						sessionStorage.removeItem("OrderItem");
						$state.go('index.order');
					}
				});
			}else
				alert(resp.msg);
		});
	}

}]);





