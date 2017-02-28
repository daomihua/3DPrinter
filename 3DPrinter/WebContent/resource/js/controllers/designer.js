/*----------------------------- 设计师查看生产订单列表 -------------------------*/
angular.module('PrinterApp')
.controller('DesignCtrl', ['$scope','$rootScope','$state','OrderService' ,'OrderItemService',
function($scope,$rootScope,$state,OrderService,OrderItemService){

	var designer_id = $rootScope.currentAdmin.adID;
	var state = 0;
	$scope.item_id;
	$scope.change=false;

	$scope.filterOptions = {
		filterText: "",
		useExternalFilter: true
	};

	$scope.totalServerItems = 0;
    $scope.pagingOptions = {
        pageSizes: [10, 20, 30],
        pageSize: 10,
        currentPage: 1
    };  
    $scope.setPagingData = function(data, page, pageSize){  
        var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
        $scope.myData = pagedData;
        $scope.totalServerItems = data.length;
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };

    $scope.getPagedDataAsync = function (pageSize, page,state,searchText) {
       setTimeout(function () {
           var data;
           if (searchText) {
               var ft = searchText.toLowerCase();
               (new OrderService()).$designerOrder({key1:designer_id,key2:state}).then(function(resp){
                    if(resp.state){
                        data = resp.data.filter(function(item) {
                           return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                        });
                        $scope.setPagingData(data,page,pageSize); 
                    }else{
                        console.log('get data error');
                    }   
                });           
           } else {
        	   (new OrderService(
                  	// 'state':state
                  )).$designerOrder({key1:designer_id,key2:state}).then(function(resp){
                       $scope.setPagingData(resp.data,page,pageSize);  
                   });
           }
       }, 100);
   };

   $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,state,$scope.filterOptions.filterText);

    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
          $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,state,$scope.filterOptions.filterText);
        }
    }, true);
    
    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal !== oldVal) {
          $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, state ,$scope.filterOptions.filterText);
        }
    }, true);

    $scope.selectedRows=[];
	$scope.gridOptions = {
        data: 'myData',
       	columnDefs: [
	     	{field:'orderID', displayName:'订单编号'},
	     	{field:'userName', displayName:'用户'}, 
	     	{field:'orderState', displayName:'状态'},
	     	{field:'orderDate',displayName:'下单时间',cellFilter:'date:"yyyy-MM-dd HH:mm:ss"'},
	     	{field:'remark', displayName:'备注'},
	     	{field: "", 
	           		cellTemplate: '<button type="button" class="btn btn-edit"  ng-click="toOrderDetail()" >查看</button>'+
	           						'<button type="button" class="btn btn-edit" ng-click="changeDetail()" >修改</button>'
	         }
	    ],
	    showSelectionCheckbox: true, 
        enableRowSelection: true,
        selectedItems:$scope.selectedRows,//$scope.selectedRows
        multiSelect:true,
        enablePaging: true, 
        pagingOptions: $scope.pagingOptions, 
        filterOptions: $scope.filterOptions,
        showFooter: true,  
    };
	
	//修改查询订单的状态为待发货
	$scope.changeState=function(s){
		state = s;
		$scope.change=s==1?true:false;
		if(s==3)
			alert("记得及时制作商品哟~");
		$scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, state ,$scope.filterOptions.filterText);
	};
	
    //查看订单详情
    $scope.toOrderDetail=function(){
    	var id=$scope.selectedRows[0].orderID;
    	$state.go('admin.orderdetail',{orderID:id});
    };

    //跳转修改订单详情
    $scope.changeDetail=function(){
    	var id=$scope.selectedRows[0].orderID;
    	$state.go('admin.d-detail',{orderID:id});
    };
	
}]);

/*----------------------- 设计师查看、修改生产订单并确认 ---------------------*/
angular.module('PrinterApp')
.controller('DdetailCtrl', ['$scope','$rootScope','$state','$stateParams','OrderService', 'OrderItemService','ModelService',
function($scope,$rootScope,$state,$stateParams,OrderService,OrderItemService,ModelService){
	
  var order_id = $stateParams.orderID;
  $scope.color={value:''};
  $scope.ma={};
  
  (new OrderService({
    'orderID':order_id
  })).$queryOrder({key:order_id}).then(function(resp){
    $scope.order = resp.data;
    $scope.ordernum=$scope.order.items.length;
    console.log(resp.msg);
    console.log($scope.ordernum);
  });
  
  (new ModelService()).$queryMaterial().then(function(resp){
		$scope.material = resp.data;
	});
  	
  $scope.editeOrder=false;
  
  //修改订单需求
  $scope.changeOrder=function(){
	  if($scope.order.receiver==null || $scope.order.phone==null || 
			  $scope.order.postCode==null || $scope.order.direction==null){
		  alert('请完善收件信息');
	  }else{
		  (new OrderService({
			  'orderID':$scope.order.orderID,
			  'receiver':$scope.order.receiver,
			  'phone':$scope.order.phone,
			  'postCode':$scope.order.postCode,
			  'direction':$scope.order.direction
		  }
		  )).$changeOrder().then(function(resp){
			  alert(resp.msg);
			  if(resp.state)
				  $scope.order.$editing=false;
		  });
	  }
  };

  $scope.changeItem=function(){
	  console.log($scope.ma);

	  var size=$scope.citem.size;
	  var s=size.split('×');
	  var f=true;
	  if(s.length!=3){
		  alert("请输入正确的型号格式");
		  f=false;
	  }else{
		  for(var i=0;i<3;i++){
			  if(isNaN(s[i])){
				  f=false;
				  alert("请输入正确的型号格式");
				  break;
			  }
		  }
	  }
	  if(!f){
		  item.size=size;
	  }else if($scope.ma==null || $scope.ma==undefined){
		  alert("请选择材料");
	  }else if($scope.color.value=='' || $scope.color.value==undefined){
		  alert("请选择颜色");
	  }else if(isNaN($scope.citem.quantity)){
		  alert("请输入正确的数量");
	  }else{
		   (new OrderItemService({
			   'orderID':$scope.citem.orderID,
			   'modelID':$scope.citem.modelID,
			   'quantity':$scope.citem.quantity,
			   'color':$scope.color.value,
			   'material':$scope.ma.materialName,
			   'size':$scope.citem.size
		   })).$updateItem().then(function(resp){
				   alert(resp.msg);
		   });  
	  }
  };

  $scope.editeItem=function(item){
  	  $scope.citem=item;
	  $scope.ma.materialName=item.material;
	  console.log($scope.ma);
	  console.log($scope.citem.material);
	  $scope.color.value=item.color;
  };
  
  //确认订单
  $scope.checkOrder=function(){
	  var check=confirm('要确认此订单吗？');
	  if(check){
		  //状态改为待付款  2
		  (new OrderService()).$updateOrder({key1:2,key2:$scope.order.orderID})
		  .then(function(resp){
			  alert(resp.msg);
			  $state.go('admin.designer');
		  });
	  }
  };
  
  //生产订单
  $scope.makeProduct=function(){
	  var check=confirm('要确认生产此订单吗？');
	  if(check){
		  //订单状态改为待发货  5
		  (new OrderService()).$updateOrder({key1:5,key2:$scope.order.orderID})
		  .then(function(resp){
			  alert(resp.msg);
			  $state.go('admin.designer');
		  });
	  }
  };
  
  //确认发货
  $scope.deliver=function(){
	  var check=confirm('要确认发货吗？');
	  if(check){
		  //订单状态改为待收货  6
		  (new OrderService()).$updateOrder({key1:6,key2:$scope.order.orderID})
		  .then(function(resp){
			  alert(resp.msg);
			  $state.go('admin.designer');
		  });
	  }
  };
  
  //设计师发布产品信息
  $scope.itemImg='image';
  $rootScope.item_id;
  $rootScope.model_id;
  $scope.upload=function(item){
	  $rootScope.item_id=item.orderID;
	  $rootScope.model_id=item.modelID;
	};

}]);

//设计师上传个人成品
angular.module('PrinterApp')
.controller('ItemUploadCtrl',
function($scope,$rootScope,$http,baneFileFactory,OrderItemService){
	var type=[".jpg",".jpge",".png",".gif"];
	$scope.imgType=type;
	$scope.data={"a":"1","b":"2"};
	$scope.onSelect=function(files,error,dataForm){
		console.log(dataForm[0]);
		baneFileFactory.uploadDataForm("rest/orderitem/upload",dataForm[0]).then(function(resp){
			alert(resp.data.msg);
			$rootScope.itemImg = resp.data.url;
			console.log($rootScope.itemImg);
		});
	};
	
	  $scope.setTitle=function(){
		  console.log($rootScope.item_id);
			console.log($rootScope.itemImg);
			 (new OrderItemService({
			 	'orderID':$rootScope.item_id,
			 	'modelID':$rootScope.model_id,
			 	'image':$rootScope.itemImg,
			 	'title':$scope.title
			 })).$uploadItem().then(function(resp){
			 	alert(resp.msg);
			 });
		};


});

angular.module('PrinterApp')
.controller('ItemCtrl',['$scope','OrderItemService',
function($scope,OrderItemService){
	$scope.itemImg;
	$scope.setTitle=function(item){
		console.log($scope.itemImg);
		item.image=$scope.itemImg;
		 (new OrderItemService({
		 	'orderID':item.orderID,
		 	'modelID':item.modelID,
		 	'image':$scope.itemImg,
		 	'title':$scope.title
		 })).$uploadItem().then(function(resp){
		 	alert(resp.msg);
		 });
	};
}]);

