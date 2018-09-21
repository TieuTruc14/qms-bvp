
app.controller('qmsCtrl',['$scope','$http'
    ,function ($scope,$http) {
        $scope.name="";
        $scope.dataLoaded=false;
        $scope.page=page;
        $http.get(preUrl+"/admin/management/reception/list")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.page=response.data;
                    $scope.page.pageCount=getPageCount($scope.page);
                    $scope.page.pageList=getPageList($scope.page);
                }
            });

        $scope.search=function () {
            if(!$scope.validate()){
                return;
            }
            $scope.page=page;
            $http.get(preUrl+"/admin/management/reception/list",{params:{code:$scope.code,from:$scope.from,to:$scope.to}})
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
            if(pageNumber > 0){
                if(!$scope.validate()){
                    return;
                }
                $http.get(preUrl+"/admin/management/reception/list", {params: {p:pageNumber}})
                    .then(function (response) {
                        $scope.page=response.data;
                        $scope.page.pageList=getPageList($scope.page);
                        $scope.page.pageCount=getPageCount($scope.page);
                    });
            }
        };

        $scope.validate=function () {
            $scope.errorDateFrom="";
            $scope.errorDateTo="";
            if($scope.from!=null && $scope.from!='undefined' && $scope.from.length>0){
                if(formatDate($scope.from)==null){
                    $scope.errorDateFrom="Nhập đúng định dạng dd/MM/yyyy";
                    return false;
                }
            }
            if($scope.to!=null && $scope.to!='underfined' && $scope.to.length>0){
                if(formatDate($scope.to)==null){
                    $scope.errorDateTo="Nhập đúng định dạng dd/MM/yyyy";
                    return false;
                }
            }

            return true;
        };


        $('#from').datepicker().on('changeDate', function (ev) {
            $scope.from=this.value;
        });
        $('#to').datepicker().on('changeDate', function (ev) {
            $scope.to=this.value;
        });
        $scope.clear=function () {
            $scope.username="";
            $scope.from="";
            $scope.to="";
            $scope.msisdn="";
        };



    }]);