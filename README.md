# SeleniumTests
Selenium testing for agile warriors

# Dependencies
User must install selenium.

# How to Run
In the src folder, the user can find Junit tests to run on the website. The tests will record their results in the logs folder.
AbstractPageTest contains all universal tests. Each individual page has a class that extends the AbstractPageTest class.

# Adding new universal tests
Go to AbstractPageTest and add a new @Test unit test

# Adding page specific tests on existing page
Go to that page's test class and add a new @Test unit test

# Adding tests on a new page.
Create a new class for the webpage you want to test and it must extend AbstractPageTest.
Add the method beforeClassSetPageSpecifics() as a Junit @BeforeClass method, and in this method, set the TEST_NAME variable and PAGE_TO_TEST variable. PAGE_TO_TEST must be the url of the page you are testing.
Then you may add a @Test method for any page-specific test.