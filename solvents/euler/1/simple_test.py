import simple
import unittest

class TestSimple(unittest.TestCase):
	def test_linear_sum(self):
		for i in range(10):
			self.assertEqual(sum(range(i + 1)), simple.linear_sum(i))

if __name__ == "__main__":
	unittest.main()
