
app.controller('qmsCtrl',['$scope','$http'
    ,function ($scope,$http) {
        $scope.name="";
        $scope.dataLoaded=false;
        $scope.page=page;
        $http.get(preUrl+"/admin/reception/list",{params:{name:$scope.name}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.page=response.data;
                    $scope.page.pageCount=getPageCount($scope.page);
                    $scope.page.pageList=getPageList($scope.page);
                }
            });

        $scope.search=function () {
            $scope.page=page;
            $http.get(preUrl+"/admin/reception/list",{params:{name:$scope.name}})
                .then(function (response) {
                    if(response!=null && response!='undefined' && response.status==200){
                        $scope.page=response.data;
                        $scope.page.pageCount=getPageCount($scope.page);
                        $scope.page.pageList=getPageList($scope.page);
                    }
                });
        };

        /*----------------------------------------------------------------------------------*/

        $scope.loadPage=function (pageNumber) {
            if(pageNumber>=1){
                $http.get(preUrl+"/admin/reception/list", {params: {p:pageNumber}})
                    .then(function (response) {
                        $scope.page=response.data;
                        $scope.page.pageList=getPageList($scope.page);
                        $scope.page.pageCount=getPageCount($scope.page);
                    });
            }
        };



    }]);