(function() {
    'use strict';

    angular
        .module('mappingBugApp')
        .controller('CampaignDeleteController',CampaignDeleteController);

    CampaignDeleteController.$inject = ['$uibModalInstance', 'entity', 'Campaign'];

    function CampaignDeleteController($uibModalInstance, entity, Campaign) {
        var vm = this;

        vm.campaign = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Campaign.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
