angular.module("PrinterApp")
.controller('FeedBackCtrl', ['$scope', '$rootScope','FeedBackService',
function($scope,$rootScope,FeedBackService){
	(new FeedBackService()).$queryFeedBacks().then(function(resp){
		$scope.question=resp.data1;
		$scope.reply=resp.data2;
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
			(new FeedBackService()).$queryFeedBacks().then(function(resp){
				$scope.question=resp.data1;
				$scope.reply=resp.data2;
			});
		}
	}

}]);