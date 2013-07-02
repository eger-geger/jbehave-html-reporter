var reportModule = angular.module("StepDocReport", ["ui.bootstrap"]);

reportModule.controller("StepDocController", function($scope, $http) {
    $scope.query = "";

    $scope.types = {
        showGiven: true,
        showWhen: true,
        showThen: true,

        isShown: function(item){
            return (this.showGiven && item.type.toLowerCase() === "given")
                || (this.showWhen && item.type.toLowerCase() === "when")
                || (this.showThen && item.type.toLowerCase() === "then");
        }
    };

    $scope.filterByType = function(item){
        return $scope.types.isShown(item);
    }

    $scope.pagination = {
        onPage: 12, size: 0, current: 1,

        getFirstOffset: function(){
            return this.getLastOffset() - this.onPage;
        },

        getLastOffset: function(){
            return this.onPage * this.current;
        },

        setSize: function(dataLength){
            this.size = 1 + dataLength / this.onPage;
        }
    };

    $scope.updateStepDocs = function() {
        $http.get("stepdocs.json").success(function(data){
            $scope.pagination.setSize(data.length);
            $scope.stepdocs = data.slice($scope.pagination.getFirstOffset(), $scope.pagination.getLastOffset());
        });
    }

    $scope.updateStepDocs();
});
