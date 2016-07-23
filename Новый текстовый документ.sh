echo -n "Please, input the comments: "
read text
echo
git add .
echo
git commit -m "$text"
echo
git push
echo
git status
echo
echo -n "Press any key"
read