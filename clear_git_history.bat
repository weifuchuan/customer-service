rem 1.Checkout
git checkout --orphan latest_branch
rem 2. Add all the files
git add -A
rem 3. Commit the changes
git commit -am "commit"
rem 4. Delete the branch
git branch -D master
rem 5.Rename the current branch to master
git branch -m master
rem 6.Finally, force update your repository
git push -f origin master