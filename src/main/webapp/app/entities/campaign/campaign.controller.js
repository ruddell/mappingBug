(function() {
    'use strict';

    angular
        .module('mappingBugApp')
        .controller('CampaignController', CampaignController);

    CampaignController.$inject = ['$scope', '$state', 'Campaign'];

    function CampaignController ($scope, $state, Campaign) {
        var vm = this;
        
        vm.campaigns = [];

        loadAll();

        function loadAll() {
            Campaign.query(function(result) {
                vm.campaigns = result;
            });
        }
    }
})();
