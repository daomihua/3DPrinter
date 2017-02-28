angular.module('PrinterApp')
.controller('UserCaseCtrl', ['$scope','$stateParams','CaseService' ,
function($scope,$stateParams,CaseService){
	console.log($stateParams.id+'---'+$stateParams.name);
	var id=$stateParams.id;
	var name=$stateParams.name;
	$scope.category=$stateParams.name;
//	if((name==null || name==undefined) && (id==null || id==undefined)){
		(new CaseService()).$queryAllCases()
		.then(function(resp){
			$scope.cases1=resp.category1;
			$scope.cases2=resp.category2;
			$scope.cases3=resp.category3;
			$scope.cases4=resp.category4;
			$scope.cases5=resp.category5;
			$scope.cases6=resp.category6;
			$scope.cases7=resp.category7;
			$scope.cases8=resp.category8;
			console.log($scope.cases8);
		});
//	}else{
//		(new CaseService()).$queryCases({key:$stateParams.name})
//		.then(function(resp){
//			$scope.cases=resp.data;
//			console.log($scope.cases);
//		});
//	}

}]);

angular.module('PrinterApp')
.controller('CaseContentCtrl', ['$scope','$stateParams','CaseService' ,
function($scope,$stateParams,CaseService){
	$scope.onecase='<h2>aaaaa</h2>';
	var caseID=$stateParams.caseID;
	(new CaseService()).$queryCase({key:caseID})
	.then(function(resp){
		$scope.onecase=resp.data;
	});
}]);
