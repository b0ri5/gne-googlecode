"""Simple implementation of problem 1"""

__author__ == "Greg Tener"

def linear_sum(k):
	return k * (k + 1) / 2

def main():
	n = 1000 - 1
	print 3 * linear_sum(n / 3) + \
				5 * linear_sum(n / 5) - \
				15 * linear_sum(n / 15)

if __name__ == "__main__":
	main()
