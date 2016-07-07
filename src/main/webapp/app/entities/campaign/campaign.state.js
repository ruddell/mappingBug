(function() {
    'use strict';

    angular
        .module('mappingBugApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('campaign', {
            parent: 'entity',
            url: '/campaign',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Campaigns'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/campaign/campaigns.html',
                    controller: 'CampaignController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('campaign-detail', {
            parent: 'entity',
            url: '/campaign/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Campaign'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/campaign/campaign-detail.html',
                    controller: 'CampaignDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Campaign', function($stateParams, Campaign) {
                    return Campaign.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('campaign.new', {
            parent: 'campaign',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/campaign/campaign-dialog.html',
                    controller: 'CampaignDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('campaign', null, { reload: true });
                }, function() {
                    $state.go('campaign');
                });
            }]
        })
        .state('campaign.edit', {
            parent: 'campaign',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/campaign/campaign-dialog.html',
                    controller: 'CampaignDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Campaign', function(Campaign) {
                            return Campaign.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('campaign', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('campaign.delete', {
            parent: 'campaign',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/campaign/campaign-delete-dialog.html',
                    controller: 'CampaignDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Campaign', function(Campaign) {
                            return Campaign.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('campaign', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
