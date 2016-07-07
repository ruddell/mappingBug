(function() {
    'use strict';

    angular
        .module('mappingBugApp')
        .controller('CampaignDetailController', CampaignDetailController);

    CampaignDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Campaign', 'Company', 'Beacon', 'Application'];

    function CampaignDetailController($scope, $rootScope, $stateParams, entity, Campaign, Company, Beacon, Application) {
        var vm = this;

        vm.campaign = entity;

        var unsubscribe = $rootScope.$on('mappingBugApp:campaignUpdate', function(event, result) {
            vm.campaign = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
