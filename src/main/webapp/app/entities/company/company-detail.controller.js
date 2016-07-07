(function() {
    'use strict';

    angular
        .module('mappingBugApp')
        .controller('CompanyDetailController', CompanyDetailController);

    CompanyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Company', 'User'];

    function CompanyDetailController($scope, $rootScope, $stateParams, entity, Company, User) {
        var vm = this;

        vm.company = entity;

        var unsubscribe = $rootScope.$on('mappingBugApp:companyUpdate', function(event, result) {
            vm.company = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
