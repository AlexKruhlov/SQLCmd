echo -n "Please, input the comments"
read text
git add .
git commit -m "$text"
git push
git status
echo -n "Press any key"
read