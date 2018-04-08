@ALL_TESTS @CONSOLIDATOR
Feature: Consolidator

    @WIP
    Scenario: Consolidate poloniex transactions into common contracking.info csv format
        Given I have access to the "POLONIEX" Consolidator Service
        When I use the "POLONIEX" Consolidator Service to consoliate
        Then I see the "POLONIEX" transactions consolidated into a csv file


    Scenario: Consolidate bittrex transactions into common contracking.info csv format
        Given I have access to the "BITTREX" Consolidator Service
        When I use the "BITTREX" Consolidator Service to consoliate
        Then I see the "BITTREX" transactions consolidated into a csv file


