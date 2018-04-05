@ALL_TESTS @CSV_WRITER @WIP
Feature: CSV Writer


    Scenario: A cointracking transaction bean is passed to the CsvWriter and a CSV record is written to a file
        Given I have an individual cointracking transaction bean
        When I use the CSV Writer Service to save the cointracking transaction bean as a CSV record
        Then I see the cointracking transaction bean contents are written to a csv file


