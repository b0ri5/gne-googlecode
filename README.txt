Git:
	
	Using:
		To start a new branch
		$ git checkout -b <branch_name>

		To see what files aren't committed
		$ git status

		To add files to be committed
		$ git add <path>

		To see the diff against the master branch
		$ git diff master
		And more generally, an arbitrary branch
		$ git diff <branch_name>

	Tips:
		To see the current branch in the bash prompt
		# Place this in your ~/.bashrc
		export PS1='\u@\h:\w$(__git_ps1)\$ '
