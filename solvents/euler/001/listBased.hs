{-
	Project Euler, problem 1:
		"Find the sum of all the multiples of 3 or 5 below 1000."

	Solution:
		* List all multiples of three < 1000.
		* List all multiples of five < 1000 that aren't multiples of 3.
		* Sum the above lists.
		* Sum the two sums.
-}

main = print $ listMultSum 3 5 1000

listMultSum :: Int -> Int -> Int -> Int
listMultSum x y n = xSum + ySum
	where
		xSum = sum [x, 2*x..(n - 1)]
		ySum = sum $ filter ((/= 0) . (`rem` x)) [y, 2*y..(n - 1)]
