/*------------------------- 用户提交反馈 -----------------------------*/
angular.module("PrinterApp")
.controller('FeedBackCtrl', ['$scope', '$rootScope','FeedBackService',
function($scope,$rootScope,FeedBackService){
	(new FeedBackService()).$queryFeedBack({key:$rootScope.currentUser.userName})
	.then(function(resp){
		$scope.question=resp.data1;
		$scope.reply=resp.data2;
		console.log(resp.data1);
	});

	//提交反馈
	$scope.subfeedback=function(){
		var state;
		(new FeedBackService({
			'feedBack':$scope.content,
			'userName':$rootScope.currentUser.userName
		})).$addFeedBack().then(function(resp){
			alert(resp.msg);
			state=resp.state;
		});

		if(state){
			(new FeedBackService()).$queryFeedBack({key:$rootScope.currentUser.userName})
			.then(function(resp){
				$scope.question=resp.data1;
				$scope.reply=resp.data2;
			});
		}
	}

	//
	$scope.showfeed=function(f){
		$scope.feed={};
		$scope.feed=f;
	}

}]);

/*-------------------------------- 管理员查看反馈列表 -----------------------------*/
angular.module('PrinterApp')
.controller('FBListCtrl', ['$scope','$state','FeedBackService', 
function($scope,$state,FeedBackService){

    $scope.selectedState={
    		value:''
    };

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
        console.log(pagedData);
        $scope.myData = pagedData;
        $scope.totalServerItems = data.length;
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };

    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
       setTimeout(function () {
           var data;
           if (searchText) {
               var ft = searchText.toLowerCase();
               (new FeedBackService()).$queryAllFeedBack().then(function(resp){
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
               (new FeedBackService()).$queryAllFeedBack().then(function(resp){
                    $scope.setPagingData(resp.data,page,pageSize); 
                    console.log(resp.data);
                });
           }
       }, 100);
   };

    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
          $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    $scope.$watch('filterOptions', function (newVal, oldVal) {
       if (newVal !== oldVal) {
         $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
       }
   }, true);

    $scope.selectedRows=[];

    $scope.gridOptions = {
        data: 'myData',
        columnDefs: [
            {field:'feedBack',width:300, displayName:'内容',enableCellEdit: false}, 
            {field:'userName',displayName:'用户',enableCellEdit: false},
            {field:'submitDate', displayName:'时间',enableCellEdit: false,cellFilter:'date:"yyyy-MM-dd HH:mm:ss"'},
            {field:'state', displayName:'状态',enableCellEdit: false},
            {field: "", enableCellEdit: false,  
                    cellTemplate: '<button type="button" class="btn btn-edit"  ng-click="seeFeedBack()" >查看</button>'
            }
        ],
        showSelectionCheckbox: true,  
        enableRowSelection: true,
        selectedItems:$scope.selectedRows,
        multiSelect:true, 
        enablePaging: true, 
        pagingOptions: $scope.pagingOptions, 
        filterOptions: $scope.filterOptions,
        showFooter: true,  
        enableCellSelection: true
    };

    //查看案例
    $scope.seeFeedBack=function(){
        var id=$scope.selectedRows[0].fbID;
        $state.go('admin.feedback2',{id:id});
    };

    $scope.search=function(){ 
        $scope.filterOptions.filterText=$scope.selectedState.value;
    };
    
}]);

/*-------------------------------- 反馈详情 --------------------------*/
angular.module('PrinterApp')
.controller('FBDetailCtrl', ['$scope','$rootScope','$stateParams','FeedBackService', 
function($scope,$rootScope,$stateParams,FeedBackService){
	var id = $stateParams.id;
	(new FeedBackService()).$queryOneFeedBack({key:id}).then(function(resp){
		$scope.feed=resp.data;
	});
	

	$scope.replyFeedBack=function(){
		if($scope.feed.reply!=null && $scope.feed.reply!=undefined){
			(new FeedBackService({
				'fbID':id,
				'reply':$scope.feed.reply,
				'replyer':$rootScope.currentAdmin.adName
			})).$updateFeedBack().then(function(resp){
				alert(resp.msg);
			});
		}else{
			alert('请输入回复内容');
		}
	};
}]);