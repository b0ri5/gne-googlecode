// Taken from http://code.google.com/p/js-test-driver/wiki/GettingStarted

GreeterTest = TestCase("GreeterTest");

GreeterTest.prototype.testGreet = function() {
  var greeter = new myapp.Greeter();
  assertEquals("Hello World!?!", greeter.greet("World"));
};
