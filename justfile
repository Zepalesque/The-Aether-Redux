run:
	./gradlew runclient

data: quickdata
	./gradlew spotlessApply

quickdata:
	./gradlew rundata

update:
	git fetch
	git pull
