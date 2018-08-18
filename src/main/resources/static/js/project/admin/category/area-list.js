/**
 * Created by Admin on 8/13/2018.
 */
app.controller('qmsCtrl',['$scope','$http','$filter','$window','$timeout','$q'
    ,function ($scope,$http,$filter,$window,$timeout,$q) {
        $scope.dataLoaded=false;
        $scope.page=page;
        $http.get(preUrl+"/admin/category/area/page")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.page=response.data;
                    $scope.page.pageCount=getPageCount($scope.page);
                    $scope.page.pageList=getPageList($scope.page);
                }
            });

        /*----------------------------------------------------------------------------------*/

        $scope.loadPage=function (pageNumber) {
            if(pageNumber>=1){
                $http.get(preUrl+"/admin/category/area/page", {params: {p:pageNumber,name:$scope.name}})
                    .then(function (response) {
                        $scope.page=response.data;
                        $scope.page.pageList=getPageList($scope.page);
                        $scope.page.pageCount=getPageCount($scope.page);
                    });
            }
        };

    }]);