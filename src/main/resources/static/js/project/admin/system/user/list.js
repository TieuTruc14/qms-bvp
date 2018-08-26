
app.controller('qmsCtrl',['$scope','$http',function ($scope,$http) {
        $scope.username="";
        $scope.dataLoaded=false;
        $scope.items="";
        $http.get(preUrl+"/admin/system/user/list")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.page=response.data;
                    $scope.page.pageList=getPageList($scope.page);
                    $scope.page.pageCount=getPageCount($scope.page);
                }
            });

        $scope.loadPage=function (pageNumber) {
            if(pageNumber>=1){
                $http.get(preUrl+"/admin/system/user/list", {params: {p:pageNumber,username:$scope.username}})
                    .then(function (response) {
                        $scope.page=response.data;
                        $scope.page.pageList=getPageList($scope.page);
                        $scope.page.pageCount=getPageCount($scope.page);
                    });
            }
        };
        // $scope.reload=function () {
        //     $http.get(preUrl+"/admin/management/generality/list-door")
        //         .then(function (response) {
        //             if(response!=null && response!='undefined' && response.status==200){
        //                 $scope.items=response.data;
        //             }
        //         });
        // }
    }]);