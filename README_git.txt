Git:

	Links:
	  http://git-scm.com/book
	
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

		To commit the changes to the branch
		# -a means all and -m means message
		$ git commit -a -m "<commit_message>"

		To push a branch to the remote repository
		$ git push origin <branch_name>

	Unix tips:
		To see the current branch in the bash prompt
		# Place this in your ~/.bashrc
		export PS1='\u@\h:\w$(__git_ps1)\$ '
