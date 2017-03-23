all:
	git pull
	git add --all
	read message ;\
	git commit -m "$$message"
	git push origin master
