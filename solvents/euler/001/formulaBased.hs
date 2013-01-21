{-
	Project Euler, problem 1:
		"Find the sum of all the multiples of 3 or 5 below 1000."

	Solution:
		* Calculate the sum of 3, 6..999
		* Calculate the sum of 5, 10..1000
        * Sum the above and subtract out duplicates of the form 3*5*n
-}

main = print $ formMultSum 3 5 1000

formMultSum :: Int -> Int -> Int -> Int
formMultSum x y n = multSum x + multSum y - multSum (x * y)
	where
        multSum a = a * nSum ((n - 1) `div` a)
        nSum z = ((z + 1) * z) `div` 2
