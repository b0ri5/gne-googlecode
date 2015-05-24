# Introduction #

This explains how to use git to work with gne. We shouldn't use remote branches and instead use them only locally, and push to origin/master.


# Details #

```
# Checkout the project (if not already done)
$ git clone https://<username>@code.google.com/p/gne/

# If you get an error that looks like this:
# error: error setting certificate verify locations:
#   CAfile: /usr/ssl/certs/ca-bundle.crt
#   CApath: none while accessing # https://borisb0ri5@code.google.com/p/gne/info/refs
# fatal: HTTP request failed
# 
# then the easiest thing to do to get this to work is
# $ git config --global http.sslVerify "false"

# Create the branch for adding the unit test
$ git checkout -b add_unittest

# Add the unit test
...
# Commit the changes
$ git commit -a -m "Adding a unit test."

# Ensure the diff is what you expect
$ git diff origin/master

# Create the issue in rietveld
$ upload.py --rev=origin/master -r <reviewers email> --send_mail

# Get it code reviewed and LGTM'd
...
# See what we'd push if we did
$ git push --dry-run origin HEAD:master

# This will show something like
# To https://code.google.com/p/gne
#  d66cea5..67a2bc6  HEAD -> master
# to verify the diff is what you expect, do
# git diff d66cea5..67a2bc6

# Push the diff
$ git push origin HEAD:master

# Now clean up the already-pushed branch
# Pull the changes into master
$ git checkout master
$ git pull
# Remove the merged-in branch
$ git branch -d add_unittest

# Done!!!
# The following can help make the last part easier:
# branch=$(git symbolic-ref -q HEAD | awk -F'/' '{print $NF}'); git checkout master && git pull && git branch -d ${branch}
```