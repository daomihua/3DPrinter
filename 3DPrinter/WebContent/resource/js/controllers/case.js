/*------------------------- 管理成功案例 ---------------------*/
angular.module('PrinterApp')
.controller('CaseCtrl', ['$scope','$state','CaseService', 
function($scope,$state,CaseService){

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
               (new CaseService()).$queryCaseList().then(function(resp){
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
               (new CaseService()).$queryCaseList().then(function(resp){
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
            {field:'caseTitle',width:300, displayName:'标题',enableCellEdit: false}, 
            {field:'publisher',displayName:'发布者',enableCellEdit: false},
            {field:'category', displayName:'类别',enableCellEdit: false},
            {field:'state', displayName:'状态',enableCellEdit: false},
            {field:'publishTime',displayName:'发布时间',enableCellEdit: false},
            {field: "", enableCellEdit: false,  
                    cellTemplate: '<button type="button" class="btn btn-edit"  ng-click="seeCase()" >查看</button>' + 
                    '<button type="button" class="btn btn-edit"  ng-click="deleteCase()" >删除</button>'
            }
        ],
        showSelectionCheckbox: true,  
        enableRowSelection: true,
        selectedItems:$scope.selectedRows,//$scope.selectedRows
        multiSelect:true,
        // enablePinning: true,  
        enablePaging: true, 
        pagingOptions: $scope.pagingOptions, 
        filterOptions: $scope.filterOptions,
        showFooter: true,  
        // enableColumnResize: false,  
        enableCellSelection: true
        // enableCellEditOnFocus: true
    };

    //删除一条数据
    $scope.deleteCase=function(){
    	var check=confirm('确定要删除该案例吗？');
    	if(check){
	        (new CaseService()).$deleteCase({ key:$scope.selectedRows[0].caseID })
	        .then(function(resp){
	                alert(resp.msg);
	                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
	        });
    	}
    };

    //删除所选数据
    $scope.deleteCases = function(){
    	var check=confirm('确定要删除所选案例吗？');
    	if(check){
	        var count=$scope.selectedRows.length;
	        for (var i = $scope.selectedRows.length - 1; i >= 0; i--) {
	            console.log($scope.selectedRows[i].caseID);
	            (new CaseService()).$deleteCase({ key:$scope.selectedRows[i].caseID })
	            .then(function(resp){
	                if(resp.state){
	                    count--;
	                }
	            });
	        }
	        if( count==0 ){
	            alert("案例删除成功!");
	            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
	        }
        }
    };

    //查看案例
    $scope.seeCase=function(){
        var id=$scope.selectedRows[0].caseID;
        $state.go('admin.c-content',{caseID:id});
    };

    $scope.search=function(){ 
        $scope.filterOptions.filterText=$scope.selectedState.value;
    };
    
}]);

/*------------------------------ 案例内容 ------------------------*/
angular.module('PrinterApp')
.controller('ContentCtrl', ['$scope','$rootScope', '$stateParams','CaseService',
function($scope,$rootScope,$stateParams,CaseService){
    var caseID=$stateParams.caseID;
    (new CaseService()).$queryACase({key:caseID}).then(function(resp){
        $scope.onecase=resp.data;
    });

    $scope.auditCase=function(){
        (new CaseService()).$auditCase({key:caseID}).then(function(resp){
            alert(resp.msg);
        });
    };
}]);