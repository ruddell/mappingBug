'use strict';

describe('Controller Tests', function() {

    describe('Application Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockApplication, MockCompany, MockCampaign;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockApplication = jasmine.createSpy('MockApplication');
            MockCompany = jasmine.createSpy('MockCompany');
            MockCampaign = jasmine.createSpy('MockCampaign');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Application': MockApplication,
                'Company': MockCompany,
                'Campaign': MockCampaign
            };
            createController = function() {
                $injector.get('$controller')("ApplicationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mappingBugApp:applicationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
